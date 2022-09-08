package com.firstdata.payeezy.samples;

import com.firstdata.payeezy.JSONHelper;
import com.firstdata.payeezy.PayeezyClientHelper;
import com.firstdata.payeezy.models.transaction.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ECheckSampleTest {

    @Autowired
    private PayeezyClientHelper payeezyClientHelper;

    /*
     * Use this method to do transactions via the Telecheck® service.
     * Supports only manual entry of routing and account.
     * Requires First Data Acquiring Account. Supported transaction is Purchase.
     * For ACH token and instant DDA validation, look at
     * the ConnectPay℠ – ACH on File solution section on the /apis page.*/
    @Test
    public void givenTeleCheck_WhenPurchase_ThenTransactionIsSuccessful() throws Exception {
        TransactionRequest request = getECheckPurchaseRequest();

        PayeezyResponse response = payeezyClientHelper.doPrimaryTransaction(request);
        System.out.println(response.getResponseBody());
    }


    @Test
    public void givenPurchasedTransactionByTeleCheck_WhenVoid_ThenTransactionIsVoided() throws Exception {
        TransactionRequest request = getECheckPurchaseRequest();

        PayeezyResponse response = payeezyClientHelper.doPrimaryTransaction(request);
        System.out.println(response.getResponseBody());
        TransactionResponse transactionResponse = new JSONHelper().fromJson(response.getResponseBody(), TransactionResponse.class) ;

        request.transactionType("void");
        response = payeezyClientHelper.doSecondaryTransaction(transactionResponse.getTransactionId(), request);
        System.out.println(response.getResponseBody());
    }
    @Test
    public void givenTeleCheckWithPurchasedTrans_WhenVoidWithTag_ThenTransactionIsVoided() throws Exception {
        TransactionRequest request = getECheckPurchaseRequest();

        PayeezyResponse response = payeezyClientHelper.doPrimaryTransaction(request);
        System.out.println(response.getResponseBody());
        TransactionResponse transactionResponse = new JSONHelper().fromJson(response.getResponseBody(), TransactionResponse.class) ;

        request = new TransactionRequest()
                .paymentMethod(PaymentMethod.TELE_CHECK.getValue())
                .transactionType(TransactionType.VOID.getValue())
                .amount("1000")
                .currency("USD")
                .transactionTag(transactionResponse.getTransactionTag());

        response = payeezyClientHelper.doSecondaryTransaction(transactionResponse.getTransactionId(), request);
        System.out.println(response.getResponseBody());
    }

    private static TransactionRequest getECheckPurchaseRequest(){
        TeleCheck teleCheck = new TeleCheck();
        teleCheck.setCheckNumber("0101");
        teleCheck.setCheckType("C");
        teleCheck.setBankRoutingNumber("BN1234567801234567890");
        teleCheck.setBankAccountNumber("17101874");
        teleCheck.setAccountholderName("John Smith");
        teleCheck.setCustomerIdType("1");
        teleCheck.setCustomerIdNumber("c");
        teleCheck.setClientEmail("abc@email.com");
        teleCheck.setGiftCardAmount("100");
        teleCheck.setVip("n");
        teleCheck.setClerkId("RVK_001");
        teleCheck.setDeviceId("c");
        teleCheck.setReleaseType("X");
        teleCheck.setRegistrationNumber("12345");
        teleCheck.setRegistrationDate("2014-01-01");
        teleCheck.setDateOfBirth("1983-01-01");

        TransactionRequest transRequest = new TransactionRequest()
                .paymentMethod(PaymentMethod.TELE_CHECK.getValue())
                .transactionType(TransactionType.PURCHASE.getValue())
                .amount("1000")
                .currency("USD")
                .teleCheck(teleCheck);

        return transRequest;
    }
}
