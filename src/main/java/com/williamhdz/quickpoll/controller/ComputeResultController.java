package com.williamhdz.quickpoll.controller;

import java.util.HashMap;
import java.util.Map;

import jakarta.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.williamhdz.quickpoll.domain.Vote;
import com.williamhdz.quickpoll.dto.OptionCount;
import com.williamhdz.quickpoll.dto.VoteResult;
import com.williamhdz.quickpoll.repository.VoteRepository;

@RestController
public class ComputeResultController {
	
	@Inject
	private VoteRepository voteRepository;
	
	@GetMapping("/computeresult")
	public ResponseEntity<?> computeResult(@RequestParam Long pollId) {
		VoteResult voteResult = new VoteResult();
		Iterable<Vote> allVotes = voteRepository.findByPoll(pollId);
		
		// Algorithm to count votes
		int totalVotes = 0;
		Map<Long, OptionCount> tempMap = new HashMap<Long, OptionCount>();
		
		for(Vote v : allVotes) {
			totalVotes ++;
			// Get the OptionCount corresponding to this Option
			OptionCount optionCount = tempMap.get(v.getOption().getId());
			if(optionCount == null) {
				optionCount = new OptionCount();
				optionCount.setOptionId(v.getOption().getId());
				tempMap.put(v.getOption().getId(), optionCount);
			}
				optionCount.setCount(optionCount.getCount()+1);
			}
		
			voteResult.setTotalVotes(totalVotes);
			voteResult.setResults(tempMap.values());
		// end algorithm
		
		return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);
	}
	
}
