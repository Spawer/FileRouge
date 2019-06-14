package model;

import javax.persistence.Entity;

/**
 * 
 * 
 * This class represents the class inherited of the class {@link Answer}
 * Represents the answers to an multiple choice question provided by the
 * candidates
 * 
 * @author Team Pastis
 * @see Answer
 */
@Entity
public class AnswerMCQ extends Answer {
	// this attribute represents the proposition provided by the conceptor
	// to help the corrector
	private PropositionMCQ propositionSelected; // mettre une classe fille ex: PropositionMCQ

	public AnswerMCQ(){}
	/**
	 * Instantiates an multiple choice answer
	 * 
	 * @param questionnaire
	 * @param question
	 * @param proposition
	 */
	public AnswerMCQ(Questionnaire questionnaire, Question question, PropositionMCQ proposition) {
		super(questionnaire, question);
		this.propositionSelected = proposition;
	}

	public String getAnswer() {
		return propositionSelected.getStatement();
	}

	public void setAnswer(PropositionMCQ answer) {
		this.propositionSelected = answer;
	}

}
