package com.example.testingspringboottests.feign;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoModel implements Serializable {
	private double lat;

	private double lng;
}