package com.firstdata.payeezy.samples;

import com.firstdata.payeezy.PayeezyClientHelper;
import com.firstdata.payeezy.models.transaction.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GiftCardSampleTest {

    @Autowired
    private PayeezyClientHelper payeezyClientHelper;


    @Test
    public void givenGiftCard_WhenCashout_ThenTrasactionIsApproved() throws Exception {
        TransactionRequest request = getGiftCardTransRequest();
        request.transactionType(TransactionType.CASHOUT.getValue());

        PayeezyResponse response = payeezyClientHelper.doPrimaryTransaction(request);
        System.out.println(response.getResponseBody());

    }

    @Test
    public void givenGiftCard_WhenReload_ThenTrasactionIsApproved() throws Exception {
        TransactionRequest request = getGiftCardTransRequest();
        request.transactionType(TransactionType.RELOAD.getValue())
                .amount("5000")
                .currency("USD");

        PayeezyResponse response = payeezyClientHelper.doPrimaryTransaction(request);
        System.out.println(response.getResponseBody());

    }

    @Test
    public void givenGiftCard_WhenPurchase_ThenTrasactionIsApproved() throws Exception {
        TransactionRequest request = getGiftCardTransRequest();
        request.transactionType(TransactionType.PURCHASE.getValue())
                .amount("3000")
                .currency("USD")
                .postDate("04032022")
                .reference3("svspin");

        PayeezyResponse response = payeezyClientHelper.doPrimaryTransaction(request);
        System.out.println(response.getResponseBody());

    }

    private static TransactionRequest getGiftCardTransRequest() {
        ValueLink valueLink = new ValueLink();
        valueLink.setCardHoldersName("Joe Smith");
        valueLink.setCardNumber("7777045839985463");

        TransactionRequest request = new TransactionRequest()
                .paymentMethod(PaymentMethod.VALUELINK.getValue())
                .valuelink(valueLink);
        return request;
    }
}
