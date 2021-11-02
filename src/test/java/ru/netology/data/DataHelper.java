package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    private static final String approvedCard = "4444 4444 4444 4441";
    private static final String declinedCard = "4444 4444 4444 4442";
    private static final Faker fakerEn = new Faker(new Locale("en-US"));

    @Value
    public static class PaymentInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String cardHolder;
        private String cvc;
    }

    public static PaymentInfo usingApprovedCard(int MonthsToAdd) {
        return new PaymentInfo(approvedCard, expiryMonth(MonthsToAdd), expiryYear(MonthsToAdd),
                getRandomName(), getRandomCVC());
    }

    public static PaymentInfo usingDeclinedCard(int MonthsToAdd) {
        return new PaymentInfo(declinedCard, expiryMonth(MonthsToAdd), expiryYear(MonthsToAdd),
                getRandomName(), getRandomCVC());
    }

    public static LocalDate expiryDate(int MonthsToAdd) {
        var expiryDate = LocalDate.now().plusMonths(MonthsToAdd);
        return expiryDate;
    }

    public static String expiryYear(int MonthsToAdd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        var year = expiryDate(MonthsToAdd).format(formatter);
        return year;
    }

    public static String expiryMonth(int MonthsToAdd) {
        var month = expiryDate(MonthsToAdd).getMonthValue();
        if (month < 10) {
            String monthFormat = "0" + Integer.toString(month);
            return monthFormat;
        }
        return Integer.toString(month);
    }

    public static int randomPlusMonths() {
        Random random = new Random();
        int MonthsToAdd = random.nextInt(60);
        return MonthsToAdd;
    }

    public static String getRandomName() {
        String name = fakerEn.name().fullName();
        return name;
    }

    public static String getRandomCVC() {
        String cvc = fakerEn.numerify("###");
        return cvc;
    }

    public static String getRandomLatinSymbols() {
        String cyrillicSymbols = fakerEn.bothify("?????????");
        return cyrillicSymbols;
    }

    public static String getRandomCyrillicSymbols() {
        Faker fakerRu = new Faker(new Locale("ru"));
        String cyrillicSymbols = fakerRu.name().firstName();
        return cyrillicSymbols;
    }

    public static String getRandomCyrillicFullName() {
        Faker fakerRu = new Faker(new Locale("ru"));
        String cyrillicSymbols = fakerRu.name().fullName();
        return cyrillicSymbols;
    }
}
