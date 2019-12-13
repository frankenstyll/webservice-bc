package com.ktb.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RegisterModel extends BaseModel{

	private String userId;
	private String email;
	private String employeeId;
	private String otp;
	private String refNumber;
	private Timestamp created;
	private Timestamp expire;
	private String status;
	
	@Override
	public String toString() {
		return "RegisterModel [userId=" + userId + ", email=" + email + ", employeeId=" + employeeId + ", otp=" + otp
				+ ", refNumber=" + refNumber + ", created=" + created + ", expire=" + expire + ", status=" + status
				+ "]";
	}
}
