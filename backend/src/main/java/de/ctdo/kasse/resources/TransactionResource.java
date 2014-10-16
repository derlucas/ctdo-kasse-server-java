package de.ctdo.kasse.resources;


import com.sun.jersey.api.NotFoundException;
import de.ctdo.kasse.core.Banker;
import de.ctdo.kasse.core.Transaction;
import de.ctdo.kasse.dao.AccountDAO;
import de.ctdo.kasse.dao.TransactionDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    final AccountDAO accountDAO;
    final TransactionDAO transactionDAO;

    public TransactionResource(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }


    @GET
    @Path("/{id}")
    @UnitOfWork
    public Transaction get(@Auth Banker banker, @PathParam("id") Long id) {
        final Transaction transaction = transactionDAO.findById(id);

        if (transaction != null) {
            return transaction;
        }

        throw new NotFoundException();
    }


}