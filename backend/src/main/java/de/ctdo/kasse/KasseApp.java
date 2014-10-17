package de.ctdo.kasse;

import de.ctdo.kasse.auth.BankerAuthenticator;
import de.ctdo.kasse.core.Account;
import de.ctdo.kasse.core.Banker;
import de.ctdo.kasse.core.Product;
import de.ctdo.kasse.core.Transaction;
import de.ctdo.kasse.dao.AccountDAO;
import de.ctdo.kasse.dao.BankerDAO;
import de.ctdo.kasse.dao.ProductDAO;
import de.ctdo.kasse.dao.TransactionDAO;
import de.ctdo.kasse.health.DummyHealthCheck;
import de.ctdo.kasse.resources.AccountsResource;
import de.ctdo.kasse.resources.BankerResource;
import de.ctdo.kasse.resources.ProductResource;
import de.ctdo.kasse.resources.TransactionResource;
import io.dropwizard.Application;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author: lucas
 * @date: 05.06.14 23:50
 */
public class KasseApp extends Application<AppConfiguration> {

    public static void main(String[] args) throws Exception {
        new KasseApp().run(args);
    }

    @Override
    public String getName() {
        return "kasse-backend";
    }

    private final HibernateBundle<AppConfiguration> hibernate = new HibernateBundle<AppConfiguration>(Banker.class, Account.class, Transaction.class, Product.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
//        bootstrap.addBundle(new AssetsBundle("/assets/", "/static", "index.html"));


    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        final BankerDAO bankerDAO = new BankerDAO(hibernate.getSessionFactory());
        final AccountDAO accountDAO = new AccountDAO(hibernate.getSessionFactory());
        final TransactionDAO transactionDAO = new TransactionDAO(hibernate.getSessionFactory());
        final ProductDAO productDAO = new ProductDAO(hibernate.getSessionFactory());

        environment.healthChecks().register("dummy", new DummyHealthCheck());
//        environment.jersey().register(new BasicAuthProvider<>(new AccountAuthenticator(accountDAO), "account required"));
        environment.jersey().register(new BasicAuthProvider<>(new BankerAuthenticator(bankerDAO), "banker required"));
        environment.jersey().register(new AccountsResource(accountDAO, transactionDAO));
        environment.jersey().register(new TransactionResource(accountDAO, transactionDAO));
        environment.jersey().register(new BankerResource(bankerDAO));
        environment.jersey().register(new ProductResource(productDAO));
    }

}