package fr.vaelia.resources;

import javax.ws.rs.Produces;
import javax.json.Json;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.WebApplicationException;

import fr.vaelia.model.Corrector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Path("/correctors")
public class CorrectorResource {

	@GET
	public String hello() {
		return "hello";
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
	@Operation(summary="Get corrector", description="Get corrector by its ID")
	public Response getSingle(@Parameter(description="The ID of the corrector", required=true)@PathParam("id") Long id) {
		Corrector corrector = Corrector.findById(id);
		if (corrector == null) {
			throw new WebApplicationException("Corrector with id of "+id+" does not exist.", 404);
		}
        return Response.ok(corrector.toString()).status(200).build();
    }
	
	@POST
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Corrector corrector) {
		if (corrector.getName().length() == 0 || corrector.getFirstname().length() == 0) {
			throw new WebApplicationException("Corrector name or/and firstname was invalidly set on request.", 422);
		}
		corrector.persist();
		return Response.ok(corrector).status(201).build();
	}
	
	@PUT
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response fullUpdate(@PathParam("id") Long id, Corrector corrector) {
		if (corrector.getName().length() == 0 || corrector.getFirstname().length() == 0) {
			throw new WebApplicationException("Person name or/and firstname was not set on request.", 422);
		}
		Corrector entity = Corrector.findById(id);
		if (entity == null) {
			throw new WebApplicationException("Person with id of "+id+" does not exist.", 404);
		}
		entity.setName(corrector.getName());
		entity.setFirstname(corrector.getFirstname());
		return Response.ok(entity).status(200).build();
	}
	
	@DELETE
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		Corrector corrector = Corrector.findById(id);
		if (corrector == null) {
			throw new WebApplicationException("Person with id of "+id+" does not exist.", 404);
		}
		corrector.delete();
        return Response.ok(corrector).status(200).build();
	}
	
	@Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {
 
        @Override
        public Response toResponse(Exception exception) {
            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }
            return Response.status(code)
                    .entity(Json.createObjectBuilder().add("error", exception.getMessage()).add("code", code).build())
                    .build();
        }
 
    }
}
