package model;

import java.util.HashSet;
import java.util.Set;

public class Conceptor {
	private String name;
	private Set<Questionnaire> questionnaires = new HashSet<>();
	
	public Conceptor(String name) {
		this.name=name;
	}
	
	public void addQuestionnaire(Questionnaire questionnaire) {
		questionnaires.add(questionnaire);
	}
	
	public void removeQuestionnaire(Questionnaire questionnaire) {
		questionnaires.remove(questionnaire);
	}
	
	public Set<Questionnaire> getQuestionnairesAnswered(Questionnaire questionnaire) {
		return questionnaires;
	}

	public Questionnaire createQuestionnaire(String name) {
		Questionnaire q = new Questionnaire(name);
		questionnaires.add(q);
		return q;
	}

}
