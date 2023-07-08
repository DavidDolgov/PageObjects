package ru.netology.test;

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
        card1ReplenishPage.cardReplenishment(DataHelper.getTransferAmount(), 2);
        var card2ReplenishPage = dashboardPage.pressButton2();
        card2ReplenishPage.cardReplenishment(DataHelper.getTransferAmount(), 1);

        Assertions.assertEquals(dashboardPage.getCardBalance(1), dashboardPage.getCardBalance(2));
    }

    @Test
    @DisplayName("LoginPageTest - Неверный логин; Верный пароль")
    void negativePathTestForLoginField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getOtherLoginAuthInfo();
        loginPage.noValidLogin(authInfo);
    }

    @Test
    @DisplayName("LoginPageTest - Неверный пароль; Верный логин")
    void negativePathTestForPasswordField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getOtherPasswordAuthInfo();
        loginPage.noValidLogin(authInfo);
    }

    @Test
    @DisplayName("LoginPageTest - Неверный пароль; Неверный логин")
    void negativePathTestForPasswordAndLoginField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getOtherAuthInfo();
        loginPage.noValidLogin(authInfo);
    }

    @Test
    @DisplayName("LoginPageTest - Пустой логин; Верный пароль")
    void negativePathTestForEmptyLoginField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.emptyLogin(authInfo);
    }

    @Test
    @DisplayName("LoginPageTest - Пустой пароль; Верный логин")
    void negativePathTestForEmptyPasswordField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.emptyPassword(authInfo);
    }

    @Test
    @DisplayName("VerificationPageTest - Неверный код")
    void negativePathTestForCodeField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getNotVerificationCode(authInfo);
        verificationPage.noValidVerify(verificationCode);
    }

    @Test
    @DisplayName("VerificationPageTest - Пустой код")
    void negativePathTestForEmptyCodeField() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.emptyCode();
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
        card1ReplenishPage.cardReplenishmentEmptyFields();
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
        card1ReplenishPage.cardReplenishmentEmptyNumberCardField(DataHelper.getTransferAmount());
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
        card1ReplenishPage.cardReplenishmentOtherNumberCardField(DataHelper.getTransferAmount());
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
        card1ReplenishPage.cardReplenishment(DataHelper.getTransferAmount(), 1);
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
        card1ReplenishPage.cardReplenishment(DataHelper.getNegativeTransferAmount(), 2);

        int expectedCard1 = 10000 + DataHelper.getNegativeTransferAmount();
        int actualCard1 = dashboardPage.getCardBalance(1);
        int expectedCard2 = 10000 - DataHelper.getNegativeTransferAmount();
        int actualCard2 = dashboardPage.getCardBalance(2);
        Assertions.assertEquals(expectedCard1,actualCard1);
        Assertions.assertEquals(expectedCard2,actualCard2);

        var card2ReplenishPage = dashboardPage.pressButton2();
        card2ReplenishPage.cardReplenishment(DataHelper.getNegativeTransferAmount(), 1);
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
        card2ReplenishPage.cardReplenishmentEmptyFields();
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
        card2ReplenishPage.cardReplenishmentEmptyNumberCardField(DataHelper.getTransferAmount());
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
        card2ReplenishPage.cardReplenishmentOtherNumberCardField(DataHelper.getTransferAmount());
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
        card2ReplenishPage.cardReplenishment(DataHelper.getTransferAmount(),2);
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
        card2ReplenishPage.cardReplenishment(DataHelper.getNegativeTransferAmount(), 1);

        int expectedCard2 = 10000 + DataHelper.getNegativeTransferAmount();
        int actualCard2 = dashboardPage.getCardBalance(2);
        int expectedCard1 = 10000 - DataHelper.getNegativeTransferAmount();
        int actualCard1 = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(expectedCard2,actualCard2);
        Assertions.assertEquals(expectedCard1,actualCard1);


        var card1ReplenishPage = dashboardPage.pressButton1();
        card1ReplenishPage.cardReplenishment(DataHelper.getNegativeTransferAmount(), 2);
    }
}
