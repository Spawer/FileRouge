package fr.vaelia.resources;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.vaelia.model.Conceptor;
import fr.vaelia.model.Question;
import fr.vaelia.model.Questionnaire;

/**
 * QuestionnaireResource
 */
@Path("/questionnaires")
public class QuestionnaireResource {

	// Route for creating a questionnaire 
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response createQuestionnaire(Questionnaire questionnaire,@QueryParam("conceptorId") Long conceptorId) {
		questionnaire.setConceptor(Conceptor.findById(conceptorId));
		questionnaire.persist();
		return Response
				.ok(questionnaire)
				.status(Response.Status.CREATED)
				.build();
	}

	@POST // Create the questions of a given questionnaire
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	@Path("/{id}/questions")
	public Response createQuestionsByQuestionnaire(Question question, @PathParam("id") Long id) {
		Questionnaire questionnaire = Questionnaire.findById(id);
		question.setQuestionnaire(questionnaire);
		question.persist();
		
		return Response
				.ok(question) // returns the question
				.status(Response.Status.CREATED)
				.build();
	}

	@GET // Get all questionnaires
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllQuestionnaires() {        
        return Response.ok(Questionnaire.listAll()).build();
	}

	@GET // Get a questionnaire
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
	
	@GET // Get the questions of the given questionnaire
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/questions")
	public Response getQuestionsByQuestionnaire(@PathParam("id") Long id) {
		Questionnaire questionnaire = Questionnaire.findById(id);
		List<Question> questions = questionnaire.getQuestions();
		
		return Response
				.ok(questions) // returns the list of questions for the given questionnaire
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