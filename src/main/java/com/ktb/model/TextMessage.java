package com.ktb.model;

import java.util.List;

import lombok.Data;

@Data
public class TextMessage {
	private String userId;
	private List<Messages> messages;
}
