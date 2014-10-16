package de.ctdo.kasse.dao;

import de.ctdo.kasse.core.Account;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author: lucas
 * @date: 14.10.14 17:22
 */
public class AccountDAO extends AbstractDAO<Account> {

    public AccountDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Account> findAll() {
        return list(namedQuery("de.ctdo.kasse.core.Account.findAll"));
    }

    public Account findById(String id) {
        return get(id);
    }

    public Account save(Account account) {
        return persist(account);
    }

}
