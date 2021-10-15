package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

@Data
public class PurchasePage {
    private SelenideElement heading = $("[class=\"heading heading_size_l heading_theme_alfa-on-white\"]");
    private SelenideElement tourInfo =
            $("[class=\"grid-row grid-row_gutter-mobile-s_16 grid-row_gutter-desktop-m_24 grid-row_justify_between grid-row_theme_alfa-on-white\"]");
    private SelenideElement paymentButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public PurchasePage() {
        heading.shouldBe(visible).shouldHave(exactText("Путешествие дня"));
        tourInfo.shouldBe(visible);
    }

    public PaymentPage payWithCard(){
        paymentButton.click();
        return new PaymentPage();
    }

    public CreditPage payWithCredit(){
        creditButton.click();
        return new CreditPage();
    }
}
