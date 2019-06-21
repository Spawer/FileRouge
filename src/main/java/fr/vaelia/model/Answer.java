package fr.vaelia.model;

import javax.persistence.Entity;

import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
	 * 
	 * 
	 * This abstract class represents the super class answers
	 * All answers will be of her type {@link AnswerMCQ } , {@link AnswerOpen } 
	 * Represents the answers provided by the candidates
	 * 
	 * @author Team Pastis
	 * @see AnswerMCQ 
	 * @see AnswerOpen
	 * 
	 */
@Entity 
@Inheritance
public abstract class Answer extends PanacheEntity {

	

	//The answers are related to a question
	@ManyToOne//(optional = false)
	protected Question question;
	
	@ManyToOne
	@JoinColumn(name = "candidate_id")
	protected Candidate candidate;
	
	// The answers are related to a question
	@ManyToOne
	@JoinColumn(name = "questionnaire_id")
	protected Questionnaire questionnaire;
	

	public Answer() {}
	/**
	 * Instantiates an answer
	 * @param questionnaire
	 * @param question
	 */
	public Answer(Questionnaire questionnaire,Question question){
		this.question=question;
		this.questionnaire=questionnaire;
	}
	
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}	
	
	public Question getQuestion(){
		return this.question;
	}
	public void setQuestion(Question question) {
		this.question=question;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	
	/**
	 *@return this method return the answer in String form 
	 */
	public String getAnswer() {
		// à définir dans les classe filles pour savoir comment on fait
		return "";
	}
	
}
