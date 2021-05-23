package com.example.testingspringboottests.restclient;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressModel implements Serializable {
	private String street;

	private String suite;

	private String city;

	private String zipcode;

	private GeoModel geo;
}
