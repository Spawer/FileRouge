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
import fr.vaelia.model.Questionnaire;

/**
 * QuestionnaireResource
 */
@Path("/questionnaires")
public class QuestionnaireResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	@Path("/conceptor/{id}")
	public Response createQuestionnaire(Questionnaire questionnaire,@PathParam("id") Long id) {
		questionnaire.setConceptor(Conceptor.findById(id));
		questionnaire.persist();
		return Response
				.ok(questionnaire)
				.status(Response.Status.CREATED)
				.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getQuestionnaireById(@PathParam("id") Long id) {
		Questionnaire q = Questionnaire.findById(id);
		System.out.println(q.getName());
		return Response
				.ok(q)
				.status(200)
				.build();
	}
	
	@PUT	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Transactional
	public Response updateQuestionnaireById(@PathParam("id") Long id, Questionnaire questionnaire) {
		Questionnaire q = Questionnaire.findById(id);
		q.setName(questionnaire.getName());
		q.setValide(questionnaire.isValide());
		return Response.ok(q).status(200).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@Transactional
	public Response deleteQuestionnaireById(@PathParam("id") Long id, Questionnaire questionnaire) {
		Questionnaire q = Questionnaire.findById(id);
		q.delete();
		return Response.ok("Questionnaire deleted").status(200).build();
	}

}