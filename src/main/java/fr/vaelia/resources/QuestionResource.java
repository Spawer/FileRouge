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
import javax.ws.rs.core.Response.Status;

import fr.vaelia.model.Proposition;
import fr.vaelia.model.Question;
import fr.vaelia.model.Questionnaire;
import fr.vaelia.model.Question.QuestionType;

/**
 * QuestionResource
 */
@Path("/question")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuestionResource {

    @GET
    public Response getAllQuestions() {
        Question.listAll();
        return Response.ok().status(200).build();
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
        return Response.status(204).build();
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
            return Response.status(Status.BAD_REQUEST).entity("Il manque le Statement").build();
        }
        if (question.getType() == null) {
            return Response.status(Status.BAD_REQUEST).entity("Il manque le Type").build();
        }
        if (question.getTimer() == 0) {
            return Response.status(Status.BAD_REQUEST).entity("Il manque le Timer").build();
        }
        if (question.getType() == QuestionType.MCQ) {

            if (question.getPropositions() == null || question.getPropositions().isEmpty()
                    || question.getPropositions().size() < 2) {
                return Response.status(Status.BAD_REQUEST).entity("Le type de question MCQ n'a pas assez propositions")
                        .build();
            } else {
                for (Proposition proposition : question.getPropositions()) {
                    if (proposition.getStatement().isEmpty()) {
                        return Response.status(Status.BAD_REQUEST).entity("Des propositions vides").build();
                    }
                }

            }
        }
        if (question.getType() == QuestionType.OPEN
                && (question.getPropositions() != null && !question.getPropositions().isEmpty())) {
            return Response.status(Status.BAD_REQUEST).entity("Le type de question OPEN a des propositions").build();
        }
        if (question.getType() == QuestionType.PROGRAMMING && (question.getPropositions() == null
                || question.getPropositions().isEmpty() || question.getPropositions().size() > 1)) {
            return Response.status(Status.BAD_REQUEST)
                    .entity("Le type de question PROGRAMMING doit avoir qu'une proposition").build();
        } else if (question.getProposition(0).getStatement().isEmpty()) {
            return Response.status(Status.BAD_REQUEST).entity("Le proposition est vide").build();
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
        if (question.getType() == Question.QuestionType.PROGRAMMING && (question.getPropositions() == null
                || question.getPropositions().isEmpty() || question.getPropositions().size() > 1)) {
            return Response.status(Status.BAD_REQUEST)
                    .entity("Le type de question PROGRAMMING doit avoir qu'une proposition").build();
        }
        if (question.getType() == QuestionType.OPEN
                && (question.getPropositions() != null && !question.getPropositions().isEmpty())) {
            return Response.status(Status.BAD_REQUEST).entity("Le type de question OPEN a des propositions").build();
        }
        if (question.getType() == QuestionType.MCQ) {

            if (question.getPropositions() == null || question.getPropositions().isEmpty()
                    || question.getPropositions().size() < 2) {
                return Response.status(Status.BAD_REQUEST).entity("Le type de question MCQ n'a pas assez propositions")
                        .build();
            } else {
                for (Proposition proposition : question.getPropositions()) {
                    if (proposition.getStatement().isEmpty()) {
                        return Response.status(Status.BAD_REQUEST).entity("Des propositions vides").build();
                    }
                }

            }
        }
        question2.setStatement(question.getStatement());
        question2.setTimer(question.getTimer());
        if (question.getType() == QuestionType.MCQ || question.getType() == Question.QuestionType.PROGRAMMING) {

            question2.getPropositions().clear();
            question2.getPropositions().addAll(question.getPropositions());

        }
        question2.persist();
        return Response.ok().build();
    }

}
