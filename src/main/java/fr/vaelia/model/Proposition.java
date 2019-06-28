package fr.vaelia.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * 
 * 
 * This class represents the super class Proposition Represents the propositions
 * provided by the {@link Conceptor} to help the corrector
 * 
 * @author Team Pastis
 * @see Conceptor
 */
@Entity
@Inheritance
public class Proposition extends PanacheEntity {
	@ManyToOne
	@JoinColumn(name = "question_id")
	protected Question question;

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
	 * 
	 * @param statement (the statement of the proposition)
	 */
	public Proposition() {}
	public Proposition(String statement) {
		this.statement = statement;
	}

	public Proposition() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((statement == null) ? 0 : statement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proposition other = (Proposition) obj;
		if (statement == null) {
			if (other.statement != null)
				return false;
		} else if (!statement.equals(other.statement))
			return false;
		if (other.id == null || other.id != id)
			return false;
		return true;
	}

}
