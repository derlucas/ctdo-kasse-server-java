package de.ctdo.kasse.resources;


import com.sun.jersey.api.ConflictException;
import com.sun.jersey.api.NotFoundException;
import de.ctdo.kasse.core.Banker;
import de.ctdo.kasse.core.Product;
import de.ctdo.kasse.dao.ProductDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    final ProductDAO productDAO;

    public ProductResource(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }


    @GET
    @Path("/{id}")
    @UnitOfWork
    public Product get(@Auth Banker banker, @PathParam("id") Long id) {
        final Product product = productDAO.findById(id);

        if (product != null) {
            return product;
        }

        throw new NotFoundException();
    }

    @POST
    @UnitOfWork
    public Product create(@Auth Banker banker, @Valid Product product) {
        if (productDAO.findByBarcode(product.getBarcode()) != null) {
            throw new ConflictException();
        }
        return productDAO.save(product);
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Product update(@Auth Banker banker, @PathParam("id") Long id, @Valid Product product) {
        if (productDAO.findById(id) == null) {
            throw new NotFoundException();
        }

        if (productDAO.findByBarcode(product.getBarcode()) != null) {
            throw new ConflictException();
        }

        product.setId(id);

        return productDAO.save(product);
    }

    @GET
    @UnitOfWork
    public List<Product> getAll(@Auth Banker banker) {
        return productDAO.findAll();
    }

}