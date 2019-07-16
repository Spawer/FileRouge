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

import fr.vaelia.model.Proposition;

/**
 * QuestionnaireResource
 */
@Path("/proposition")
public class PropositionResource {

	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response createProposition(Proposition proposition) {
		proposition.persist();
		return Response
				.ok(proposition)
				.status(Response.Status.CREATED)
				.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getPropositionById(@PathParam("id") Long id) {
		Proposition p = Proposition.findById(id);
		return Response
				.ok(p)
				.status(200)
				.build();
	}
	
	@PUT	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Transactional
	public Response updatePropositionById(@PathParam("id") Long id, Proposition proposition) {
		Proposition p = Proposition.findById(id);
		p.setStatement(proposition.getStatement());
		return Response.ok(p).status(200).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Transactional
	public Response deletePropositionById(@PathParam("id") Long id, Proposition proposition) {
		Proposition p =	Proposition.findById(id);
		p.delete();
		return Response.ok(p).status(200).build();
	}

}