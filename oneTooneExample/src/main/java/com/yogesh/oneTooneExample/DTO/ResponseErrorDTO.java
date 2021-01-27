package com.yogesh.oneTooneExample.DTO;

public class ResponseErrorDTO extends ResponseWrapperDTO {

	public String error;

	public ResponseErrorDTO(String message, Integer status, String path, String error) {
		super(message, status, path);
		this.error = error;
	}

	public ResponseErrorDTO(String message, Integer status, String path) {
		super(message, status, path);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
