package com.example.testingspringboottests.service;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Todo {
	@Id
	private Long id;

	private Long userId;

	private String title;

	private Boolean completed;
}
