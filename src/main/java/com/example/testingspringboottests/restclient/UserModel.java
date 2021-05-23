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
public class UserModel implements Serializable {
	long id;

	String name;

	String username;

	String email;

	String phone;

	String website;

	AddressModel address;

	CompanyModel company;
}
