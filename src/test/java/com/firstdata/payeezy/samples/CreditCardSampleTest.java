package com.firstdata.payeezy.samples;

import com.firstdata.payeezy.JSONHelper;
import com.firstdata.payeezy.PayeezyClientHelper;
import com.firstdata.payeezy.models.transaction.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Example to illustrate an authorization and reversal(void) using a credit card
 */
@SpringBootTest
public class CreditCardSampleTest{

    @Autowired
    PayeezyClientHelper payeezyClientHelper;

    @Test
    public void givenCreditCard_WhenAuthorizeAndCapture_ThenTransactionIsSuccessful() throws Exception {
        TransactionRequest transactionRequest = getPrimaryTransRequest();

        PayeezyResponse payeezyResponse = payeezyClientHelper.doPrimaryTransaction(transactionRequest);
        System.out.println(payeezyResponse.getResponseBody());
        TransactionResponse transactionResponse = new JSONHelper().fromJson(payeezyResponse.getResponseBody(), TransactionResponse.class) ;
        // Capture the credit card authorization
        TransactionRequest captureRequest = new TransactionRequest()
                .transactionTag(transactionResponse.getTransactionTag())
                .transactionType(TransactionType.CAPTURE.name().toLowerCase())
                .paymentMethod(PaymentMethod.CREDIT_CARD.getValue())
                .amount("100") // should always match the request amount
                .currency("USD");
        PayeezyResponse captureResponse = payeezyClientHelper.doSecondaryTransaction(transactionResponse.getTransactionId(), captureRequest);
        System.out.println(captureResponse.getResponseBody());

    }

    @Test
    public void givenCreditCard_WhenAuthorizingAndSplitShipment_ThenTransactionIsSuccessful() throws Exception {
        TransactionRequest transactionRequest = getPrimaryTransRequest();

        PayeezyResponse payeezyResponse = payeezyClientHelper.doPrimaryTransaction(transactionRequest);
        System.out.println(payeezyResponse.getResponseBody());
        TransactionResponse transactionResponse = new JSONHelper().fromJson(payeezyResponse.getResponseBody(), TransactionResponse.class) ;

        TransactionRequest splitRequest = new TransactionRequest()
                .transactionTag(transactionResponse.getTransactionTag())
                .transactionType(TransactionType.SPLIT_VALUE)
                .paymentMethod(PaymentMethod.CREDIT_CARD.getValue())
//              .amount("1299"); // TOO MUCH greater than authorized amount, 100
                .amount("199")
                .splitShipment("01/01")
                .currency("USD");

        PayeezyResponse splitResponse = payeezyClientHelper.doSecondaryTransaction(transactionResponse.getTransactionId(), splitRequest);
        System.out.println(splitResponse.getResponseBody());
    }
    @Test
    public void givenCreditCard_WhenAuthorizedAndForcingPost_ThenTransactionBeingCapturedSuccessfully() throws Exception {
        TransactionRequest transactionRequest = getPrimaryTransRequest();

        PayeezyResponse payeezyResponse = payeezyClientHelper.doPrimaryTransaction(transactionRequest);
        System.out.println(payeezyResponse.getResponseBody());
        TransactionResponse transactionResponse = new JSONHelper().fromJson(payeezyResponse.getResponseBody(), TransactionResponse.class) ;

        TransactionRequest forcedPostRequest = new TransactionRequest()
                .transactionType(TransactionType.FORCED_POST_VALUE)
                .paymentMethod(PaymentMethod.CREDIT_CARD.getValue())
                .amount("1299") //BUG ???
                .currency("USD")
                //Have to have CARD info
                .card(new Card().setName("Not Provided").setType("visa").setCvv("123").setExpiryDt("1030").setNumber("4012000033330026"));

        PayeezyResponse forcedPostResponse = payeezyClientHelper.doSecondaryTransaction(transactionResponse.getTransactionId(), forcedPostRequest);
        System.out.println(forcedPostResponse.getResponseBody());
    }

    @Test
    public void givenCreditCard_WhenRequestARecurringStoredCredentials_ThenTransactionBeingAuthorizedSuccessfully() throws Exception {
        TransactionRequest transactionRequest = getPrimaryStoredTransRequest();
        PayeezyResponse payeezyResponse = payeezyClientHelper.doPrimaryTransaction(transactionRequest);
        System.out.println(payeezyResponse.getResponseBody());
    }
    @Test
    public void givenCreditCardAndFirstRecurring_WhenSubsequenceRecurring_ThenTransactionBeingAuthorizedSuccessfully() throws Exception {
        TransactionRequest transactionRequest = getPrimaryStoredTransRequest();

        PayeezyResponse payeezyResponse = payeezyClientHelper.doPrimaryTransaction(transactionRequest);
        System.out.println(payeezyResponse.getResponseBody());

        StoredCredentials storedCredentials = new StoredCredentials();
        storedCredentials.setInitiator(InitiatorType.CARDHOLDER.name());
        storedCredentials.setSequence(SequenceType.SUBSEQUENT.name());
        storedCredentials.setScheduled(true);
        storedCredentials.setCardBrandOriginalTransactionId("759196687838957"); // HARD CORD number
        transactionRequest.storedCredentials(storedCredentials).recurring(true);

        payeezyResponse = payeezyClientHelper.doPrimaryTransaction(transactionRequest);
        System.out.println(payeezyResponse.getResponseBody());
    }

    @Test
    public void givenGermanDebitCard_WhenRequestPayment_ThenTransactionBeingAuthorizedSuccessfully() throws Exception {
        TransactionRequest transRequest = getGermanDebitRequest();

        PayeezyResponse payeezyResponse = payeezyClientHelper.doPrimaryTransaction(transRequest);
        System.out.println(payeezyResponse.getResponseBody());
    }

    @Test
    public void givenTokenizedCreditCard_WhenAuthorize_ThenTransactionIsSuccessful() throws Exception {
        TransactionRequest transRequest = getTokenRequest();

        PayeezyResponse payeezyResponse = payeezyClientHelper.doTokenization(transRequest);
        System.out.println(payeezyResponse.getResponseBody());

        //In reality, have to get back token info

        transRequest = getTokenBasedPaymentRequest();
        transRequest.transactionType(TransactionType.AUTHORIZE_VALUE);

        payeezyResponse = payeezyClientHelper.doPrimaryTransaction(transRequest);
        System.out.println(payeezyResponse.getResponseBody());
    }

    @Test
    public void givenTokenizedCreditCard_WhenUsing3DSToPurchase_ThenTransactionIsSuccessful() throws Exception {
        // ??? WHERE to get this 3DS info
        ThreeDomainSecureToken threeDomainSecureToken = new ThreeDomainSecureToken();
        threeDomainSecureToken.setThreeDomainType("D");
        threeDomainSecureToken.setCavv("01ade6ae340005c681c3a1890418b53000020000");

        Address billingAddress = new Address();
        billingAddress.setState("NY");
        billingAddress.setAddressLine1("sss");
        billingAddress.setCity("New York");
        billingAddress.setZip("11747");

        TransactionRequest transRequest = getTokenBasedPaymentRequest();
        transRequest
                .transactionType(TransactionType.PURCHASE.getValue())
                .paymentMethod(PaymentMethod.THREEDS.getValue())
                .amount("100")
                .currency("USD")
                .threeDomainSecureToken(threeDomainSecureToken)
                .billing(billingAddress);

        PayeezyResponse payeezyResponse = payeezyClientHelper.doPrimaryTransaction(transRequest);
        System.out.println(payeezyResponse.getResponseBody());
    }









    private static TransactionRequest getPrimaryTransRequest() {
        TransactionRequest transactionRequest = new TransactionRequest()
                .amount("100") // always set the amouunt in cents
                .transactionType(TransactionType.AUTHORIZE.name().toLowerCase())
                .paymentMethod(PaymentMethod.CREDIT_CARD.getValue())
                .referenceNo(""+System.currentTimeMillis()) // this is your order number
                .currency("USD");
        // set the credit card info
        Card card = new Card().setName("Not Provided").setType("visa").setCvv("123").setExpiryDt("1030").setNumber("4012000033330026");
        transactionRequest.card(card);
        Address billingAddress = new Address();
        billingAddress.setState("NY");
        billingAddress.setAddressLine1("sss");
        billingAddress.setCity("New York");
        billingAddress.setZip("11747");
        billingAddress.setCountry("US");
        transactionRequest.billing(billingAddress);
        return transactionRequest;
    }

    private static TransactionRequest getPrimaryStoredTransRequest(){
        TransactionRequest transactionRequest = new TransactionRequest()
                .amount("9200") // always set the amouunt in cents
                .transactionType(TransactionType.RECURRING_VALUE)
                .paymentMethod(PaymentMethod.CREDIT_CARD.getValue())
                .referenceNo(""+System.currentTimeMillis()) // this is your order number
                .currency("USD");
        // set the credit card info
        Card card = new Card().setName("Not Provided").setType("visa").setCvv("123").setExpiryDt("1030").setNumber("4012000033330026");
        transactionRequest.card(card);

        StoredCredentials storedCredentials = new StoredCredentials();
        storedCredentials.setInitiator(InitiatorType.MERCHANT.name());
        storedCredentials.setSequence(SequenceType.FIRST.name());
        storedCredentials.setScheduled(true);
        transactionRequest.storedCredentials(storedCredentials);

        return transactionRequest;
    }

    private static TransactionRequest getGermanDebitRequest(){
        DebitCard debitCard = new DebitCard();
        debitCard.setIban("DE34500100600032121604");
        debitCard.setMandateReference("ABCD1234");
        debitCard.setBic("PBNKDEFFXXX");
        TransactionRequest transactionRequest = new TransactionRequest()
                .paymentMethod(PaymentMethod.DIRECT_DEBIT.getValue())
                .transactionType(TransactionType.CREDIT_VALUE)
                .amount("100")
                .currency("EUR")
                .recurring(null)
                .debitCard(debitCard);

        Phone phone = new Phone();
        phone.setNumber("49 89 2112400");
        phone.setType("Cell");
        Address billingAddress = new Address();
        billingAddress.setName("John Smith");
        billingAddress.setCity("Munich");
        billingAddress.setAddressLine1("sss");
        billingAddress.setCountry("Germany");
        billingAddress.setEmail("ssss@payeezy.com");
        billingAddress.setPhoneNumber(phone);
        billingAddress.setZip("80331");
        billingAddress.setState("Neufdsa");

        transactionRequest.billing(billingAddress);

        return transactionRequest;
    }

    private static TransactionRequest getTokenRequest(){
        Card card = new Card();
        card.setType("visa");
        card.setCvv("123");
        card.setExpiryDt("1030");
        card.setNumber("4788250000028291");
        card.setName("JohnSmith");

        TransactionRequest transactionRequest = new TransactionRequest()
                .auth(false)
                .taToken("NOIW")
                .type(TokenType.FDToken.name())
                .card(card);

        return transactionRequest;
    }

    private static TransactionRequest getTokenBasedPaymentRequest(){
        Transarmor transarmor = new Transarmor();
        transarmor.setType("visa");
        transarmor.setName("JohnSmith");
        transarmor.setExpiryDt("1030");
        transarmor.setValue("1537446225198291");

        Token token = new Token();
        token.setTokenData(transarmor);
        token.setTokenType(TokenType.FDToken.name());

        TransactionRequest transactionRequest = new TransactionRequest()
                .token(token)
                .referenceNo("Astonishing-sale")
                .paymentMethod("token")
                .amount("200")
                .currency("USD");

        return transactionRequest;
    }






}
