package fr.vaelia.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Conceptor {
	@Id
	private int id;
	private String name;
	@OneToMany(mappedBy="conceptor")
	private List<Questionnaire> questionnaires;
	
	public Conceptor() {}

	public Conceptor(String name) {
		this.name=name;
	}
	
	public void addQuestionnaire(Questionnaire questionnaire) {
		questionnaires.add(questionnaire);
	}
	
	public void removeQuestionnaire(Questionnaire questionnaire) {
		questionnaires.remove(questionnaire);
	}
	
	public List<Questionnaire> getQuestionnairesAnswered(Questionnaire questionnaire) {
		return questionnaires;
	}

	public Questionnaire createQuestionnaire(String name) {
		Questionnaire q = new Questionnaire(name);
		questionnaires.add(q);
		return q;
	}

}
