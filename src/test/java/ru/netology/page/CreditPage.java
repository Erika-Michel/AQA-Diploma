package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Data;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;


@Data
public class CreditPage {
    private SelenideElement heading = $("[class = 'heading heading_size_m heading_theme_alfa-on-white']");
    private SelenideElement paymentButton = $(byText("Купить"));
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement cardHolderField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcNumberField = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");
    private SelenideElement inputInvalid = $(".input__sub");
    private SelenideElement anyNotification = $(".notification");


    public CreditPage() {
        heading.shouldBe(visible).shouldHave(exactText("Кредит по данным карты"));
    }

    public PaymentPage payWithDebit() {
        paymentButton.click();
        return new PaymentPage();
    }

    public void fillCreditDataAndSubmit(String card, String month, String year, String name, String cvc) {
        cardNumberField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        cardHolderField.setValue(name);
        cvcNumberField.setValue(cvc);
        continueButton.click();
    }

    public void successfulCredit(String card, String month, String year, String name, String cvc) {
        fillCreditDataAndSubmit(card, month, year, name, cvc);
        successNotification.shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(text("Операция одобрена Банком."));
    }

    public void unsuccessfulCredit(String card, String month, String year, String name, String cvc) {
        fillCreditDataAndSubmit(card, month, year, name, cvc);
        errorNotification.shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    public void sendEmptyForm() {
        continueButton.click();
        inputInvalid.shouldBe(visible);
    }

    public void inputInvalidError() {
        inputInvalid.shouldBe(visible);
    }

    public void anyNotification() {
        anyNotification.shouldBe(visible, Duration.ofSeconds(15));
    }
}
