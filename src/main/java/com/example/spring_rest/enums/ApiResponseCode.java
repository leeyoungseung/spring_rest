package com.example.spring_rest.enums;

import com.example.spring_rest.types.ResponseEnumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode implements ResponseEnumType {
	OK("Success"),
	BAD_PARAMETER("Bad Parameter"),
	NOT_FOUND("Not Found"),
	UNAUTHORIZED("Unauthorized"),
	SERVER_ERROR("Server Error")
	;

	private final String message;
	
	@Override
	public String getId() {
		return name();
	}

	@Override
	public String getText() {
		return message;
	}

}
