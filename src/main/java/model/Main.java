package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Question.QuestionType;


public class Main {

	public static void main(String[] args) {
		/*
		Proposition proposition1Q1 = new Proposition("blanc");
		Proposition proposition1Q2 = new Proposition("vert");
		Proposition proposition1Q3 = new Proposition("bleu");
		Proposition proposition1Q4 = new Proposition("rouge");
		List<Proposition> propositions = new ArrayList<>();
		propositions.add(proposition1Q1);
		propositions.add(proposition1Q2);
		propositions.add(proposition1Q3);
		propositions.add(proposition1Q4);
		Question question1 = new Question(QuestionType.MCQ, "Quelle est la couleur du cheval blanc d'Henri IV ?", 30, propositions);
		List<Question> questions = new ArrayList<>();
		questions.add(question1);
		Questionnaire questionnaire = new Questionnaire("test Henri IV", questions);
		Candidate candidate = new Candidate();
		Answer answer = new AnswerMCQ(questionnaire.getQuestion(0), candidate, questionnaire, questionnaire.getQuestion(0).getProposition(0));
		List<Answer> answers = new ArrayList<Answer>();
		ansConceptorwers.add(answer);
		candidate.setEmail("toto@titi.com");
		candidate.setFirstName("toto");
		candidate.setLastName("titi");
		candidate.setQuestionnairesAnswered(questionnaire, answers);
		List<Answer>
		System.out.println("");*/
		
		Conceptor conceptor = new Conceptor("Paul");
		Questionnaire questionnaire1 = conceptor.createQuestionnaire("Java");
		Question question1 = questionnaire1.createQuestion("Qu'est-ce que Java ?", 2 , QuestionType.MCQ);
		PropositionMCQ proposition1Question1 = question1.createProposition("Un langage de programmation",true);
		PropositionMCQ proposition2Question1 = question1.createProposition("Du café", false);
		System.out.println("programme terminé");	
		Candidate candidate = new Candidate("Pierre");
		candidate.createAnswer(questionnaire1,question1,proposition1Question1);
		System.out.println(questionnaire1.displayResult(candidate));

	}

}
