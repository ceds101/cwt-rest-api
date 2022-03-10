package com.cwt.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustomerPayload {

	@Size(min = 2, max = 20)
	private String firstName;

	@Size(min = 2, max = 20)
	private String lastName;

	@Email(message = "Email is not valid")
	private String email;

	private String location;

}
