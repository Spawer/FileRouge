package fr.vaelia.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Question extends PanacheEntity {

	public static enum QuestionType {
		MCQ, OPEN, PROGRAMMING
	}

	String statement;

	public String getStatement() {
		return this.statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	@Enumerated(EnumType.STRING)
	private QuestionType type;
	private int timer;

	@OneToMany(mappedBy = "questionMCQ", cascade =  CascadeType.ALL, orphanRemoval = true)
	private List<PropositionMCQ> propositionsMCQ = new ArrayList<PropositionMCQ>();

	@OneToOne(mappedBy = "question",cascade = CascadeType.PERSIST)
	private Proposition proposition;

	@ManyToOne
	@JoinColumn(name = "questionnaire_id")
	private Questionnaire questionnaire;

	public Question() {
	}

	public Question(String statement, int timer, QuestionType type) {
		this.statement = statement;
		this.type = type;
		this.timer = timer;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public Proposition getProposition() {
		return proposition;
	}

	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	
	
	public List<PropositionMCQ> getPropositionsMCQ() {
		return this.propositionsMCQ;
	}

	public void setPropositionsMCQ(List<PropositionMCQ> propositions) {
		this.propositionsMCQ = propositions;
	}
}
