package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Data
public class Card2ReplenishmentPage {
    private SelenideElement cardReplenishment = $x("//h1[contains(text(),'Пополнение карты')]");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement toField = $("[data-test-id=to] input[value='**** **** **** 0002']");
    private SelenideElement transferButton = $("[data-test-id=action-transfer] .button__text");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__title");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel] .button__text");

    public Card2ReplenishmentPage() {
        cardReplenishment.shouldHave(Condition.visible);
        toField.shouldBe(Condition.visible);
    }

    public DashboardPage card2Replenishment(int amount, int fromCardNumber) {
        amountField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        fromField.setValue(DataHelper.CardsNumber.getNumberCard(fromCardNumber));
        transferButton.click();
        return new DashboardPage();
    }

    public Card2ReplenishmentPage card2ReplenishmentEmptyFields() {
        amountField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        fromField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        transferButton.click();
        return new Card2ReplenishmentPage();
    }

    public Card2ReplenishmentPage card2ReplenishmentEmptyNumberCardField(int amount) {
        amountField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        transferButton.click();
        return new Card2ReplenishmentPage();
    }

    public Card2ReplenishmentPage card2ReplenishmentOtherNumberCardField(int amount) {
        amountField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        fromField.setValue("1111 2222 3333 4444");
        transferButton.click();
        return new Card2ReplenishmentPage();
    }

    public DashboardPage card2ReplenishmentFromThisCardField(int amount) {
        amountField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        fromField.setValue("5559 0000 0000 0002");
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage pressCancelButton(int amount, int fromCardNumber) {
        amountField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        fromField.setValue(DataHelper.CardsNumber.getNumberCard(fromCardNumber));
        cancelButton.click();
        return new DashboardPage();
    }

}
