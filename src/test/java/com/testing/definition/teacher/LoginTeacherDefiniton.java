package com.testing.definition.teacher;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.testing.page.teacher.LoginPage;
import com.testing.utils.DriverUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginTeacherDefiniton {
    private LoginPage loginPage;

    @Given("Saya buka browser & akses halaman login teacher.")
    public void openBrowserandLogin(){
        loginPage = new LoginPage(DriverUtil.getInstance());
        DriverUtil.getInstance().get("http://localhost:8074/SIMNS/");
    }

    @When("Saya input username {string} & password {string} klik login.")
    public void loginWebsite(String username, String password){
        loginPage.inputUsername(username);
        loginPage.inputPassword(password);
        loginPage.clickButtonLogin();
    }

    @Then("menampilkan tampilan Dashboard teacher.")
    public void showDashboardPage(){
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/teacher.php"));

        String currentUrl = loginPage.getCurrentURL();
        String expectedUrl = "http://localhost:8074/SIMNS/teacher.php";
        Assert.assertEquals(currentUrl, expectedUrl);
    }

    @Then("menampilkan card {string} pada Dashboard.")
    public void verifyCardOnDashboard(String cardName){
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(5));
        WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '" + cardName + "')]")));
        Assert.assertTrue(card.isDisplayed(), "Card " + cardName + " tidak ditemukan pada Dashboard.");
    }
}
