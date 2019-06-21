package fr.vaelia.resources;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.vaelia.model.Conceptor;

/**
 * QuestionnaireResource
 */
@Path("/conceptor")
public class ConceptorResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response createConceptor(Conceptor conceptor) {
		conceptor.persist();
		return Response
				.ok(conceptor)
				.status(Response.Status.CREATED)
				.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getConceptorById(@PathParam("id") Long id) {
		Conceptor c = Conceptor.findById(id);
		System.out.println(c.getFirstName());
		return Response
				.ok(c)
				.status(200)
				.build();
	}
	
	@PUT	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Transactional
	public Response updateById(@PathParam("id") Long id, Conceptor conceptor) {
		Conceptor c = Conceptor.findById(id);
		c.setFirstName(conceptor.getFirstName());
		c.setLastName(conceptor.getLastName());
		return Response.ok(c).status(200).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Transactional
	public Response deleteById(@PathParam("id") Long id, Conceptor conceptor) {
		Conceptor c = Conceptor.findById(id);
		c.delete();
		return Response.ok(c).status(200).build();
	}

}