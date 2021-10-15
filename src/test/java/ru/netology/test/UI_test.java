package ru.netology.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;

public class UI_test {


    PurchasePage purchasePage;

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
        purchasePage = new PurchasePage();
    }

    @Test
    @DisplayName("Должен переключаться со страницы оплаты на страницу оформления кредита")
    void shouldSwitchFromPaymentPageToCreditPage() {
        var paymentPage = purchasePage.payWithCard();
        var creditPage = paymentPage.payWithCredit();
    }

    @Test
    @DisplayName("Должен переключаться со страницы кредита на страницу оплаты")
    void shouldSwitchFromCreditPageToPaymentPage() {
        var creditPage = purchasePage.payWithCredit();
        var paymentPage = creditPage.payWithDebit();
    }

    @Test
    @DisplayName("Должен заполнить форму и отправить платеж с действующей карты")
    void shouldFillFormAndPayWithApprovedCard() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.successfulPayment(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Должен заполнить форму и запросить кредит с действующей карты")
    void shouldFillFormAndRequestCreditWithApprovedCard() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.successfulCredit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    //падает, баг
    @Test
    @DisplayName("Должен заполнить форму и отправить платеж с заблокированной карты")
    void shouldFillFormAndPayWithDeclinedCard() {
        var paymentPage = purchasePage.payWithCard();
        var declinedPayment = DataHelper.usingDeclinedCard(DataHelper.randomPlusMonths());
        paymentPage.unsuccessfulPayment(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvc());
    }

    //падает, баг
    @Test
    @DisplayName("Должен заполнить форму и запросить кредит с заблокированной карты")
    void shouldFillFormAndRequestCreditWithDeclinedCard() {
        var creditPage = purchasePage.payWithCredit();
        var declinedPayment = DataHelper.usingDeclinedCard(DataHelper.randomPlusMonths());
        creditPage.unsuccessfulCredit(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvc());
    }

    //падает, баг
    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с действующей карты со сроком 61 месяц")
    void shouldFillFormAndPayWithApprovedCardExpiryOver5Years() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(61);
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    //падает, баг
    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с действующей карты со сроком 61 месяц")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiryOver5Years() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(61);
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен заполнить форму и отправить платеж с действующей карты со сроком 1 месяц")
    void shouldFillFormAndPayWithApprovedCardExpiry1Month() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(1);
        paymentPage.successfulPayment(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Должен заполнить форму и запросить кредит с действующей карты со сроком 1 месяц")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiry1Month() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(1);
        creditPage.successfulCredit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }


    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с действующей карты со сроком 100 месяцев")
    void shouldFillFormAndPayWithApprovedCardExpiry100Months() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(100);
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с действующей карты со сроком 100 месяцев")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiry100Months() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(100);
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с действующей карты с истекшим сроком")
    void shouldFillFormAndPayWithApprovedCardExpired() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(0);
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(), "20",
                approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с действующей карты с истекшим сроком")
    void shouldFillFormAndRequestCreditWithApprovedCardExpired() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(0);
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(), "20",
                approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен заполнить форму и отправить платеж с действующей карты со сроком до текущего месяца")
    void shouldFillFormAndPayWithApprovedCardExpiryCurrentMonth() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(0);
        paymentPage.successfulPayment(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Должен заполнить форму и запросить кредит с действующей карты со сроком до текущего месяца")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiryCurrentMonth() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(0);
        creditPage.successfulCredit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с действующей карты, 13 месяц")
    void shouldFillFormAndPayWithApprovedCard13Month() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с действующей карты, 13 месяц")
    void shouldFillFormAndRequestCreditWithApprovedCard13Month() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    //падает, баг
    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с действующей карты, 00 месяц")
    void shouldFillFormAndPayWithApprovedCard00Month() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    //падает, баг
    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с действующей карты, 00 месяц")
    void shouldFillFormAndRequestCreditWithApprovedCard00Month() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен заполнить форму и отправить платеж с действующей карты, 01 месяц")
    void shouldFillFormAndPayWithApprovedCardExpiry01Month() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        paymentPage.successfulPayment(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Должен заполнить форму и запросить кредит с действующей карты, 01 месяц")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiry01Month() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        creditPage.successfulCredit(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Должен заполнить форму и отправить платеж с действующей карты, 12 месяц")
    void shouldFillFormAndPayWithApprovedCardExpiry12Month() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        paymentPage.successfulPayment(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Должен заполнить форму и запросить кредит с действующей карты, 12 месяц")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiry12Month() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        creditPage.successfulCredit(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке пустой формы со страницы оплаты")
    void shouldNotSubmitEmptyPaymentForm() {
        var paymentPage = purchasePage.payWithCard();
        paymentPage.sendEmptyForm();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке пустой формы со страницы кредита")
    void shouldNotSubmitEmptyCreditForm() {
        var creditPage = purchasePage.payWithCredit();
        creditPage.sendEmptyForm();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с пустым номером карты")
    void shouldNotSubmitEmptyCardNumberPayment() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(" ", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с пустым номером карты")
    void shouldNotSubmitEmptyCardNumberCredit() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(" ", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с пустым месяцем")
    void shouldNotSubmitEmptyMonthPayment() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), " ",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с пустым месяцем")
    void shouldNotSubmitEmptyMonthCredit() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), " ",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с пустым годом")
    void shouldNotSubmitEmptyYearPayment() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                " ", approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с пустым годом")
    void shouldNotSubmitEmptyYearCredit() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                " ", approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с пустым владельцем")
    void shouldNotSubmitEmptyCardHolderPayment() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), " ", approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с пустым владельцем")
    void shouldNotSubmitEmptyCardHolderCredit() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), " ", approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с пустым CVC")
    void shouldNotSubmitEmptyCVCPayment() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), " ");
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с пустым CVC")
    void shouldNotSubmitEmptyCVCCredit() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), " ");
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с латиницей в поле номер карты")
    void shouldNotSubmitPaymentWithLatinInCardNumber() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(DataHelper.getRandomLatinSymbols(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с латиницей в поле номер карты")
    void shouldNotSubmitCreditWithLatinInCardNumber() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(DataHelper.getRandomLatinSymbols(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с кириллицей в поле номер карты")
    void shouldNotSubmitPaymentWithCyrillicInCardNumber() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(DataHelper.getRandomCyrillicSymbols(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с кириллицей в поле номер карты")
    void shouldNotSubmitCreditWithCyrillicInCardNumber() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(DataHelper.getRandomCyrillicSymbols(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа со спецсимволами в поле номер карты")
    void shouldNotSubmitPaymentWithSymbolsInCardNumber() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit("!§$%&/()=?><;:-+*#'°^", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита со спецсимволами в поле номер карты")
    void shouldNotSubmitCreditWithSymbolsInCardNumber() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit("!§$%&/()=?><;:-+*#'°^", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с латиницей в поле месяц")
    void shouldNotSubmitPaymentWithLatinInMonth() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), DataHelper.getRandomLatinSymbols(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с латиницей в поле месяц")
    void shouldNotSubmitCreditWithLatinInMonth() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), DataHelper.getRandomLatinSymbols(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с кириллицей в месяц")
    void shouldNotSubmitPaymentWithCyrillicInMonth() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), DataHelper.getRandomCyrillicSymbols(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с кириллицей в поле месяц")
    void shouldNotSubmitCreditWithCyrillicInMonth() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), DataHelper.getRandomCyrillicSymbols(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа со спецсимволами в поле месяц")
    void shouldNotSubmitPaymentWithSymbolsInMonth() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), "!§$%&/()=?><;:-+*#'°^",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита со спецсимволами в поле месяц")
    void shouldNotSubmitCreditWithSymbolsInMonth() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), "!§$%&/()=?><;:-+*#'°^",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с латиницей в поле год")
    void shouldNotSubmitPaymentWithLatinInYear() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                DataHelper.getRandomLatinSymbols(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с латиницей в поле год")
    void shouldNotSubmitCreditWithLatinInYear() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                DataHelper.getRandomLatinSymbols(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с кириллицей в поле год")
    void shouldNotSubmitPaymentWithCyrillicInYear() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                DataHelper.getRandomCyrillicSymbols(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с кириллицей в поле год")
    void shouldNotSubmitCreditWithCyrillicInYear() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                DataHelper.getRandomCyrillicSymbols(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа со спецсимволами в поле год")
    void shouldNotSubmitPaymentWithSymbolsInYear() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "!§$%&/()=?><;:-+*#'°^", approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита со спецсимволами в поле год")
    void shouldNotSubmitCreditWithSymbolsInYear() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "!§$%&/()=?><;:-+*#'°^", approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с кириллицей в поле Владелец")
    void shouldNotSubmitPaymentWithCyrillicInCardHolder() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), DataHelper.getRandomCyrillicSymbols(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с кириллицей в поле Владелец")
    void shouldNotSubmitCreditWithCyrillicInCardHolder() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), DataHelper.getRandomCyrillicSymbols(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с цифрами в поле Владелец")
    void shouldNotSubmitPaymentWithNumbersInCardHolder() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "1234455", approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с цифрами в поле Владелец")
    void shouldNotSubmitCreditWithNumbersInCardHolder() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "1278430", approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа со спецсимволами в поле Владелец")
    void shouldNotSubmitPaymentWithSymbolsInCardHolder() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "!§$%&/()=?><;:-+*#'°^", approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита со спецсимволами в поле Владелец")
    void shouldNotSubmitCreditWithSymbolsInCardHolder() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "!§$%&/()=?><;:-+*#'°^", approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с латиницей в поле CVC")
    void shouldNotSubmitPaymentWithLatinInCVC() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), DataHelper.getRandomLatinSymbols());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с латиницей в поле CVC")
    void shouldNotSubmitCreditWithLatinInCVC() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), DataHelper.getRandomLatinSymbols());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа с кириллицей в поле CVC")
    void shouldNotSubmitPaymentWithCyrillicInCVC() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), DataHelper.getRandomCyrillicSymbols());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита с кириллицей в поле CVC")
    void shouldNotSubmitCreditWithCyrillicInCVC() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), DataHelper.getRandomCyrillicSymbols());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при отправке платежа со спецсимволами в поле CVC")
    void shouldNotSubmitPaymentWithSymbolsInCVC() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "!§$%&/()=?><;:-+*#'°^");
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показать ошибку при запросе кредита со спецсимволами в поле CVC")
    void shouldNotSubmitCreditWithSymbolsInCVC() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "!§$%&/()=?><;:-+*#'°^");
        creditPage.inputInvalidError();
    }

}
