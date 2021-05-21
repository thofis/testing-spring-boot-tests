package com.example.testingspringboottests.feign;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyModel implements Serializable {
	private String name;

	private String catchPhrase;

	private String bs;

}
