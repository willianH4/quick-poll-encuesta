package com.williamhdz.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;

import com.williamhdz.quickpoll.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long> {

}
