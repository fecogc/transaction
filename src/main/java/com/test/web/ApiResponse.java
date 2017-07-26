package com.test.web;

import org.springframework.http.HttpStatus;

public class ApiResponse {

    private HttpStatus status;
    private String message;
    private String request_id;

    //

    public ApiResponse() {
        super();
    }

    public ApiResponse(final HttpStatus status, final String message, final String request_id) {
        super();
        this.status = status;
        this.message = message;
        this.request_id = request_id;
    }

    //

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

	public String getRequest_id()
	{
		return request_id;
	}

	public void setRequest_id(String request_id)
	{
		this.request_id = request_id;
	}

}
