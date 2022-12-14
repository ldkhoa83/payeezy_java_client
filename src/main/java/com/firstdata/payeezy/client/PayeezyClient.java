package com.firstdata.payeezy.client;


import com.firstdata.payeezy.models.enrollment.ACHPayRequest;
import com.firstdata.payeezy.models.enrollment.BAARequest;
import com.firstdata.payeezy.models.enrollment.EnrollmentRequest;
import com.firstdata.payeezy.models.exception.ApplicationRuntimeException;
import com.firstdata.payeezy.models.transaction.EventQuery;
import com.firstdata.payeezy.models.transaction.TransactionRequest;
import com.firstdata.payeezy.models.transaction.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Component
public class PayeezyClient {

	private static Logger logger =  LoggerFactory.getLogger(PayeezyClient.class);

	private static Charset UTF_8 = Charset.forName("UTF-8");

	private final RestTemplate restTemplate;

	private String transactionsUrl;
	
	private String secondaryTransactionUrl;

	private String baseUrl;


	public PayeezyClient(PayeezyRequestOptions requestOptions, String transactionsUrl, RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new PayeezyRequestInterceptor(requestOptions));
		if(transactionsUrl == null || "".equals(transactionsUrl.trim())){
			throw new ApplicationRuntimeException("URL is not configured.");
		}
		this.restTemplate.setInterceptors(interceptors);
		this.transactionsUrl = transactionsUrl;
		String url;
		if (this.transactionsUrl.endsWith(APIResourceConstants.PRIMARY_TRANSACTIONS)) {
			baseUrl = this.transactionsUrl.substring(0,transactionsUrl.indexOf("/v1"));
			url = transactionsUrl;
		} else {
			baseUrl = transactionsUrl;
			url = transactionsUrl + APIResourceConstants.PRIMARY_TRANSACTIONS;
		}
		
		logger.info("Transaction URL: " + url);
		//logger.info("Secondary transaction URL: " + this.secondaryTransactionUrl);
		this.secondaryTransactionUrl = url + "/{id}";
		this.transactionsUrl = url;

		// set up a proxy
		String proxyHost = requestOptions.getProxyHost();
		if(proxyHost != null && !"".equals(proxyHost.trim())){
			//default to 80 if none provided
			String proxyPort = requestOptions.getProxyPort() != null && !"".equals(requestOptions.getProxyPort().trim()) ? requestOptions.getProxyPort() : "80";
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			Proxy proxy= new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.valueOf(proxyPort)));
			requestFactory.setProxy(proxy);
			restTemplate.setRequestFactory(requestFactory);
		}
	}

	public ResponseEntity<TransactionResponse> post(TransactionRequest request) {
		return this.restTemplate.postForEntity(this.transactionsUrl, request, TransactionResponse.class);
	}

	public ResponseEntity<TransactionResponse> post(TransactionRequest request, String id){
//		logger.info("Secondary Transaction: {} {} ", this.secondaryTransactionUrl, jsonHelper.getJSONObject(request) );
		return this.restTemplate.postForEntity(this.secondaryTransactionUrl, request,TransactionResponse.class, id);
	}

	public ResponseEntity<TransactionResponse> tokenize(TransactionRequest request){
		String url = this.baseUrl+APIResourceConstants.SECURE_TOKEN_URL;
		return this.restTemplate.postForEntity(url, request, TransactionResponse.class);
	}

	/**
	 * Enrollment call for ACH
	 * @param enrollmentRequest
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<String> enrollInACH(EnrollmentRequest enrollmentRequest) throws Exception {
		String url = this.baseUrl+APIResourceConstants.ACH_ENROLLMENT_URL;
		return this.restTemplate.postForEntity(url, enrollmentRequest, String.class);
	}

	/**
	 * Validate Micro Deposit
	 * @param microDeposit
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<String> validateMicroDeposit(BAARequest microDeposit) throws Exception {
		String url = this.baseUrl+ APIResourceConstants.ACH_MICRO_DEPOSIT;
		return this.restTemplate.postForEntity(url, microDeposit, String.class);
	}

	public ResponseEntity<String> payACH(ACHPayRequest request) {
		String url = this.baseUrl+ APIResourceConstants.ACH_PAYMENT;
		return this.restTemplate.postForEntity(url, request, String.class);
	}

	/**
	 * Update ACH Enrollment info
	 * @param enrollmentRequest
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<String> updateACHEnrollment(EnrollmentRequest enrollmentRequest) throws Exception {
		String url = this.baseUrl+APIResourceConstants.ACH_ENROLLMENT_URL;
		HttpEntity<EnrollmentRequest> entity = new HttpEntity<>(enrollmentRequest);
		return this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
	}

	/**
	 * Close Enrollment call for ACH
	 * @param enrollmentRequest
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<String> closeACHEnrollment(EnrollmentRequest enrollmentRequest) throws Exception {
		String url = this.baseUrl+APIResourceConstants.ACH_CLOSE;
		return this.restTemplate.postForEntity(url, enrollmentRequest, String.class);
	}

	public ResponseEntity<String> getEvents(EventQuery eventQuery){
		String url = this.baseUrl + APIResourceConstants.EVENTS +
				"?from={from}&to={to}&offset={offset}&limit={limit}&eventType=TRANSACTION_STATUS";
		Map<String,String> eventQueryMap = Map.of("from",eventQuery.getFrom(),
				"to",eventQuery.getTo(),
				"offset",eventQuery.getOffset().toString(),
				"limit",eventQuery.getLimit().toString());
		return get(url,eventQueryMap);
	}

	public ResponseEntity<String> get(String URL, Map<String, String> queryMap) {
		return this.restTemplate.getForEntity(URL, String.class, queryMap);
	}
	
	

	
	
	

}
