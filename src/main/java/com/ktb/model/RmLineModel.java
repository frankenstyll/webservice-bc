package com.ktb.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RmLineModel extends BaseModel{
	private String employeeId;
	private String lineId;
	private Timestamp created;
}
