package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class PageObjectTest {

    @BeforeEach
    void setUP() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    @DisplayName("Положительный сценарий пополнения c карты на карту 200 р. и обратно")
    void shouldReplenishFromCardToCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1ReplenishPage = dashboardPage.pressButton1();
        card1ReplenishPage.card1Replenishment(DataHelper.getTransferAmount(), 2);
        var card2ReplenishPage = dashboardPage.pressButton2();
        card2ReplenishPage.card2Replenishment(DataHelper.getTransferAmount(), 1);

        int expected = 0;
        int actual = dashboardPage.getDifferenceInCardAmounts();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("LoginPageTest - Неверный логин; Верный пароль")
    void negativePathTestForLoginField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getOtherLoginAuthInfo();
        loginPage.noValidLogin(authInfo);
        loginPage.getErrorNotification().shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("LoginPageTest - Неверный пароль; Верный логин")
    void negativePathTestForPasswordField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getOtherPasswordAuthInfo();
        loginPage.noValidLogin(authInfo);
        loginPage.getErrorNotification().shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("LoginPageTest - Неверный пароль; Неверный логин")
    void negativePathTestForPasswordAndLoginField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getOtherAuthInfo();
        loginPage.noValidLogin(authInfo);
        loginPage.getErrorNotification().shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("LoginPageTest - Пустой логин; Верный пароль")
    void negativePathTestForEmptyLoginField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.emptyLogin(authInfo);
        loginPage.getLoginInputInvalid().shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("LoginPageTest - Пустой пароль; Верный логин")
    void negativePathTestForEmptyPasswordField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.emptyPassword(authInfo);
        loginPage.getPasswordInputInvalid().shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("VerificationPageTest - Неверный код")
    void negativePathTestForCodeField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getNotVerificationCode(authInfo);
        verificationPage.noValidVerify(verificationCode);
        verificationPage.getErrorNotification().shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("VerificationPageTest - Пустой код")
    void negativePathTestForEmptyCodeField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.emptyCode();
        verificationPage.getCodeInputInvalid().shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("DashBoardCard1Test - Поля для ввода пустые")
    void negativePathTestForCard1EmptyFields() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1ReplenishPage = dashboardPage.pressButton1();
        card1ReplenishPage.card1ReplenishmentEmptyFields();
        card1ReplenishPage.getErrorNotification().shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("DashBoardCard1Test - Заполняется только сумма")
    void negativePathTestForCard1EmptyNumberCardFromFields() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1ReplenishPage = dashboardPage.pressButton1();
        card1ReplenishPage.card1ReplenishmentEmptyNumberCardField(DataHelper.getTransferAmount());
        card1ReplenishPage.getErrorNotification().shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("DashBoardCard1Test - Другой номер карты")
    void negativePathTestForCard1OtherNumberCardFromFields() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1ReplenishPage = dashboardPage.pressButton1();
        card1ReplenishPage.card1ReplenishmentOtherNumberCardField(DataHelper.getTransferAmount());
        card1ReplenishPage.getErrorNotification().shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("DashBoardCard1Test - Этот же номер карты - БАГ")
    void negativePathTestForCard1ThisNumberCardFromFields() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1ReplenishPage = dashboardPage.pressButton1();
        card1ReplenishPage.card1ReplenishmentFromThisCardField(DataHelper.getTransferAmount());
    }

    @Test
    @DisplayName("DashBoardCard1Test - Нажатие кнопки отмена")
    void happyPathTestForCard1PressCancelButton() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1ReplenishPage = dashboardPage.pressButton1();
        card1ReplenishPage.pressCancelButton(DataHelper.getTransferAmount(), 2);
    }

    @Test
    @DisplayName("DashBoardCard1Test - Сумма больше имеющейся - БАГ")
    void negativePathTestForCard1AmountMoreThanAvailable() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1ReplenishPage = dashboardPage.pressButton1();
        card1ReplenishPage.card1Replenishment(DataHelper.getNegativeTransferAmount(), 2);

        boolean expected;
        if (dashboardPage.getCardBalance(dashboardPage.getCard2()) < 0) {
            expected = true;
        } else {
            expected = false;
        }
        boolean actual = true;
        Assertions.assertEquals(expected, actual);

        var card2ReplenishPage = dashboardPage.pressButton2();
        card2ReplenishPage.card2Replenishment(DataHelper.getNegativeTransferAmount(), 1);
    }

    @Test
    @DisplayName("DashBoardCard2Test - Поля для ввода пустые")
    void negativePathTestForCard2EmptyFields() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card2ReplenishPage = dashboardPage.pressButton2();
        card2ReplenishPage.card2ReplenishmentEmptyFields();
        card2ReplenishPage.getErrorNotification().shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("DashBoardCard2Test - Заполняется только сумма")
    void negativePathTestForCard2EmptyNumberCardFromFields() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card2ReplenishPage = dashboardPage.pressButton2();
        card2ReplenishPage.card2ReplenishmentEmptyNumberCardField(DataHelper.getTransferAmount());
        card2ReplenishPage.getErrorNotification().shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("DashBoardCard2Test - Другой номер карты")
    void negativePathTestForCard2OtherNumberCardFromFields() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card2ReplenishPage = dashboardPage.pressButton2();
        card2ReplenishPage.card2ReplenishmentOtherNumberCardField(DataHelper.getTransferAmount());
        card2ReplenishPage.getErrorNotification().shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("DashBoardCard2Test - Этот же номер карты - БАГ")
    void negativePathTestForCard2ThisNumberCardFromFields() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card2ReplenishPage = dashboardPage.pressButton2();
        card2ReplenishPage.card2ReplenishmentFromThisCardField(DataHelper.getTransferAmount());
    }

    @Test
    @DisplayName("DashBoardCard2Test - Нажатие кнопки отмена")
    void happyPathTestForCard2PressCancelButton() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card2ReplenishPage = dashboardPage.pressButton2();
        card2ReplenishPage.pressCancelButton(DataHelper.getTransferAmount(), 1);
    }

    @Test
    @DisplayName("DashBoardCard2Test - Сумма больше имеющейся - БАГ")
    void negativePathTestForCard2AmountMoreThanAvailable() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card2ReplenishPage = dashboardPage.pressButton2();
        card2ReplenishPage.card2Replenishment(DataHelper.getNegativeTransferAmount(), 1);

        boolean expected;
        if (dashboardPage.getCardBalance(dashboardPage.getCard1()) < 0) {
            expected = true;
        } else {
            expected = false;
        }
        boolean actual = true;
        Assertions.assertEquals(expected, actual);

        var card1ReplenishPage = dashboardPage.pressButton1();
        card1ReplenishPage.card1Replenishment(DataHelper.getNegativeTransferAmount(), 2);
    }
}
