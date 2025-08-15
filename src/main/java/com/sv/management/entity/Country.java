package com.sv.management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "countries")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer countryId;

	private String name;

	@OneToMany(mappedBy = "country")
	private List<State> states;

}
