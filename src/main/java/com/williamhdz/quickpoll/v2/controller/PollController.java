package com.williamhdz.quickpoll.v2.controller;

import java.net.URI;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.williamhdz.quickpoll.domain.Poll;
import com.williamhdz.quickpoll.exception.ResourceNotFoundException;
import com.williamhdz.quickpoll.repository.PollRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("pollControllerV2")
@Tag(name = "polls", description = "Poll API")
@RequestMapping("/v2")
public class PollController {

	@Inject
	private PollRepository pollRepository; // DI with native
	
	// Get all polls
	@GetMapping("/polls")
	@Operation(summary = "Retrieves all the polls")
	public ResponseEntity<Iterable<Poll>> getAllPolls() {
		Iterable<Poll> allPolls = pollRepository.findAll();
		return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK);
	}
	
	// Save one poll
	@PostMapping("/polls")
	@Operation(summary = "Creates a new Poll", description = "The newly created poll Id will be sent in the location response header")
	public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll) {
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
	@Operation(summary = "Retrieves a Poll associated with the pollId")
	public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
		return new ResponseEntity<>(verifyPoll(pollId), HttpStatus.OK);
	}
	
	// update
	@PutMapping("polls/{pollId}")
	@Operation(summary = "Update a Poll exist")
	public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
		// save the entity
		verifyPoll(pollId);
		pollRepository.save(poll);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// delete one poll
	@DeleteMapping("polls/{pollId}")
	@Operation(summary = "Delete a Poll associated with the pollId")
	public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
		verifyPoll(pollId);
		pollRepository.deleteById(pollId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// metodo de exception
	protected Poll verifyPoll(Long pollId) throws ResourceNotFoundException {
		Optional<Poll> poll = pollRepository.findById(pollId);
		if(!poll.isPresent()) {
			throw new ResourceNotFoundException("Poll with id " + pollId
			+ " not found");
		}
			return poll.get();
		}
	
}
