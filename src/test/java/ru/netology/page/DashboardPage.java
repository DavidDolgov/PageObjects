package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {
    private SelenideElement cards = $x("//h1[contains(text(),'Ваши карты')]");
    private SelenideElement card1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement card2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    private SelenideElement replenishOnTheCard1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button__text");
    private SelenideElement replenishOnTheCard2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button__text");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        cards.shouldBe(Condition.visible);
    }

    public SelenideElement getCard1() {
        return card1;
    }

    public SelenideElement getCard2() {
        return card2;
    }

    public int getCardBalance(SelenideElement card) {
        val text = card.text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getDifferenceInCardAmounts() {
        int difference;
        int card1 = getCardBalance(getCard1());
        int card2 = getCardBalance(getCard2());
        if (card1 >= card2) {
            difference = card1 - card2;
        } else {
            difference = card2 - card1;
        }
        return difference;
    }

    public Card1ReplenishmentPage pressButton1() {
        replenishOnTheCard1.click();
        return new Card1ReplenishmentPage();
    }

    public Card2ReplenishmentPage pressButton2() {
        replenishOnTheCard2.click();
        return new Card2ReplenishmentPage();
    }

}
