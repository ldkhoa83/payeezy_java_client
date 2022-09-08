package com.firstdata.payeezy.models.transaction;

public enum TransactionType {
        PURCHASE,
        AUTHORIZE,
        CAPTURE,
        REFUND,
        VOID,
        SPLIT,
        RECURRING,
        ORDER,
        FORCED_POST,
        CASHOUT,
        ACTIVATION,
        BALANCE_INQUIRY,
        RELOAD,
        DEACTIVATION,
        CREDIT,
        SCORE_ONLY,
        AUTHORIZE_SCORE,
        PURCHASE_SCORE,
        PL_BALANCE_INQUIRY,
        PL_PROMO_DETAILS;

        TransactionType(){}

        private String value;


        public static final String PURCHASE_VALUE = "purchase";
        public static final String SPLIT_VALUE = "split";
        public static final String AUTHORIZE_VALUE = "authorize";
        public static final String CAPTURE_VALUE = "capture";
        public static final String FORCED_POST_VALUE = "forced_post";
        public static final String RECURRING_VALUE = "recurring";
        public static final String CREDIT_VALUE = "credit";
        public static final String REFUND_VALUE = "refund";
        public static final String RELOAD_VALUE = "reload";
        public static final String CASHOUT_VALUE = "cashout";
        public static final String VOID_VALUE = "void";


        static {
                SPLIT.value = SPLIT_VALUE;
                AUTHORIZE.value = AUTHORIZE_VALUE;
                CAPTURE.value = CAPTURE_VALUE;
                FORCED_POST.value = FORCED_POST_VALUE;
                RECURRING.value = RECURRING_VALUE;
                CREDIT.value = CREDIT_VALUE;
                REFUND.value = REFUND_VALUE;
                RELOAD.value = RELOAD_VALUE;
                CASHOUT.value = CASHOUT_VALUE;
                VOID.value = VOID_VALUE;
                PURCHASE.value = PURCHASE_VALUE;
        }

        public String getValue(){
                return this.value;
        }
    }

