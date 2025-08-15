package com.sv.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cities")
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cityId;

	private String name;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private State state;

}
