package ru.test.wallet.web;


import static ru.test.wallet.util.PurchasesUtil.DEFAULT_PRICE_LIMIT_DAY;

public class SecurityUtil {

    private static int authUserId=1;

    public static int authUserId() {
        return authUserId;
    }

    public static void setAuthUserId(int authUserId) {
        SecurityUtil.authUserId = authUserId;
    }

    public static int authUserPriceLimitDay() {
        return DEFAULT_PRICE_LIMIT_DAY;
    }
}