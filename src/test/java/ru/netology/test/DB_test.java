package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DB_test {
    PurchasePage purchasePage;

    @BeforeAll
    static void allureSetup() {
        SelenideLogger.addListener("allure", new AllureSelenide().
                screenshots(true).savePageSource(false));
    }

    @BeforeEach
    void browserSetUp() {
        open("http://localhost:8080/");
        purchasePage = new PurchasePage();
    }

    @AfterAll
    static void tearDownAllure() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should not save credit ID on Payment page")
    void shouldNotSaveCreditIdOnPaymentPage() throws InterruptedException {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.anyNotification();
        assertEquals("null", DbHelper.getCreditId());
    }

    @Test
    @DisplayName("Should not save credit ID on Credit page")
    void shouldNotSaveCreditIdOnCreditPage() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.anyNotification();
        assertEquals("null", DbHelper.getCreditId());
    }

    @Test
    @DisplayName("Should save payment with approved card as approved")
    void shouldSavePaymentWithApprovedCardAsApproved() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.anyNotification();
        assertEquals("APPROVED", DbHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Should save payment with declined card as declined")
    void shouldSavePaymentWithDeclinedCardAsDeclined() {
        var paymentPage = purchasePage.payWithCard();
        var declinedPayment = DataHelper.usingDeclinedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvc());
        paymentPage.anyNotification();
        assertEquals("DECLINED", DbHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Should save credit with approved card as approved")
    void shouldSaveCreditWithApprovedCardAsApproved() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.anyNotification();
        assertEquals("APPROVED", DbHelper.getCreditStatus());
    }

    @Test
    @DisplayName("Should save credit with declined card as declined")
    void shouldSaveCreditWithDeclinedCardAsDeclined() {
        var creditPage = purchasePage.payWithCredit();
        var declinedPayment = DataHelper.usingDeclinedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvc());
        creditPage.anyNotification();
        assertEquals("DECLINED", DbHelper.getCreditStatus());
    }
}
