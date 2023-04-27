package com.williamhdz.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;

import com.williamhdz.quickpoll.domain.Poll;

public interface PollRepository extends CrudRepository<Poll, Long> {

}
