package com.ktb.model;

import lombok.Data;

@Data
public class Register {

	private String userId;
	private String email;
	private String employeeId;
	
	private String otpNumber;
	private String otpRefNumber;
}
