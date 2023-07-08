package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private SelenideElement cards = $x("//h1[contains(text(),'Ваши карты')]");
    private ElementsCollection cardNumbers = $$(".list__item div");
    private SelenideElement replenishOnTheCard1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button__text");
    private SelenideElement replenishOnTheCard2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button__text");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        cards.shouldBe(Condition.visible);
    }

    public int getCardBalance(int index) {
        val text = cardNumbers.get(index - 1).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public CardReplenishmentPage pressButton1() {
        replenishOnTheCard1.click();
        return new CardReplenishmentPage();
    }

    public CardReplenishmentPage pressButton2() {
        replenishOnTheCard2.click();
        return new CardReplenishmentPage();
    }

}
