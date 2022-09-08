package com.firstdata.payeezy.models.enrollment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true)
public class ACHPayRequest {

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("method")
    private String paymentMethod;

    @JsonProperty("subscriber_id")
    private String subscriberId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency_code")
    private String currency;

    @JsonProperty("connectpay_payment_number")
    private String connectpayPaymentNumber;

    @JsonProperty("pin_data")
    private String pin;

    @JsonProperty("merchant_transaction_id")
    private String merchantTransactionId;

    @JsonProperty("recurring_payment")
    private String recurringPayment;

    @JsonProperty("credit_debit_indicator")
    private String creditDebitIndicator;

    @JsonProperty("application")
    private EnrollmentApp application;
}
