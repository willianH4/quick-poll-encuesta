package com.williamhdz.quickpoll.controller;

import java.net.URI;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	// Save one poll
	@PostMapping("/polls")
	public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
		poll = pollRepository.save(poll);
		// forma 1: crea el registro sin retornar info
		// return new ResponseEntity<>(null, HttpStatus.CREATED);
		
		// forma 2: Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newPollUri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(poll.getId())
				.toUri();
		responseHeaders.setLocation(newPollUri);
		
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
		
	}
	
	// Get poll by ID
	@GetMapping("/polls/{pollId}")
	public ResponseEntity<?> getPoll(@PathVariable Long pollId) throws Exception {
		Optional<Poll> poll = pollRepository.findById(pollId);
		if (!poll.isPresent()) {
			throw new Exception("Poll not found");
		}
		return new ResponseEntity<>(poll.get(), HttpStatus.OK);
	}
	
	// update
	@PutMapping("polls/{pollId}")
	public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
		// save the entity
		Poll newPoll = pollRepository.save(poll);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// delete one poll
	@DeleteMapping("polls/{pollId}")
	public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
		pollRepository.deleteById(pollId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
