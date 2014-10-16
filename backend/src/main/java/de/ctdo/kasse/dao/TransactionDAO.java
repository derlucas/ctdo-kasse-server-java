package de.ctdo.kasse.dao;

import de.ctdo.kasse.core.Account;
import de.ctdo.kasse.core.Transaction;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author: lucas
 * @date: 14.10.14 17:22
 */
public class TransactionDAO extends AbstractDAO<Transaction> {

    public TransactionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Transaction> findAll() {
        return list(namedQuery("de.ctdo.kasse.core.Transaction.findAll"));
    }

    public Transaction findById(Long id) {
        return get(id);
    }

    public List<Transaction> findByAccount(Account account) {
        return list(namedQuery("de.ctdo.kasse.core.Transaction.findByAccount").setParameter("account", account));
    }

    public Transaction save(Transaction transaction) {
        return persist(transaction);
    }

}
