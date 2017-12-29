package com.karam.springboot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.karam.springboot.model.Question;
import com.karam.springboot.service.SurveyService;

@RestController
public class SurveyController {

	@Autowired
	SurveyService surveyService;

	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> retieveQuestionsForSurvey(@PathVariable String surveyId) {
		return surveyService.retrieveQuestions(surveyId);
	}

	@GetMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retieveDetailsForQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
		return surveyService.retrieveQuestion(surveyId, questionId);
	}

	@PostMapping("/surveys/{surveyId}/questions")
	public ResponseEntity<Object> addQuestionToSurvey(@PathVariable String surveyId,
			@RequestBody Question newQuestion) {
		Question question = surveyService.addQuestion(surveyId, newQuestion);
		if (question == null) {
			return ResponseEntity.noContent().build();//return 204
		}

		// this is to return the URI for the created resource along with the
		// status of the request
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(question.getId())
				.toUri();

		return ResponseEntity.created(location).build();//return 201
	}
}
