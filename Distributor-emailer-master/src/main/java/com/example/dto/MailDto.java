package com.example.dto;

import lombok.Data;

@Data
public class MailDto {
	private String to;
	private String message;
	private String receiverName;
	private String subject;
	private String templateName;
	private String otp;
	private String orderName;
	private String deliveryCode;
}
