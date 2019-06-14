package fr.vaelia.quickstart;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    PersistenceService ps;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "et bonjour";
    }


    @GET
    @Path("/inject")
    @Produces(MediaType.TEXT_PLAIN)
    public String inject() {
        ps.injectEntities(ps.createEntities());
        return "et bonjour";
    }

    public void buildStructure() {
        
    }
}