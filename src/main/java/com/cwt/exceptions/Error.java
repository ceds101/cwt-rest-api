package com.cwt.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
public class Error {

	@NonNull
	private Integer status;

	@NonNull
	private String message;

	private List<FieldError> errors = new ArrayList<>();

	public void addFieldError(String field, String message) {
		FieldError error = new FieldError(field, message);
		errors.add(error);
	}

	@Data
	@AllArgsConstructor
	class FieldError {

		@NonNull
		private String field;

		private String message;
	}

}
