package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
	 * 
	 * 
	 * This class represents the super class Proposition
	 * Represents the propositions provided by the {@link Conceptor}
	 * to help the corrector
	 * @author Team Pastis
	 * @see Conceptor
	 */
@Entity
@Inheritance
public class Proposition {
	@Id @GeneratedValue
	protected int id;

	@ManyToOne
	@JoinColumn(name = "question_id")
	protected Question question;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}

	// contains the value of proposition provided by the Conceptor
	private String statement;
	/**
	 * Instantiates a proposition
	 * @param statement (the statement of the proposition)
	 */
	public Proposition(String statement) {
		this.statement=statement;
	}
	
}
