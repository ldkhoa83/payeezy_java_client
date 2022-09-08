package com.firstdata.payeezy.samples;

import com.firstdata.payeezy.PayeezyClientHelper;
import com.firstdata.payeezy.models.transaction.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThreeDomainSecureSampleTest {

    @Autowired
    private PayeezyClientHelper payeezyClientHelper;


    @Test
    public void given3DSTransactionV1_WhenAuthorizeTrans_ThenTransIsAuthorized() throws Exception {
        TransactionRequest request = get3DSTransactionRequest();
        request.transactionType(TransactionType.AUTHORIZE_VALUE);

        PayeezyResponse response = payeezyClientHelper.doPrimaryTransaction(request);
        System.out.println(response.getResponseBody());
    }

    /*
     * Use this method to make 3DS Transaction 2.0;
     * MasterCard Identity Check ™ and Accountholder Authentication Value (AAV) verification services
     * as part of the EMV ™ 3-D secure authentication protocol for e-commerce transactions.
     * The info must be provided by those merchants who are participating in
     * the MasterCard Identity Check ™ (SecureCode) program when the Program Protocol and
     * Transaction ID values are returned by the plug-ins. These fields must be passed through and
     * the merchants need to coordinate with their merchant plug-in (MPI)
     * to receive the Directory Server Transaction ID.
     * Please note that the AAV (Accountholder Authentication Value) must be provided as part of
     * the API request (using the "cavv" API field).
     * */
    @Test
    public void given3DSTransactionV2_WhenAuthorizeTrans_ThenTransIsAuthorized() throws Exception {
        TransactionRequest request = get3DSTransactionRequestV2();
        request.transactionType(TransactionType.AUTHORIZE_VALUE);

        PayeezyResponse response = payeezyClientHelper.doPrimaryTransaction(request);
        System.out.println(response.getResponseBody());
    }

    @Test
    public void givenDecryptedAppleAuthentication_WhenPurchase_ThenTransactionIsSuccess() throws Exception {
        TransactionRequest request = getApplePayRequest();
        request.transactionType(TransactionType.PURCHASE_VALUE);

        PayeezyResponse response = payeezyClientHelper.doPrimaryTransaction(request);
        System.out.println(response.getResponseBody());

    }



    private static TransactionRequest get3DSTransactionRequest(){
        ThreeDomainSecureToken threeDSToken = new ThreeDomainSecureToken();
        threeDSToken.setThreeDomainType("D");
        threeDSToken.setName("John Smith");
        threeDSToken.setNumber("4788250000028291");
        threeDSToken.setExpiryDt("1020");
        threeDSToken.setCvv("123");
        threeDSToken.setCavv("01ade6ae340005c681c3a1890418b53000020000");
        threeDSToken.setWalletProvider("APPLE_PAY");

        TransactionRequest request = new TransactionRequest()
                .paymentMethod(PaymentMethod.THREEDS.getValue())
                .amount("15")
                .currency("USD")
                /*
                 * Electronic Commerce Indicator (ECI) is a value that is returned from
                 * the Directory Server (Visa®, MasterCard®, and JCB®) to
                 * indicate the authentication results of your customer credit card payment on 3DS.*/
                .eciIndicator("5")
                .threeDomainSecureToken(threeDSToken);

        return request;
    }

    private static TransactionRequest get3DSTransactionRequestV2(){
        ThreeDomainSecureTokenV2 threeDSToken = new ThreeDomainSecureTokenV2();
        threeDSToken.setThreeDomainType("D");
        threeDSToken.setName("John Smith");
        threeDSToken.setNumber("4788250000028291");
        threeDSToken.setExpiryDt("1020");
        threeDSToken.setCvv("123");
        threeDSToken.setCavv("01ade6ae340005c681c3a1890418b53000020000");
        threeDSToken.setWalletProvider("APPLE_PAY");

        threeDSToken.programProtocol("2").directoryServerTransId("f38e6948-5388-41a6-bca4-b49723c19437");

        TransactionRequest request = get3DSTransactionRequest();
        request.threeDomainSecureToken(threeDSToken);

        return request;
    }

   /*
   * This payload represents the encrypted cryptogram-based payment information that is supported and
   * obtained via Apple Pay®.Payeezy will decrypt the cryptographic payload and process the transaction.
   *
   * Transaction Types
   * Supported Transaction Types are "authorize" & "purchase" only.
   * */
    private static TransactionRequest getApplePayRequest(){
        ThreeDomainSecureHeader tokenHeader = new ThreeDomainSecureHeader();
        tokenHeader.setApplicationDataHash("94ee059335e587e501cc4bf90613e0814f00a7b08bc7c648fd865a2af6a22cc2");
        tokenHeader.setEphemeralPublicKey("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEiaU1SbkYTJy/j5L1t51vtGDh4KlNl5MFPWzo/C8r0WcrktWriz5pdRaDVUDvU++KlDu2iuQsd2xSNKJlFscbDQ==");
        tokenHeader.setPublicKeyHash("YmSWN7lj4+A6fVJVPicP8TgS7gI7ougD8rEWB5LXtMM=");
        tokenHeader.setTransactionId("3331333233333334333533363337");

        ThreeDomainSecureToken token = new ThreeDomainSecureToken();
        token.setThreeDomainType("A");
        token.setData("sETsMq0fFAIbi3pzGtgKARViasmXlAAs96YkAboRF0oJ7X/Tt5HGuVJJqej2Vl0reWQzAwvxTHeGYYUMc514H6+GSvJHOu3KGSX86DEBvLmBLIirVTMXVem55LmBG/DL1Nu9S/QNttwB5uDrJIZoi8jmEw/kl0j0IcROkIxMIvKaPr8elgcCK37a1jgbGVAIyeVaw4leKFdG+zWVVeNUegmgT2tSP9/iT8tt8um3M2RyxnhIUVcQeqYxervfCG/XbAUbQlCwl102WGnscz55LIeK9cmkPa+ukLuFQGPWe2SbCYO7OKKrmKmFo3lBKV8iRLpafZ46oAzNSil+Nj0JCO3ksKmcrnlEJ4C9c4URaSZpl5PzdommSxWmhBG/m7y/Mwm/GBGkzosoNL98rxeHqr+Oa8Fk8pkAAsHHGIUOJxGvgbzf3m5jtvCMWGhVEZQ61QXKiRd/rb0f");
        token.setHeader(tokenHeader);
        token.setSignature("MIAGCSqGSIb3DQEHAqCAMIACAQExDzANBglghkgBZQMEAgEFADCABgkqhkiG9w0BBwEAAKCAMIIB0zCCAXkCAQEwCQYHKoZIzj0EATB2MQswCQYDVQQGEwJVUzELMAkGA1UECAwCTkoxFDASBgNVBAcMC0plcnNleSBDaXR5MRMwEQYDVQQKDApGaXJzdCBEYXRhMRIwEAYDVQQLDAlGaXJzdCBBUEkxGzAZBgNVBAMMEmQxZHZ0bDEwMDAuMWRjLmNvbTAeFw0xOTA3MjUxNjI3NDlaFw0zOTA3MjAxNjI3NDlaMHYxCzAJBgNVBAYTAlVTMQswCQYDVQQIDAJOSjEUMBIGA1UEBwwLSmVyc2V5IENpdHkxEzARBgNVBAoMCkZpcnN0IERhdGExEjAQBgNVBAsMCUZpcnN0IEFQSTEbMBkGA1UEAwwSZDFkdnRsMTAwMC4xZGMuY29tMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAErnHhPM18HFbOomJMUiLiPL7nrJuWvfPy0Gg3xsX3m8q0oWhTs1QcQDTT+TR3yh4sDRPqXnsTUwcvbrCOzdUEeTAJBgcqhkjOPQQBA0kAMEYCIQCXW2kjA36LdnEqc0qHb82FIYShdEk3hgPGxZDf6PfXygIhANrrpo/SY2YorozC73ZuQDtsGK3PHTdQZja5AgdEXy0yAAAxggFUMIIBUAIBATB7MHYxCzAJBgNVBAYTAlVTMQswCQYDVQQIDAJOSjEUMBIGA1UEBwwLSmVyc2V5IENpdHkxEzARBgNVBAoMCkZpcnN0IERhdGExEjAQBgNVBAsMCUZpcnN0IEFQSTEbMBkGA1UEAwwSZDFkdnRsMTAwMC4xZGMuY29tAgEBMA0GCWCGSAFlAwQCAQUAoGkwGAYJKoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTkwNzI2MTYxNDAyWjAvBgkqhkiG9w0BCQQxIgQgbmzGcR3bTze2zqaMtSu/QbfOwUzfmhaP4Wglfgbg7OUwCgYIKoZIzj0EAwIESDBGAiEAo2WF2FOLp1cN5rxiJsLuaVfgtT6hiVZvJENps+WNWLYCIQCe2dcNpPXN8+DHm01uVFIF6JJzPapGa44Y3USa2GeTowAAAAAAAA==");
        token.setVersion("EC_v1");
        token.setApplicationData("VEVTVA==");
        token.setMerchantIdentifier("PPO004.SandBoxApp");

        Address address = new Address();
        address.setAddressLine1("sssss");
        address.setState("NY");
        address.setCity("New York");
        address.setCountry("US");
        address.setZip("11747");

        TransactionRequest request = new TransactionRequest()
                .paymentMethod(PaymentMethod.THREEDS.getValue())
                .overrideAmount("1500")
                .threeDomainSecureToken(token)
                .billing(address);

        return request;
    }
}
