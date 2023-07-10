package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CardReplenishmentPage {
    private SelenideElement cardReplenishment = $x("//h1[contains(text(),'Пополнение карты')]");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer] .button__text");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__title");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel] .button__text");

    public CardReplenishmentPage() {
        cardReplenishment.shouldHave(Condition.visible);
    }

    public void clearFields() {
        amountField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
        fromField.sendKeys(Keys.LEFT_SHIFT, Keys.ARROW_UP, Keys.BACK_SPACE);
    }

    public void setValueAmount(int amount) {
        amountField.setValue(String.valueOf(amount));
    }

    public void setValueFromNumberCard(int fromCardNumber) {
        fromField.setValue(DataHelper.CardsNumber.getNumberCard(fromCardNumber));
    }

    public DashboardPage cardReplenishment(int amount, int fromCardNumber) {
        clearFields();
        setValueAmount(amount);
        setValueFromNumberCard(fromCardNumber);
        transferButton.click();
        return new DashboardPage();
    }

    public void cardReplenishmentEmptyFields() {
        clearFields();
        transferButton.click();
        errorNotification.shouldBe(Condition.visible);
    }

    public void cardReplenishmentEmptyNumberCardField(int amount) {
        clearFields();
        setValueAmount(amount);
        transferButton.click();
        errorNotification.shouldBe(Condition.visible);
    }

    public void cardReplenishmentOtherNumberCardField(int amount, int fromCardNumber) {
        clearFields();
        setValueAmount(amount);
        fromField.setValue(DataHelper.CardsNumber.getNumberCard(fromCardNumber));
        transferButton.click();
        errorNotification.shouldBe(Condition.visible);
    }

    public DashboardPage pressCancelButton(int amount, int fromCardNumber) {
        clearFields();
        setValueAmount(amount);
        setValueFromNumberCard(fromCardNumber);
        cancelButton.click();
        return new DashboardPage();
    }

    public void cardReplenishmentNegative(int amount, int fromCardNumber) {
        clearFields();
        setValueAmount(amount);
        setValueFromNumberCard(fromCardNumber);
        transferButton.click();
        errorNotification.shouldBe(Condition.visible);
    }
}
