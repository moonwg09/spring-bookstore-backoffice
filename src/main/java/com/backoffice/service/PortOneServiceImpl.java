package com.backoffice.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.backoffice.model.PortOnePaymentVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PortOneServiceImpl implements PortOneService {

    private static final String PORTONE_API_URL = "https://api.iamport.kr";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getAccessToken() {

        try {

       
        	
            String apiKey = System.getenv("PORTONE_API_KEY");
            String apiSecret = System.getenv("PORTONE_API_SECRET");

            if (apiKey == null || apiSecret == null) {
                throw new IllegalStateException(
                    "PortOne API 환경변수가 설정되지 않았습니다."
                );
            }

            String requestBody =
                objectMapper.createObjectNode()
                    .put("imp_key", apiKey)
                    .put("imp_secret", apiSecret)
                    .toString();

            HttpRequest request =
                HttpRequest.newBuilder()
                    .uri(URI.create(PORTONE_API_URL + "/users/getToken"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response =
                httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
                );

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                    "PortOne Access Token 발급 실패: HTTP "
                    + response.statusCode()
                );
            }

            JsonNode root =
                objectMapper.readTree(response.body());

            if (root.get("code").asInt() != 0) {
                throw new RuntimeException(
                    "PortOne Access Token 발급 실패: "
                    + root.get("message").asText()
                );
            }

            return root
                    .get("response")
                    .get("access_token")
                    .asText();

        } catch (Exception e) {
            throw new RuntimeException(
                "PortOne Access Token 처리 중 오류 발생",
                e
            );
        }
    }


    @Override
    public PortOnePaymentVO getPayment(String impUid) {

        try {

            String accessToken = getAccessToken();

            System.out.println("=== PortOne 결제 조회 ===");
            System.out.println("impUid = [" + impUid + "]");

            String paymentUrl =
                    PORTONE_API_URL + "/payments/" + impUid + "?include_sandbox=true";

            System.out.println("조회 URL = [" + paymentUrl + "]");

            HttpRequest request =
                    HttpRequest.newBuilder()
                        .uri(URI.create(paymentUrl))
                        .header("Authorization", accessToken)
                        .GET()
                        .build();

            HttpResponse<String> response =
                    httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                    );

            System.out.println(
                "PortOne HTTP Status = "
                + response.statusCode()
            );

            System.out.println(
                "PortOne Response Body = "
                + response.body()
            );

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                    "PortOne 결제 조회 실패: HTTP "
                    + response.statusCode()
                    + " / "
                    + response.body()
                );
            }

            JsonNode root =
                    objectMapper.readTree(response.body());

            if (root.get("code").asInt() != 0) {
                throw new RuntimeException(
                    "PortOne 결제 조회 실패: "
                    + root.get("message").asText()
                );
            }

            JsonNode paymentNode =
                    root.get("response");

            PortOnePaymentVO payment =
                    new PortOnePaymentVO();

            payment.setImp_uid(
                    paymentNode.get("imp_uid").asText()
            );

            payment.setMerchant_uid(
                    paymentNode.get("merchant_uid").asText()
            );

            payment.setStatus(
                    paymentNode.get("status").asText()
            );

            payment.setAmount(
                    paymentNode.get("amount").asLong()
            );

            return payment;

        } catch (Exception e) {

            throw new RuntimeException(
                "PortOne 결제정보 조회 중 오류 발생",
                e
            );
        }
    }


	@Override
	public boolean cancelPayment(String impUid, String reason) {

	    try {

	        String accessToken = getAccessToken();

	        String requestBody =
	            objectMapper.createObjectNode()
	                .put("imp_uid", impUid)
	                .put("reason", reason)
	                .toString();

	        HttpRequest request =
	            HttpRequest.newBuilder()
	                .uri(
	                    URI.create(
	                        PORTONE_API_URL + "/payments/cancel"
	                    )
	                )
	                .header(
	                    "Authorization",
	                    accessToken
	                )
	                .header(
	                    "Content-Type",
	                    "application/json"
	                )
	                .POST(
	                    HttpRequest.BodyPublishers.ofString(
	                        requestBody
	                    )
	                )
	                .build();

	        HttpResponse<String> response =
	            httpClient.send(
	                request,
	                HttpResponse.BodyHandlers.ofString()
	            );

	        if (response.statusCode() != 200) {
	            throw new RuntimeException(
	                "PortOne 결제 취소 실패: HTTP "
	                + response.statusCode()
	            );
	        }

	        JsonNode root =
	            objectMapper.readTree(response.body());

	        if (root.get("code").asInt() != 0) {
	            throw new RuntimeException(
	                "PortOne 결제 취소 실패: "
	                + root.get("message").asText()
	            );
	        }

	        return true;

	    } catch (Exception e) {

	        throw new RuntimeException(
	            "PortOne 결제 취소 처리 중 오류 발생",
	            e
	        );
	    }
	}
}
