package com.example.testingspringboottests.feign;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel implements Serializable {
	private String street;

	private String suite;

	private String city;

	private String zipcode;

	private GeoModel geo;
}
