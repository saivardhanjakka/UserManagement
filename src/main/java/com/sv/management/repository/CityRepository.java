package com.sv.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sv.management.entity.City;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
	List<City> findByStateStateId(Integer stateId);
}
