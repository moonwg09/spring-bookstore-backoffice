package com.backoffice.service;

import com.backoffice.model.PortOnePaymentVO;

public interface PortOneService {
	
	// PortOne REST API Access Token 발급
	String getAccessToken();
	
	PortOnePaymentVO getPayment(String impUid);
	
	boolean cancelPayment(String impUid, String reason);

}

