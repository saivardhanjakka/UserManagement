package com.sv.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sv.management.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
