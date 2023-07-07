package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

@Data
public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login] .button__text");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__title");
    private SelenideElement loginInputInvalid = $("[data-test-id=login].input_invalid .input__sub");
    private SelenideElement passwordInputInvalid = $("[data-test-id=password].input_invalid .input__sub");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public LoginPage noValidLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new LoginPage();
    }

    public LoginPage emptyLogin(DataHelper.AuthInfo info) {
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new LoginPage();
    }

    public LoginPage emptyPassword(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        loginButton.click();
        return new LoginPage();
    }
}
