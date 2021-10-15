package ru.netology.test;

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
    @DisplayName("Should switch from Payment page to Credit page")
    void shouldSwitchFromPaymentPageToCreditPage() {
        var paymentPage = purchasePage.payWithCard();
        var creditPage = paymentPage.payWithCredit();
    }

    @Test
    @DisplayName("Should switch from Credit page to Payment page")
    void shouldSwitchFromCreditPageToPaymentPage() {
        var creditPage = purchasePage.payWithCredit();
        var paymentPage = creditPage.payWithDebit();
    }

    @Test
    @DisplayName("Should fill form and pay with Approved card")
    void shouldFillFormAndPayWithApprovedCard() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.successfulPayment(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Should fill form and request credit with Approved card")
    void shouldFillFormAndRequestCreditWithApprovedCard() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.successfulCredit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    //падает, баг
    @Test
    @DisplayName("Should show error when paying with Declined card")
    void shouldFillFormAndPayWithDeclinedCard() {
        var paymentPage = purchasePage.payWithCard();
        var declinedPayment = DataHelper.usingDeclinedCard(DataHelper.randomPlusMonths());
        paymentPage.unsuccessfulPayment(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvc());
    }

    //падает, баг
    @Test
    @DisplayName("Should show error when requesting credit with Declined card")
    void shouldFillFormAndRequestCreditWithDeclinedCard() {
        var creditPage = purchasePage.payWithCredit();
        var declinedPayment = DataHelper.usingDeclinedCard(DataHelper.randomPlusMonths());
        creditPage.unsuccessfulCredit(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvc());
    }

    //падает, баг
    @Test
    @DisplayName("Should show error when paying with Approved card, expiry in 61 months")
    void shouldFillFormAndPayWithApprovedCardExpiryOver5Years() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(61);
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    //падает, баг
    @Test
    @DisplayName("Should show error when requesting credit with Approved card, expiry in 61 months")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiryOver5Years() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(61);
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should pay with Approved card, expiry in 1 month")
    void shouldFillFormAndPayWithApprovedCardExpiry1Month() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(1);
        paymentPage.successfulPayment(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Should request credit with Approved card, expiry in 1 month")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiry1Month() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(1);
        creditPage.successfulCredit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }


    @Test
    @DisplayName("Should show error when paying with Approved card, expiry in 100 months")
    void shouldFillFormAndPayWithApprovedCardExpiry100Months() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(100);
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should show error when requesting credit with Approved card, expiry in 100 months")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiry100Months() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(100);
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should show error when paying with Approved card, expired")
    void shouldFillFormAndPayWithApprovedCardExpired() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(0);
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(), "20",
                approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should show error when requesting credit with Approved card, expired")
    void shouldFillFormAndRequestCreditWithApprovedCardExpired() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(0);
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(), "20",
                approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should pay with Approved card, expiry in current month")
    void shouldFillFormAndPayWithApprovedCardExpiryCurrentMonth() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(0);
        paymentPage.successfulPayment(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Should request credit with Approved card, expiry in current month")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiryCurrentMonth() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(0);
        creditPage.successfulCredit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Should show error when paying with Approved card, 13. month")
    void shouldFillFormAndPayWithApprovedCard13Month() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should show error when requesting credit with Approved card, 13. month")
    void shouldFillFormAndRequestCreditWithApprovedCard13Month() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    //падает, баг
    @Test
    @DisplayName("Should show error when paying with Approved card, 00. month")
    void shouldFillFormAndPayWithApprovedCard00Month() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    //падает, баг
    @Test
    @DisplayName("Should show error when requesting credit with Approved card, 00. month")
    void shouldFillFormAndRequestCreditWithApprovedCard00Month() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should pay with Approved card, 01. month")
    void shouldFillFormAndPayWithApprovedCardExpiry01Month() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        paymentPage.successfulPayment(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Should request credit with Approved card, 01. month")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiry01Month() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        creditPage.successfulCredit(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Should pay with Approved card, 12. month")
    void shouldFillFormAndPayWithApprovedCardExpiry12Month() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        paymentPage.successfulPayment(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Should request credit with Approved card, 12. month")
    void shouldFillFormAndRequestCreditWithApprovedCardExpiry12Month() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(36);
        creditPage.successfulCredit(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
    }

    @Test
    @DisplayName("Should show error when submitting empty payment form")
    void shouldNotSubmitEmptyPaymentForm() {
        var paymentPage = purchasePage.payWithCard();
        paymentPage.sendEmptyForm();
    }

    @Test
    @DisplayName("Should show error when submitting empty credit form")
    void shouldNotSubmitEmptyCreditForm() {
        var creditPage = purchasePage.payWithCredit();
        creditPage.sendEmptyForm();
    }

    @Test
    @DisplayName("Should not submit payment with empty Card Number")
    void shouldNotSubmitEmptyCardNumberPayment() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(" ", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with empty Card Number")
    void shouldNotSubmitEmptyCardNumberCredit() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(" ", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with empty Month")
    void shouldNotSubmitEmptyMonthPayment() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), " ",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with empty Month")
    void shouldNotSubmitEmptyMonthCredit() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), " ",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with empty Year")
    void shouldNotSubmitEmptyYearPayment() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                " ", approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with empty Year")
    void shouldNotSubmitEmptyYearCredit() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                " ", approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with empty Card Holder")
    void shouldNotSubmitEmptyCardHolderPayment() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), " ", approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with empty Card Holder")
    void shouldNotSubmitEmptyCardHolderCredit() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), " ", approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with empty CVC")
    void shouldNotSubmitEmptyCVCPayment() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), " ");
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with empty CVC")
    void shouldNotSubmitEmptyCVCCredit() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), " ");
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Latin in Card Number")
    void shouldNotSubmitPaymentWithLatinInCardNumber() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(DataHelper.getRandomLatinSymbols(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Latin in Card Number")
    void shouldNotSubmitCreditWithLatinInCardNumber() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(DataHelper.getRandomLatinSymbols(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Cyrillic in Card Number")
    void shouldNotSubmitPaymentWithCyrillicInCardNumber() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(DataHelper.getRandomCyrillicSymbols(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Cyrillic in Card Number")
    void shouldNotSubmitCreditWithCyrillicInCardNumber() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(DataHelper.getRandomCyrillicSymbols(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Symbols in Card Number")
    void shouldNotSubmitPaymentWithSymbolsInCardNumber() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit("!§$%&/()=?><;:-+*#'°^", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Symbols in Card Number")
    void shouldNotSubmitCreditWithSymbolsInCardNumber() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit("!§$%&/()=?><;:-+*#'°^", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Latin in Month")
    void shouldNotSubmitPaymentWithLatinInMonth() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), DataHelper.getRandomLatinSymbols(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Latin in Month")
    void shouldNotSubmitCreditWithLatinInMonth() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), DataHelper.getRandomLatinSymbols(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Cyrillic in Month")
    void shouldNotSubmitPaymentWithCyrillicInMonth() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), DataHelper.getRandomCyrillicSymbols(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Cyrillic in Month")
    void shouldNotSubmitCreditWithCyrillicInMonth() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), DataHelper.getRandomCyrillicSymbols(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Symbols in Month")
    void shouldNotSubmitPaymentWithSymbolsInMonth() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), "!§$%&/()=?><;:-+*#'°^",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Symbols in Month")
    void shouldNotSubmitCreditWithSymbolsInMonth() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), "!§$%&/()=?><;:-+*#'°^",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Latin in Year")
    void shouldNotSubmitPaymentWithLatinInYear() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                DataHelper.getRandomLatinSymbols(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Latin in Year")
    void shouldNotSubmitCreditWithLatinInYear() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                DataHelper.getRandomLatinSymbols(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Cyrillic in Year")
    void shouldNotSubmitPaymentWithCyrillicInYear() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                DataHelper.getRandomCyrillicSymbols(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Cyrillic in Year")
    void shouldNotSubmitCreditWithCyrillicInYear() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                DataHelper.getRandomCyrillicSymbols(), approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Symbols in Year")
    void shouldNotSubmitPaymentWithSymbolsInYear() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "!§$%&/()=?><;:-+*#'°^", approvedPayment.getCardHolder(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Symbols in Year")
    void shouldNotSubmitCreditWithSymbolsInYear() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "!§$%&/()=?><;:-+*#'°^", approvedPayment.getCardHolder(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    //Падает, баг
    @Test
    @DisplayName("Should not submit payment with Cyrillic in Card Holder")
    void shouldNotSubmitPaymentWithCyrillicInCardHolder() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), DataHelper.getRandomCyrillicFullName(), approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    //Падает, баг
    @Test
    @DisplayName("Should not submit credit with Cyrillic in Card Holder")
    void shouldNotSubmitCreditWithCyrillicInCardHolder() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), DataHelper.getRandomCyrillicFullName(), approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    //Падает, баг
    @Test
    @DisplayName("Should not submit payment with Numbers in Card Holder")
    void shouldNotSubmitPaymentWithNumbersInCardHolder() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "1234455", approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    //Падает, баг
    @Test
    @DisplayName("Should not submit credit with Numbers in Card Holder")
    void shouldNotSubmitCreditWithNumbersInCardHolder() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "1278430", approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    //Падает, баг
    @Test
    @DisplayName("Should not submit payment with Symbols in Card Holder")
    void shouldNotSubmitPaymentWithSymbolsInCardHolder() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "!§$%&/()=?><;:-+*#'°^", approvedPayment.getCvc());
        paymentPage.inputInvalidError();
    }

    //Падает, баг
    @Test
    @DisplayName("Should not submit credit with Symbols in Card Holder")
    void shouldNotSubmitCreditWithSymbolsInCardHolder() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "!§$%&/()=?><;:-+*#'°^", approvedPayment.getCvc());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Latin in CVC")
    void shouldNotSubmitPaymentWithLatinInCVC() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), DataHelper.getRandomLatinSymbols());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Latin in CVC")
    void shouldNotSubmitCreditWithLatinInCVC() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), DataHelper.getRandomLatinSymbols());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Cyrillic in CVC")
    void shouldNotSubmitPaymentWithCyrillicInCVC() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), DataHelper.getRandomCyrillicSymbols());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Cyrillic in CVC")
    void shouldNotSubmitCreditWithCyrillicInCVC() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), DataHelper.getRandomCyrillicSymbols());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit payment with Symbols in CVC")
    void shouldNotSubmitPaymentWithSymbolsInCVC() {
        var paymentPage = purchasePage.payWithCard();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        paymentPage.fillDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "!§$%&/()=?><;:-+*#'°^");
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Should not submit credit with Symbols in CVC")
    void shouldNotSubmitCreditWithSymbolsInCVC() {
        var creditPage = purchasePage.payWithCredit();
        var approvedPayment = DataHelper.usingApprovedCard(DataHelper.randomPlusMonths());
        creditPage.fillCreditDataAndSubmit(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "!§$%&/()=?><;:-+*#'°^");
        creditPage.inputInvalidError();
    }

}
