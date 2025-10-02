package com.samsung.data_static;

public class OrderStatus {
    public static final String PENDING = "PENDING";
    public static final String SUCCESS = "SUCCESS";
    public static final String CANCELED = "CANCELED";
    public static final String STOCK_RESERVED = "STOCK_RESERVED";
    public static final String STOCK_FAILED = "STOCK_FAILED";
    public static final String PAYMENT_SUCCESS = "PAYMENT_SUCCESS";
    public static final String PAYMENT_FAILED = "PAYMENT_FAILED";
    public static final String REFUND_MONEY = "REFUND_MONEY";
    public static final String RETURN_STOCK = "RETURN_STOCK";

    // Ngăn không cho tạo instance class này
    private OrderStatus() {}
}
