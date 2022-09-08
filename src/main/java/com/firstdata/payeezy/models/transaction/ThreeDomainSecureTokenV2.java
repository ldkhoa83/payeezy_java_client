package com.firstdata.payeezy.models.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true)
public class ThreeDomainSecureTokenV2 extends ThreeDomainSecureToken{

    @JsonProperty("program_protocol")
    private String programProtocol;

    @JsonProperty("directory_server_transaction_id")
    private String directoryServerTransId;
}
