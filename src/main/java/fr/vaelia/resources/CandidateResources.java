package fr.vaelia.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.vaelia.model.AnswerOpen;
import fr.vaelia.model.Candidate;
import fr.vaelia.model.Question;
import fr.vaelia.model.Questionnaire;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.transaction.Transactional;

@Path("/candidates")
public class CandidateResources {
	@POST
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCandidate(Candidate candidate) {
		candidate.persist();
		if (candidate.isPersistent()) {
			return Response.ok(candidate).status(200).build();
		}
		return Response.notModified("Candidate not created").status(404).build();
	}
	
	@POST // Create an answer for a given question
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{candidateId}/questionnaires/{questionnaireId}/questions/{questionId}/answers")
	public Response createCandidateAnswerByQuestion(
			@PathParam("candidateId") Long candidateId,
			@PathParam("questionnaireId") Long questionnaireId,
			@PathParam("questionId") Long questionId,
			AnswerOpen answer
	) {
		Candidate candidate = Candidate.findById(candidateId);
		Questionnaire questionnaire = Questionnaire.findById(questionnaireId);
		Question question = Question.findById(questionId);
		answer.setCandidate(candidate);
		answer.setQuestionnaire(questionnaire);
		answer.setQuestion(question);
		answer.persist();
		
		if (answer.isPersistent()) {
			return Response.ok(answer).status(201).build();
		}
		return Response.notModified("Answer not created").status(404).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getCandidate(@PathParam("id") Long id) {
		if (Candidate.findById(id) != null)
			return Response.ok(Candidate.findById(id)).status(200).build();
		return Response.notModified("Candidate not existed").status(404).build();

	}
	
	@PUT
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response updateCandidate(@PathParam("id") Long id,Candidate ncandidate) {
		if (Candidate.findById(id) != null) {
			Candidate candidate=Candidate.findById(id);
			candidate.setFirstName(ncandidate.getFirstName());
			candidate.setLastName(ncandidate.getLastName());
			candidate.setEmail(ncandidate.getEmail());
			return Response.ok(candidate).status(200).build();
		}
		return Response.notModified("Candidate not updated").status(404).build();
		
	}
	
	@DELETE
	@Transactional
	@Path("/{id}")
	public Response deleteCandidate(@PathParam("id") Long id) {
		if (Candidate.findById(id)!= null) {
			Candidate.findById(id).delete();
			return Response.ok("Candidate deleted").status(200).build();	
		}
		return Response.notModified("Candidate not existed").status(404).build();
		
	}
	

}
