package de.ctdo.kasse.dao;

import de.ctdo.kasse.core.Banker;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author: lucas
 * @date: 14.10.14 17:22
 */
public class BankerDAO extends AbstractDAO<Banker> {

    public BankerDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Banker findByUsername(String username) {
        return uniqueResult(namedQuery("de.ctdo.kasse.core.Banker.findByUsername").setString("username", username));
    }

    public List<Banker> findAll() {
        return list(namedQuery("de.ctdo.kasse.core.Banker.findAll"));
    }

    public Banker findById(Long id) {
        return get(id);
    }

    public Banker save(Banker banker) {
        return persist(banker);
    }

}
