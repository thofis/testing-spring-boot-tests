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
public class GeoModel implements Serializable {
	private double lat;

	private double lng;
}
