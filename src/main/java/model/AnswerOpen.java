package model;
/**
 * 
 * 
 * This class represents the class inherited of the class {@link Answer}
 * Represents the answers to an open question provided by the candidates
 * @author Team Pastis
 * @see Answer
 */
public class AnswerOpen extends Answer{
	// this atritbute contains the answer
	String answer;
	/**
	 * Instantiates an open answer
	 * @param questionnaire
	 * @param question
	 */
	public AnswerOpen(Questionnaire questionnaire, Question question) {
		super(questionnaire, question);
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
