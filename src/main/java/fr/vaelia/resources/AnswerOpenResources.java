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

import fr.vaelia.model.AnswerOpen;


@Path("/answerOpens")
public class AnswerOpenResources {
	@POST
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAnswerOpen(AnswerOpen answeropen) {
		answeropen.persist();
		if(answeropen.isPersistent())
			return Response.ok(answeropen).status(200).build();
		return Response.notModified("AnswerOpen not created").status(404).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getAnswerOpen(@PathParam("id") Long id ) {
		if (AnswerOpen.findById(id) != null)
			return Response.ok(AnswerOpen.findById(id)).status(200).build();
		return Response.notModified("AnswerOpen not find").status(404).build();
					
	}
	@PUT
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response updateAnswerOpen(@PathParam("id") Long id,AnswerOpen nansweropen) {
		if (AnswerOpen.findById(id) != null) {
			AnswerOpen answeropen = AnswerOpen.findById(id);
			answeropen.setAnswer(nansweropen.getAnswer());
			return Response.ok(AnswerOpen.findById(id)).status(200).build();
		
	}
		return Response.notModified("AnswerOpen not find").status(404).build();
	
	}
	@DELETE
	@Transactional
	@Path("{id}")
	public Response deleteAnswerOpen(@PathParam("id") Long id) {
		if (AnswerOpen.findById(id)!= null) {
			AnswerOpen.findById(id).delete();
			return Response.ok("AnswerOpen deleted").status(200).build();	
		}
		return Response.notModified("AnswerOpen not existed").status(404).build();
		
	}
}
