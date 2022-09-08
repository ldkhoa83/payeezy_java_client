package com.firstdata.payeezy.models.transaction;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(getterVisibility= JsonAutoDetect.Visibility.NONE,setterVisibility= JsonAutoDetect.Visibility.NONE,fieldVisibility= JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StoredCredentials {

    @JsonProperty("sequence")
    private String sequence;

    @JsonProperty("initiator")
    private String initiator;

    @JsonProperty("is_scheduled")
    private Boolean isScheduled = Boolean.FALSE;

    @JsonProperty("cardbrand_original_transaction_id")
    private String cardBrandOriginalTransactionId;

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public Boolean getScheduled() {
        return isScheduled;
    }

    public void setScheduled(Boolean scheduled) {
        isScheduled = scheduled;
    }

    public String getCardBrandOriginalTransactionId() {
        return cardBrandOriginalTransactionId;
    }

    public void setCardBrandOriginalTransactionId(String cardBrandOriginalTransactionId) {
        this.cardBrandOriginalTransactionId = cardBrandOriginalTransactionId;
    }
}
