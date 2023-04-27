package com.williamhdz.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;

import com.williamhdz.quickpoll.domain.Option;

public interface OptionRepository extends CrudRepository<Option, Long> {

}
