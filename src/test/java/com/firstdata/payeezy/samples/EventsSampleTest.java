package com.firstdata.payeezy.samples;

import com.firstdata.payeezy.PayeezyClientHelper;
import com.firstdata.payeezy.models.transaction.EventQuery;
import com.firstdata.payeezy.models.transaction.PayeezyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventsSampleTest {

    @Autowired
    private PayeezyClientHelper payeezyClientHelper;

    @Test
    public void whenAskingTop10EventsInAMonth_ThenEventsAreReturnedSuccessfully(){
        EventQuery eventQuery = EventQuery.builder()
                .from("2022-08-01")
                .to("2022-09-01")
                .limit(10)
                .build();

        PayeezyResponse response = this.payeezyClientHelper.getEvents(eventQuery);
        System.out.println(response.getResponseBody());
    }

}
