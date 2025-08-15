package com.sv.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sv.management.entity.State;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer> {
	List<State> findByCountryCountryId(Integer countryId);
}
