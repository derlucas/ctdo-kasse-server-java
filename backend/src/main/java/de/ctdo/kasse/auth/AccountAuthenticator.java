package de.ctdo.kasse.auth;

import com.google.common.base.Optional;
import de.ctdo.kasse.core.Account;
import de.ctdo.kasse.dao.AccountDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * @author: lucas
 * @date: 14.10.14 18:15
 */
public class AccountAuthenticator implements Authenticator<BasicCredentials, Account> {

    private AccountDAO accountDAO;

    public AccountAuthenticator(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Optional<Account> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        final Account account = accountDAO.findById(basicCredentials.getUsername());

        if(account != null && account.getPin().equals(basicCredentials.getPassword())) {
            return Optional.fromNullable(account);
        }

        return Optional.absent();
    }
}
