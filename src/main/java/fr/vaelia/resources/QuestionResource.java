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

import fr.vaelia.model.PropositionMCQ;
import fr.vaelia.model.Question;
import fr.vaelia.model.Question.QuestionType;
import fr.vaelia.model.Questionnaire;

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
        }
        if (question.getType() == null) {
            throw new WebApplicationException("Il manque le Type");
        }
        if (question.getTimer() == 0) {
            throw new WebApplicationException("Il manque le Timer");
        }
        if (question.getType() == QuestionType.MCQ) {
            if (question.getPropositionsMCQ() == null || question.getPropositionsMCQ().isEmpty()
                    || question.getPropositionsMCQ().size() < 2) {
                        throw new WebApplicationException("Le type de question MCQ n'a pas assez de propisitions");
            } else {
                for (PropositionMCQ proposition : question.getPropositionsMCQ()) {
                    if (proposition.getStatement().isEmpty()) {
                        return Response.status(Status.BAD_REQUEST).entity("Des propositions vides").build();
                    }
                }

            }
        }
        if (question.getType() == QuestionType.OPEN) {
            if (question.getPropositionsMCQ() != null && !question.getPropositionsMCQ().isEmpty()) {
                throw new WebApplicationException("Le type de question OPEN a des propositions");
            }
        }
        if (question.getType() == QuestionType.PROGRAMMING) {
            if (question.getPropositionsMCQ() == null || !question.getPropositionsMCQ().isEmpty()) {
                throw new WebApplicationException("Le type de question PROGRAMMING ne peut avoir de propositions MCQ"); 
            } else if (question.getProposition() == null || question.getProposition().getStatement() == null || question.getProposition().getStatement().isEmpty() ) {
                throw new WebApplicationException("La liste des propositions contient des éléments vides", Status.BAD_REQUEST);
            }
        }

        question.persist();
        return Response.ok().build();

    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response modifierQuestion(@PathParam("id") Long id, Question questionClient) {
        if (questionClient == null) {
            throw new WebApplicationException("L'objet Question est vide", Status.BAD_REQUEST);
        }
        if (questionClient.getStatement() == null || questionClient.getStatement().isEmpty()) { // || questionClient.getTimer() == 0) {
            throw new WebApplicationException("Le libellé de la question est manquant", Status.BAD_REQUEST);
        }
        if (questionClient.getTimer() == 0) {
            throw new WebApplicationException("Le timer est invalide", Status.BAD_REQUEST);
        }

        Question questionServeur = Question.findById(id);

        if (questionServeur == null) {
            throw new WebApplicationException("La question dont l'id est " + id + " n'existe pas", Status.BAD_REQUEST);
        }
        if (questionServeur.getType() != questionClient.getType()) {
            throw new WebApplicationException("Modifier le type de la question n'est pas autorisé", Status.FORBIDDEN);
        }
        if (questionClient.getType() == Question.QuestionType.PROGRAMMING && (questionClient.getPropositionsMCQ() == null
                || questionClient.getPropositionsMCQ().isEmpty() || questionClient.getPropositionsMCQ().size() > 1)) {
            throw new WebApplicationException("Le type PROGRAMMING ne doit avoir qu'une proposition", Status.BAD_REQUEST);
        }
        if (questionClient.getType() == QuestionType.OPEN) {
            if (questionClient.getPropositionsMCQ() != null && !questionClient.getPropositionsMCQ().isEmpty()) {
                throw new WebApplicationException("Le type OPEN a des propositions MCQ", Status.BAD_REQUEST);
            }
            if (questionClient.getProposition() == null 
                || questionClient.getProposition().getStatement() == null 
                || questionClient.getProposition().getStatement().isEmpty() ) {
                    throw new WebApplicationException("Le type OPEN requiert une proposition de correction", Status.BAD_REQUEST);
                }
        }
        if (questionClient.getType() == QuestionType.MCQ) {

            if (questionClient.getPropositionsMCQ() == null || questionClient.getPropositionsMCQ().isEmpty()
                    || questionClient.getPropositionsMCQ().size() < 2) {
                throw new WebApplicationException("Le type MCQ requiert plus d'une proposition", Status.BAD_REQUEST);
            } else {
                for (PropositionMCQ proposition : questionClient.getPropositionsMCQ()) {
                    if (proposition.getStatement().isEmpty()) {
                        throw new WebApplicationException("La liste des propositions contient des éléments vides", Status.BAD_REQUEST);
                    }
                }
            }
        }
        questionServeur.setStatement(questionClient.getStatement());
        questionServeur.setTimer(questionClient.getTimer());
        if (questionClient.getType() == QuestionType.MCQ) {
            questionServeur.getPropositionsMCQ().clear();
            questionServeur.getPropositionsMCQ().addAll(questionClient.getPropositionsMCQ());
        }
        return Response.ok(questionServeur).build();
    }

}
