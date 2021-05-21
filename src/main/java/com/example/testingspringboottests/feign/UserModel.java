package com.example.testingspringboottests.feign;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
