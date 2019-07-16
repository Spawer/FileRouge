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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.vaelia.model.Proposition;
import fr.vaelia.model.PropositionMCQ;
import fr.vaelia.model.Question;
import fr.vaelia.model.Questionnaire;
import fr.vaelia.model.Question.QuestionType;

/**
 * QuestionResource
 */
@Path("/questions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuestionResource {

    @GET
    public Response getAllQuestions() {
        
        return Response.ok( Question.listAll()).build();
    }

    @GET
    @Path("/questionnaire/{id}")
    public Response getQuestionByQuestionnaire(@PathParam("id") Long id) {

        Questionnaire questionnaire = Questionnaire.findById(id);
        if (questionnaire != null) {
            return Response.ok(questionnaire.getQuestions()).build();
        }
        return Response.status(204).build();
    }

    @GET
    @Path("/{id}")
    public Response getQuestionById(@PathParam("id") Long id) {

        Question question = Question.findById(id);
        if (question != null) {
            return Response.ok(question).status(200).build();
        }
        return Response.status(404).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteQuestionById(@PathParam("id") Long id) {

        Question question = Question.findById(id);
        if (question != null) {
            question.delete();
            return Response.ok().build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}/questionnaire/{idQuestionnaire}")
    @Transactional
    public Response deleteQuestDQuestionnaire(@PathParam("idQuestionnaire") Long idQuestionnaire,
            @PathParam("id") Long id) {
        Questionnaire questionnaire = Questionnaire.findById(idQuestionnaire);
        if (questionnaire == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        Question question = Question.findById(id);
        if (question == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        if (questionnaire.getQuestions().remove(question)) {
            return Response.ok().build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @Transactional
    public Response ajouterQuestion(Question question) {
        if (question.getStatement() == null || question.getStatement().isEmpty()) {
            throw new WebApplicationException("Il manque le statelent");
            //return Response.status(Status.BAD_REQUEST).entity("Il manque le Statement").build();
        }
        if (question.getType() == null) {
            throw new WebApplicationException("Il manque le Type");
            // return Response.status(Status.BAD_REQUEST).entity("Il manque le Type").build();
        }
        if (question.getTimer() == 0) {
            throw new WebApplicationException("Il manque le Timer");
            // return Response.status(Status.BAD_REQUEST).entity("Il manque le Timer").build();
        }
        if (question.getType() == QuestionType.MCQ) {
            if (question.getPropositionsMCQ() == null || question.getPropositionsMCQ().isEmpty()
                    || question.getPropositionsMCQ().size() < 2) {
                        throw new WebApplicationException("Le type de question MCQ n'a pas assez de propisitions");
                // return Response.status(Status.BAD_REQUEST).entity("Le type de question MCQ n'a pas assez propositions")
                //         .build();
            } else {
                for (PropositionMCQ proposition : question.getPropositionsMCQ()) {
                    if (proposition.getStatement().isEmpty()) {
                        return Response.status(Status.BAD_REQUEST).entity("Des propositions vides").build();
                    }
                }

            }
        }
        if (question.getType() == QuestionType.OPEN && (question.getPropositionsMCQ() != null && !question.getPropositionsMCQ().isEmpty())) {
            throw new WebApplicationException("Le type de question OPEN a des propositions");
            // return Response.status(Status.BAD_REQUEST).entity("Le type de question OPEN a des propositions").build();
        }
        if (question.getType() == QuestionType.PROGRAMMING) {
            if (question.getPropositionsMCQ() == null || !question.getPropositionsMCQ().isEmpty()) {
                throw new WebApplicationException("Le type de question PROGRAMMING ne peut avoir de propositions MCQ"); 
                // return Response.status(Status.BAD_REQUEST)
                        // .entity("Le type de question PROGRAMMING doit avoir qu'une proposition").build();
            } else if (question.getProposition() == null || question.getProposition().getStatement() == null || question.getProposition().getStatement().isEmpty() ) {
                throw new WebApplicationException("La proposition est vide");
                // return Response.status(Status.BAD_REQUEST).entity("Le proposition est vide").build();
            }
        }

        question.persist();
        return Response.ok().build();

    }

    @PUT
    @Path("/{id}")
    @Transactional

    public Response modifierQuestion(@PathParam("id") Long id, Question question) {
        if (question == null) {
            return Response.status(Status.BAD_REQUEST).entity("La question entrant est vide").build();
        }
        if (question.getStatement() == null || question.getStatement().isEmpty() || question.getTimer() == 0) {
            return Response.status(Status.BAD_REQUEST).entity("Le champ statement ou le timer est vide").build();
        }
        Question question2 = Question.findById(id);

        if (question2 == null) {
            return Response.status(Status.BAD_REQUEST).entity("La question dont l'id est " + id + " n'existe pas")
                    .build();
        }
        if (question2.getType() != question.getType()) {
            return Response.status(Status.BAD_REQUEST).entity("Non autorisé").build();
        }
        if (question.getType() == Question.QuestionType.PROGRAMMING && (question.getPropositionsMCQ() == null
                || question.getPropositionsMCQ().isEmpty() || question.getPropositionsMCQ().size() > 1)) {
            return Response.status(Status.BAD_REQUEST)
                    .entity("Le type de question PROGRAMMING doit avoir qu'une proposition").build();
        }
        if (question.getType() == QuestionType.OPEN
                && (question.getPropositionsMCQ() != null && !question.getPropositionsMCQ().isEmpty())) {
            return Response.status(Status.BAD_REQUEST).entity("Le type de question OPEN a des propositions").build();
        }
        if (question.getType() == QuestionType.MCQ) {

            if (question.getPropositionsMCQ() == null || question.getPropositionsMCQ().isEmpty()
                    || question.getPropositionsMCQ().size() < 2) {
                return Response.status(Status.BAD_REQUEST).entity("Le type de question MCQ n'a pas assez propositions")
                        .build();
            } else {
                for (Proposition proposition : question.getPropositionsMCQ()) {
                    if (proposition.getStatement().isEmpty()) {
                        return Response.status(Status.BAD_REQUEST).entity("Des propositions vides").build();
                    }
                }

            }
        }
        question2.setStatement(question.getStatement());
        question2.setTimer(question.getTimer());
        if (question.getType() == QuestionType.MCQ || question.getType() == Question.QuestionType.PROGRAMMING) {

            question2.getPropositionsMCQ().clear();
            question2.getPropositionsMCQ().addAll(question.getPropositionsMCQ());

        }
        question2.persist();
        return Response.ok().build();
    }

}
