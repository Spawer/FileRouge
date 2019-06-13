package model;
	/**
	 * 
	 * This class represents the class inherited of the class Proposition
	 * Represents the propositions of a multiple choice question
	 * provided by the {@link Conceptor} to help the corrector
	 * @author Team Pastis	 * 
	 * @see Conceptor
	 */
public class PropositionMCQ extends Proposition{
	//this attribute is set by the Conceptor and indicates if the proposition is right
	private boolean isRight;
	/**
	 * Instantiates a proposition for a multiple choice question
	 * @param statement (the statement of the proposition)
	 * @param isRight (Set the fact of being right)
	 */
	public PropositionMCQ(String statement, boolean isRight) {
		super(statement);
		this.isRight=isRight;
	}

	public boolean isRight() {
		return isRight;
	}
	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}
}
