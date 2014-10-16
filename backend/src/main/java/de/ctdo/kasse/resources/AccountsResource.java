package de.ctdo.kasse.resources;


import com.sun.jersey.api.ConflictException;
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;
import de.ctdo.kasse.core.Account;
import de.ctdo.kasse.core.Banker;
import de.ctdo.kasse.core.Transaction;
import de.ctdo.kasse.dao.AccountDAO;
import de.ctdo.kasse.dao.TransactionDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountsResource {

    final AccountDAO accountDAO;
    final TransactionDAO transactionDAO;

    public AccountsResource(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }

    @POST
    @UnitOfWork
    public Account createAccount(@Valid Account account) {
        if(accountDAO.findById(account.getId()) != null) {
            throw new ConflictException();
        }
        // better start a new account with no money in it ;)
        account.setBalance(BigDecimal.ZERO);
        return accountDAO.save(account);
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Account update(@Auth Banker banker, @PathParam("id") String id, @Valid Account accountUpdate) {
        final Account account = accountDAO.findById(id);
        if(account == null) {
            throw new NotFoundException();
        }
        account.setPin(accountUpdate.getPin());
        account.setBalance(accountUpdate.getBalance());

        return accountDAO.save(account);
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Account get(@Auth Banker banker, @PathParam("id") String id) {
        final Account account = accountDAO.findById(id);
        if(account == null) {
            throw new NotFoundException();
        }
        return account;
    }


    @POST
    @Path("/{id}/transactions")
    @UnitOfWork
    public Transaction addTransaction(@Auth Banker banker, @PathParam("id") String accountId,
                                      Transaction transaction, @Context HttpServletRequest request) {
        final Account account = accountDAO.findById(accountId);
        if(account == null) {
            throw new NotFoundException("account not found");
        }

        final BigDecimal diff = account.getBalance().add(transaction.getBalance());

        if(diff.compareTo(BigDecimal.ZERO) > 0) {
            transaction.setTimeOfAction(DateTime.now());
            transaction.setAccount(account);
            transaction.setIpAddress(request.getRemoteAddr());
            transaction.setBanker(banker.getUsername());
            transactionDAO.save(transaction);

            account.setBalance(diff);
            accountDAO.save(account);

            return transaction;
        } else {
            Response.ResponseBuilder builder = new ResponseBuilderImpl();
            builder.status(Response.Status.CONFLICT);
            builder.entity("insufficient funds");
            throw new WebApplicationException(builder.build());
        }
    }

    @GET
    @Path("/{id}/transactions")
    @UnitOfWork
    public List<Transaction> getTransactionsForAccount(@Auth Banker banker, @PathParam("id") String accountId) {
        final Account account = accountDAO.findById(accountId);
        if(account == null) {
            throw new NotFoundException("account not found");
        }
        return transactionDAO.findByAccount(account);
    }

}