package com.example.microservice.orders.microservice.orders.utils;

public class Validation {
    public static boolean isPositiveNumber(Long number) {
        return number != null && number > 0;
    }
    public static boolean isPositiveOrZeroNumber(Integer number) {
        return number != null && number >= 0;
    }
    public static boolean isFullString(String str) {
        return str != null && !str.isEmpty();
    }
}
