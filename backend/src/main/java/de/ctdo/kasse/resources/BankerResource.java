package de.ctdo.kasse.resources;


import de.ctdo.kasse.core.Banker;
import de.ctdo.kasse.dao.BankerDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/banker")
@Produces(MediaType.APPLICATION_JSON)
public class BankerResource {

    final BankerDAO bankerDAO;

    public BankerResource(BankerDAO bankerDAO) {
        this.bankerDAO = bankerDAO;
    }


    @POST
    @UnitOfWork
    public Banker create(Banker banker) {
        return bankerDAO.save(banker);
    }


}