package com.firstdata.payeezy.samples;

import com.firstdata.payeezy.PayeezyClientHelper;
import com.firstdata.payeezy.models.enrollment.ACHPayRequest;
import com.firstdata.payeezy.models.enrollment.EnrollmentApp;
import com.firstdata.payeezy.models.enrollment.EnrollmentRequest;
import com.firstdata.payeezy.models.transaction.PayeezyResponse;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ACHSampleTest {

    @Autowired
    private PayeezyClientHelper payeezyClientHelper;


    @Test
    public void givenACHConnectEstablished_WhenConsumerEnrolls_ThenEnrollmentIsApproved() throws Exception {
        EnrollmentRequest request = getACHEnrollmentRequest();

        PayeezyResponse response = payeezyClientHelper.enrollInACH(request);
        System.out.println(response.getResponseBody());
    }

    @Test
    public void givenACHConnectEstablished_WhenPurchaseWithoutPaymentForm_ThenTransactionIsDeclined() throws Exception {
        ACHPayRequest request = getACHPaymentRequest();

        PayeezyResponse response = payeezyClientHelper.payACHPayment(request);
        System.out.println(response.getResponseBody());
    }

    //TODO update, close enrollment profile


    private static EnrollmentRequest getACHEnrollmentRequest(){
        EnrollmentRequest.EnrollmentUser user = new EnrollmentRequest.EnrollmentUser();
        user.setAccountFlag("personal_check");
        user.setAccountNumber("******7890");
        user.setRoutingNumber("*****1020");
        user.setDriverLicenseNumber("84208001");
        user.setDriverLicenseState("TX");
        user.setSocialSecurityNumber("999999999");

        EnrollmentRequest.AdditionalPersonalInfo addPersonalInfo = new EnrollmentRequest.AdditionalPersonalInfo();
        addPersonalInfo.setDateOfBirth("19860126");
        addPersonalInfo.setGender("M");
        addPersonalInfo.setMemberDate("20170510");
        addPersonalInfo.setTermsAndConditionVersion("4");

        EnrollmentApp app = new EnrollmentApp();
        app.setIpAddress("10.25.124.4");
        app.setTrueIpAddress("156.140.15.76");
        app.setImei("545254525452652");
        app.setOrganizationId("org54673");
        app.setSessionId("76ed6b08-224d-4f2e-9771-28cb5c9f26bd");

        com.firstdata.payeezy.models.enrollment.Address billingAddress = new
                com.firstdata.payeezy.models.enrollment.Address();
        billingAddress.setState("NY");
        billingAddress.setAddressLine1("sss");
        billingAddress.setCity("New York");
        billingAddress.setZip("11747");
        billingAddress.setCountry("US");

        EnrollmentRequest.Phone phone = new EnrollmentRequest.Phone();
        phone.setType("HOME");
        phone.setNumber("1111111111");
        phone.setPrimary("Y");

        EnrollmentRequest request = new EnrollmentRequest();
        request.setEnrollmentUser(user);
        request.setAdditionalPersonalInfo(addPersonalInfo);
        request.setEnrollmentApplication(app);
        request.setAddress(billingAddress);
        request.setPhones(Lists.newArrayList(phone));
        request.setTransactionType("enrollconsumer");
        request.setTransactionMethod("ach");
        request.setSubscriberId("44010067");
        request.setConnectpayPaymentNumber("1639635900123456789"); //After ConnectPay was established
        request.setOnlineBankTransactionId("1001508175");
        request.setFirstName("Oliver");
        request.setLastName("Queen");
        request.setEmail("arrow@email.com");
        request.setPin("1234");

        return request;
    }

    private static ACHPayRequest getACHPaymentRequest(){
        EnrollmentApp app = new EnrollmentApp();
        app.setOrganizationId("s0b500qh");
        app.setSessionId("3ca6ee0408028f118f102ed0b64779353789ebe3"); // ??? FROM WHERE

        ACHPayRequest request = new ACHPayRequest()
                .transactionType("purchase")
                .paymentMethod("ach")
                .subscriberId("36002056")
                .amount("1299")
                .currency("USD")
                .connectpayPaymentNumber("1639635000001693")
                .pin("0F0919C936F35E1A")
                .merchantTransactionId("999999999")
                //S or Null = Single/non-recurring Payment (one time sale)
                //R = Recurring Payment. Required when submitting recurring/bill payment transactions
                .recurringPayment("S")
                //"D"= Debit is used for all purchase/sale use cases.
                //If no value is sent we will default it to "D" to Debit.
                .creditDebitIndicator("D")
                .application(app);
        return request;
    }
}
