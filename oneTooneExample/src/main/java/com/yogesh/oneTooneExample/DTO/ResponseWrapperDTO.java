package com.yogesh.oneTooneExample.DTO;

public class ResponseWrapperDTO {

	public String message;
	public Integer status;
	public String path;
	public Object data;

	/*
	 * When Data not get or error found Use this constructor
	 */
	public ResponseWrapperDTO(String message, Integer status, String path) {
		super();
		this.message = message;
		this.status = status;
		this.path = path;
	}

	/*
	 * When Data get from DB Used this constructor
	 */
	public ResponseWrapperDTO(String message, Integer accepted, String path, Object data) {
		super();
		this.message = message;
		this.status = accepted;
		this.path = path;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
