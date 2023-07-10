package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherLoginAuthInfo() {
        return new AuthInfo("qwerqwer", "qwerty123");
    }

    public static AuthInfo getOtherPasswordAuthInfo() {
        return new AuthInfo("vasya", "open123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("david", "qwert1234");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static VerificationCode getNotVerificationCode(AuthInfo authInfo) {
        return new VerificationCode("54321");
    }

    public static class CardsNumber {
        private static String card1 = "5559 0000 0000 0001";
        private static String card2 = "5559 0000 0000 0002";
        private static String notNumberCard = "1111 2222 3333 4444";
        private static String[] cards = {card1, card2, notNumberCard};

        public static String getNumberCard(int number) {
            return cards[number - 1];
        }
    }

    private static int transferAmount = 200;
    private static int negativeTransferAmount = 50000;

    public static int getTransferAmount() {
        return transferAmount;
    }

    public static int getNegativeTransferAmount() {
        return negativeTransferAmount;
    }

}
