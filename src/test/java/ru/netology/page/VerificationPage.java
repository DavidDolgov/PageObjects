package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

@Data
public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__title");
    private SelenideElement codeInputInvalid = $("[data-test-id=code].input_invalid .input__sub");

    public VerificationPage() {
        codeField.shouldHave(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode info) {
        codeField.setValue(info.getCode()).sendKeys(Keys.ENTER);
        return new DashboardPage();
    }

    public VerificationPage noValidVerify(DataHelper.VerificationCode info) {
        codeField.setValue(info.getCode()).sendKeys(Keys.ENTER);
        return new VerificationPage();
    }

    public VerificationPage emptyCode() {
        codeField.sendKeys(Keys.ENTER);
        return new VerificationPage();
    }
}
