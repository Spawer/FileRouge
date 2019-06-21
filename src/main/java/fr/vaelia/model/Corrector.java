package fr.vaelia.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Corrector extends PanacheEntity {

	private String name;
	private String firstname;
	@OneToMany
	@JoinColumn(name = "corrector_id")
	private List<Questionnaire> questionnaires;
	@OneToMany
	@JoinColumn(name = "corrector_id")
	private List<Candidate> candidates;
	
	public Corrector() {
		
	}
	
	public Corrector(String name, String firstname) {
		super();
		this.name = name;
		this.firstname = firstname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public List<Questionnaire> getQuestionnaires(Questionnaire questionnaire) {
		return questionnaires;
	}

	public void addQuestionnaire(Questionnaire questionnaire) {
		questionnaires.add(questionnaire);
	}
	
	public void removeQuestionnaire(Questionnaire questionnaire) {
		questionnaires.remove(questionnaire);
	}

	@Override
	public String toString() {
		return "Corrector [name=" + name + ", firstname=" + firstname + "]";
	}
	
}
