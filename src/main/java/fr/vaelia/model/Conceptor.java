package fr.vaelia.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Conceptor extends PanacheEntity {
	private String lastName;
	private String firstName;

	@OneToMany(mappedBy = "conceptor")
	private List<Questionnaire> questionnaires;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Conceptor() {
	}

	public Conceptor(String lastName) {
		this.setLastName(lastName);
	}
	public Conceptor(String lastName, String firstName) {
		this.setLastName(lastName);
		this.setFirstName(firstName);
	}

	public void addQuestionnaire(Questionnaire questionnaire) {
		questionnaires.add(questionnaire);
	}

	public void removeQuestionnaire(Questionnaire questionnaire) {
		questionnaires.remove(questionnaire);
	}

	public List<Questionnaire> getQuestionnaires(Questionnaire questionnaire) {
		return questionnaires;
	}

	public Questionnaire createQuestionnaire(String name) {
		Questionnaire q = new Questionnaire(name);
		questionnaires.add(q);
		return q;
	}

}
