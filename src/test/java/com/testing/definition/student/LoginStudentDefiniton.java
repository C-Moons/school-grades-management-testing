package com.testing.definition.student;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.testing.page.student.DashboardPage;
import com.testing.page.student.LoginPage;
import com.testing.utils.DriverUtil;

public class LoginStudentDefiniton {
    private LoginPage  loginPage;
    private DashboardPage dashboardPage;

    @Given("Saya membuka browser dan mengakses halaman login.")
    public void openBrowserAndLogin() {
        loginPage = new LoginPage(DriverUtil.getInstance());
        dashboardPage = new DashboardPage(DriverUtil.getInstance());
        DriverUtil.getInstance().get("http://localhost:8074/SIMNS/");
    }

    @When("Saya memasukkan username {string} dan password {string} login")
    public void loginweb(String username, String password){
        loginPage.inputUsername(username);
        loginPage.inputPassword(password);
        loginPage.clickButtonLogin();
    }

    @Then("masuk ke Dashboard.")
    public void dashboard(){
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/students.php"));

        String currentUrl = loginPage.getCurrentURL();
        String expectedUrl = "http://localhost:8074/SIMNS/students.php";
        Assert.assertEquals(currentUrl, expectedUrl);
    }

}
