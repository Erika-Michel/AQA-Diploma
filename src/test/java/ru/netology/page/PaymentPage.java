package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Data;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.DELETE;

@Data
public class PaymentPage {
    private SelenideElement heading = $("[class = 'heading heading_size_m heading_theme_alfa-on-white']");
    private SelenideElement creditButton = $(byText("Купить в кредит"));
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement cardHolderField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcNumberField = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");
    private SelenideElement inputInvalid = $(".input__sub"); //убрала точку перед инпут везде выше
//    private SelenideElement anyNotification = $(".notification");

    public PaymentPage() {
        heading.shouldBe(visible).shouldHave(exactText("Оплата по карте"));
    }

    public CreditPage payWithCredit() {
        creditButton.click();
        return new CreditPage();
    }

    private void clearForm() {
        cardNumberField.sendKeys(CONTROL + "A", DELETE);
        monthField.sendKeys(CONTROL + "A", DELETE);
        yearField.sendKeys(CONTROL + "A", DELETE);
        cardHolderField.sendKeys(CONTROL + "A", DELETE);
        cvcNumberField.sendKeys(CONTROL + "A", DELETE);
    }

    public void fillDataAndSubmit(String card, String month, String year, String name, String cvc) {
//        clearForm();
        cardNumberField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        cardHolderField.setValue(name);
        cvcNumberField.setValue(cvc);
        continueButton.click();
    }

    public void successfulPayment(String card, String month, String year, String name, String cvc) {
        fillDataAndSubmit(card, month, year, name, cvc);
        successNotification.shouldBe(visible, Duration.ofSeconds(10)).
                shouldHave(text("Операция одобрена Банком."));
    }

    public void unsuccessfulPayment(String card, String month, String year, String name, String cvc) {
        fillDataAndSubmit(card, month, year, name, cvc);
        errorNotification.shouldBe(visible, Duration.ofSeconds(10)).
                shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    public void sendEmptyForm() {
//        clearForm();
        continueButton.click();
        inputInvalid.shouldBe(visible);
    }

    public void inputInvalidError() {
        inputInvalid.shouldBe(visible);
    }

//    public void anyNotification() {
//        anyNotification.shouldBe(visible, Duration.ofSeconds(10));
//    }

}
