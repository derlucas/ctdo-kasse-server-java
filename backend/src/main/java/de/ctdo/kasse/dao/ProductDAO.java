package de.ctdo.kasse.dao;

import de.ctdo.kasse.core.Product;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author: lucas
 * @date: 14.10.14 17:22
 */
public class ProductDAO extends AbstractDAO<Product> {

    public ProductDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Product findByBarcode(String barcode) {
        return uniqueResult(namedQuery("de.ctdo.kasse.core.Product.findByBarcode").setString("barcode", barcode));
    }

    public List<Product> findAll() {
        return list(namedQuery("de.ctdo.kasse.core.Product.findAll"));
    }

    public Product findById(Long id) {
        return get(id);
    }

    public Product save(Product product) {
        return persist(product);
    }

}
