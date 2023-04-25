package com.williamhdz.quickpoll.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.williamhdz.quickpoll.domain.Poll;
import com.williamhdz.quickpoll.repository.PollRepository;

@RestController
public class PollController {

	@Inject
	private PollRepository pollRepository;
	
	// Get all polls
	@GetMapping("/polls")
	public ResponseEntity<Iterable<Poll>> getAllPolls() {
		Iterable<Poll> allPolls = pollRepository.findAll();
		return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK);
	}
}
