package com.testing.page.student;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private By username = By.name("username");
    private By password = By.name("password");
    private By signinButton = By.xpath("//button[contains(., 'Sign In')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void inputUsername(String data) {
        waitingElementReady(username).sendKeys(data);
    }

    public void inputPassword(String data) {
        waitingElementReady(password).sendKeys(data);
    }

    public void clickButtonLogin() {
        jsClick(waitingElementReady(signinButton));
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public void login(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        clickButtonLogin();
    }

}
