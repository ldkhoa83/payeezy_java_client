package com.firstdata.payeezy.models.transaction;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EventQuery {

    private String from;
    private String to;
    @Builder.Default
    private Integer offset = 0;
    @Builder.Default
    private Integer limit = 0;

}
