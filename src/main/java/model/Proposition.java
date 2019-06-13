package model;
	/**
	 * 
	 * 
	 * This class represents the super class Proposition
	 * Represents the propositions provided by the {@link Conceptor}
	 * to help the corrector
	 * @author Team Pastis
	 * @see Conceptor
	 */
public class Proposition {
	// contains the value of proposition provided by the Conceptor
	private String statement;
	/**
	 * Instantiates a proposition
	 * @param statement (the statement of the proposition)
	 */
	public Proposition(String statement) {
		this.statement=statement;
	}
	
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
}
