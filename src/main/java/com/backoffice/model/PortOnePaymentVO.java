package com.backoffice.model;

import lombok.Data;

@Data
public class PortOnePaymentVO {

	private String imp_uid;
	private String merchant_uid;
	private String status;
	private Long amount;
}
