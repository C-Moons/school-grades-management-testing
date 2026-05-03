package com.testing.definition.student;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.testing.page.student.LoginPage;
import com.testing.page.student.PerformancePage;
import com.testing.utils.DriverUtil;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PerformanceDefinition {
    private LoginPage loginPage;
    private PerformancePage performancePage;

    @Given("Saya buka browser & akses halaman login.")
    public void browserLogin(){
        loginPage = new LoginPage(DriverUtil.getInstance());
        performancePage = new PerformancePage((DriverUtil.getInstance()));
        DriverUtil.getInstance().get("http://localhost:8074/SIMNS/");
    }

    @When("Saya input username {string} & password {string} login.")
    public void loginForm(String username, String password){
        loginPage.inputUsername(username);
        loginPage.inputPassword(password);
        loginPage.clickButtonLogin();
    }

    @And("menampilkan Dashboard.")
    public void dashboardPage(){
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/students.php"));

        String currentUrl = loginPage.getCurrentURL();
        String expectedUrl = "http://localhost:8074/SIMNS/students.php";
        Assert.assertEquals(currentUrl, expectedUrl);
    }

    @Then("Pilih menu Performance.")
    public void performancePage(){
        performancePage.selectPerformance();
        performancePage.waitForAnimation();
    }
}
