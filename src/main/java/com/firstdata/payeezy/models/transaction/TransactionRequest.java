package com.firstdata.payeezy.models.transaction;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;
import lombok.experimental.Accessors;


/**
 * com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "transaction_type" 
 * (class com.firstdata.firstapi.service.transaction.payload.TransactionRequest), 
 * not marked as ignorable (11 known properties: "threeDomainSecureToken", "paymentMethod", "referenceNo", "descriptor", "card", "currency", 
 * "transactionType", "amount", "id", "billing", "transactionTag"])
 * @author f2ivckd
 *
 */

@JsonAutoDetect(getterVisibility= Visibility.DEFAULT,setterVisibility= Visibility.DEFAULT,fieldVisibility= Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
@Setter
@Getter
@Accessors(fluent = true)
public class TransactionRequest {

	public TransactionRequest() {
	}

	@JsonProperty("type")
	private String type;

	private String bin;

	@JsonProperty("transaction_type")
	private String transactionType;

	@JsonProperty("merchant_ref")
	private String referenceNo;

	@JsonProperty("mcc")
	private String mcc;
	
	@JsonProperty("method")
	private String paymentMethod;
	@JsonProperty("amount")
	private String amount;
	@JsonProperty("override_amount")
	private String overrideAmount;
	@JsonProperty("currency_code")
	private String currency;
	@JsonProperty("transaction_tag")
	private String transactionTag;

	@JsonProperty("credit_card")
	private Card card;
	@JsonProperty("token")
	private Token token;
	@JsonProperty("billing_address")
	private Address billing;
	
	@JsonProperty("split_tender_id")
	private String splitTenderId;
	
	@JsonProperty("soft_descriptors")
	private SoftDescriptor descriptor;
	
	@JsonProperty("split_shipment")
    private String splitShipment;
	
	@JsonProperty("3DS")
	private ThreeDomainSecureToken threeDomainSecureToken;
	
	@JsonProperty("eci_indicator")
    private String eciIndicator;
    
    @JsonProperty("level2")
    private Level2Properties level2Properties;
    
    @JsonProperty("level3")
    private Level3Properties level3Properties;
    
    @JsonProperty("paypal_transaction_details")
    private PaypalTransactionDetail paypal;
    

    @JsonProperty("tele_check")
	private TeleCheck teleCheck;
    
    @JsonProperty("valuelink")
    private ValueLink valuelink;
    

    @JsonProperty("direct_debit")
    private DebitCard debitCard;
    
    @JsonProperty("recurring_id")
    private String recurringId;
    
    // pass thru value for split tenders
    @JsonProperty("partial_redemption")
	private String partialRedemption;

    @JsonProperty("recurring")
    private Boolean recurring = Boolean.FALSE;

	@JsonProperty("request_origin")
	private TransactionRequestOrigin requestOrigin;

	private String origin;

	@JsonProperty("coupon")
	private CouponDetails couponDetails;

	@JsonProperty("loyalty")
	private Loyalty loyaltyDetails;

	@JsonProperty("member")
	private Member memberDetails;

	@JsonProperty("order_details")
	private OrderDetails orderDetails;

	@JsonProperty("sales_rep")
	private String salesRep;

	@JsonProperty("sales_ref_code")
	private String refCode;

	@JsonProperty("gift_message")
	private String giftMessage;

	@JsonProperty("sales_channel")
	private String salesChannel;

	@JsonProperty("gift_registry")
	private GiftRegistry giftRegistry;

	@JsonProperty("rate_reference")
	private RateReference rateReference;
	
	@JsonProperty("original_details")
	private OriginalDetails originalDetails;
	
	@JsonProperty("reversal_id")
	private String reversalId;

	@JsonProperty("post_date")
	private String postDate;

	@JsonProperty("gift_deposit_available")
	private String giftDepositAvailable;

	@JsonProperty("additional_shipping_details")
	private AdditionalShippingInfo additionalShipping;

	@JsonProperty("reference_3")
	private String reference3;

	@JsonProperty("user_name")
	private String userName;

	@JsonProperty("ach")
	private Ach ach;

	@JsonProperty("stored_credentials")
	private StoredCredentials storedCredentials;

	@JsonProperty("auth")
	private Boolean auth;

	@JsonProperty("ta_token")
	private String taToken;

}
