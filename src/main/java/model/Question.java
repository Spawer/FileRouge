package model;

import java.util.ArrayList;
import java.util.List;

public class Question {
	public static enum QuestionType {MCQ,OPEN,PROGRAMMING}
	String statement;
	private QuestionType type;
	private int timer;
	private List<Proposition> propositions = new ArrayList<Proposition>();
	
	public Question() {
	}
	
	public Question(String statement, int timer, QuestionType type) {
		this.statement=statement;
		this.type=type;
		this.timer=timer;
	}
	
	public void show() {
		
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public Proposition  getProposition(int index) {
		return propositions.get(index);
	}

	public void addProposition(Proposition  proposition) {
		propositions.add(proposition);
	}
	
	public void removePropositionByIndex(int index) {
		propositions.remove(index);
	}
	
	public void removePropositionByQuestion(Proposition proposition) {
		propositions.remove(proposition);
	}
	public QuestionType getType() {
		return type;
	}
	public void setType(QuestionType type) {
		this.type = type;
	}

	public Proposition createProposition(String statement) {
		Proposition p = new Proposition(statement);
		propositions.add(p);
		return p;
	}
	
	public PropositionMCQ createProposition(String statement, boolean isRight) {
		PropositionMCQ p = new PropositionMCQ(statement,isRight);
		propositions.add(p);
		return p;
	}
}
