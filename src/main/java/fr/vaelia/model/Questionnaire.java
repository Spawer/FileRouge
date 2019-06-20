package fr.vaelia.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fr.vaelia.model.Question.QuestionType;

@Entity
public class Questionnaire {
	@Id @GeneratedValue
	private int id;

	private String name;
	private boolean isValide;

	@OneToMany(mappedBy="questionnaire", cascade = CascadeType.PERSIST)
	private List<Question> questions = new ArrayList<Question>();

	@ManyToOne(optional = false)
	@JoinColumn(name = "conceptor_id")
	private Conceptor conceptor;

	public Conceptor getConceptor() {
		return this.conceptor;
	}

	public void setConceptor(Conceptor conceptor) {
		this.conceptor = conceptor;
	}
	
	public Questionnaire(String name) {
		this.name=name;
	}
	
	public Question getQuestion(int index) {
		return questions.get(index);
	}

	public void addQuestion(Question question) {
		questions.add(question);
	}
	
	public void removeQuestionByIndex(int index) {
		questions.remove(index);
	}
	
	public void removeQuestionByQuestion(Question question) {
		questions.remove(question);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isValide() {
		return isValide;
	}

	public void setValide(boolean isValide) {
		this.isValide = isValide;
	}

	
	public String generateLink() {
		return "";
	}
	
	public Question createQuestion(String statement, int timer, QuestionType questionType) {
		return new Question(statement, timer, questionType);
	}

	@Override
	public boolean equals(Object questionnaire) {
		if(questionnaire instanceof Questionnaire) {
			return this.getName().equals(((Questionnaire) questionnaire).getName());
		}
		return false;
	}

	public String displayResult(Candidate candidate) {
		Set cles = candidate.getQuestionnairesAnswered().keySet();
		Iterator it = cles.iterator();
		while(it.hasNext()) {
			//candidate.getQuestionnairesAnsweredFromQuestionnaire(candidate.getQuestionnairesAnsweredFromQuestionnaire());
			
			Object object = it.next();
		}
		// temporar bullshit
		return "";
	}
	
	
	
}
