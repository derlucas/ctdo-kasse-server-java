package de.ctdo.kasse.auth;

import com.google.common.base.Optional;
import de.ctdo.kasse.core.Banker;
import de.ctdo.kasse.dao.BankerDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * @author: lucas
 * @date: 14.10.14 18:15
 */
public class BankerAuthenticator implements Authenticator<BasicCredentials, Banker> {

    private BankerDAO bankerDAO;

    public BankerAuthenticator(BankerDAO bankerDAO) {
        this.bankerDAO = bankerDAO;
    }

    @Override
    public Optional<Banker> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        final Banker banker = bankerDAO.findByUsername(basicCredentials.getUsername());

        if(banker != null) {

            if(basicCredentials.getPassword().equals(banker.getPassword()) ) {
                return Optional.fromNullable(banker);
            }
        }

        return Optional.absent();
    }
}
