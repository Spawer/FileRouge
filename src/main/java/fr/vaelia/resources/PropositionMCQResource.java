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
import fr.vaelia.model.PropositionMCQ;

/**
 * QuestionnaireResource
 */
@Path("/propositionMCQs")
public class PropositionMCQResource {

	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response createPropositionMCQ(PropositionMCQ proposition) {
		proposition.persist();
		return Response
				.ok(proposition)
				.status(Response.Status.CREATED)
				.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getPropositionMCQById(@PathParam("id") Long id) {
		PropositionMCQ p = PropositionMCQ.findById(id);
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
	public Response updatePropositionMCQById(@PathParam("id") Long id, PropositionMCQ proposition) {
		PropositionMCQ p = PropositionMCQ.findById(id);
		p.setStatement(proposition.getStatement());
		p.setIsRight(proposition.isRight());
		return Response.ok(p).status(200).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Transactional
	public Response deletePropositionMCQById(@PathParam("id") Long id, PropositionMCQ proposition) {
		PropositionMCQ p =	PropositionMCQ.findById(id);
		p.delete();
		return Response.ok(p).status(200).build();
	}

}