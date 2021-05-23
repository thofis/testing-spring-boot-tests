package com.example.testingspringboottests.restclient;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyModel implements Serializable {
	private String name;

	private String catchPhrase;

	private String bs;

}
