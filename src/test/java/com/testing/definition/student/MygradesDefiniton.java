package com.testing.definition.student;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.testing.page.student.DashboardPage;
import com.testing.page.student.LoginPage;
import com.testing.page.student.MygradesPage;
import com.testing.utils.DriverUtil;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MygradesDefiniton {
    private LoginPage loginPage;
    private MygradesPage mygradesPage;

    @Given("Saya buka browser dan akses halaman login.")
    public void accessBrowserAndLogin() {
        loginPage = new LoginPage(DriverUtil.getInstance());
        mygradesPage = new MygradesPage(DriverUtil.getInstance());
        DriverUtil.getInstance().get("http://localhost:8074/SIMNS/");
    }

    @When("Saya input username {string} dan password {string} login.")
    public void Login(String user, String pass){
        loginPage.inputUsername(user);
        loginPage.inputPassword(pass);
        loginPage.clickButtonLogin();
    }

    @And("tampilan Dashboard.")
    public void dashboardPage(){
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/students.php"));

        String currentUrl = loginPage.getCurrentURL();
        String expectedUrl = "http://localhost:8074/SIMNS/students.php";
        Assert.assertEquals(currentUrl, expectedUrl);
    }

    @Then("Pilih menu My Grades.")
    public void myGradesPage(){
        mygradesPage.selectMyGrade();
        mygradesPage.waitForAnimation();
    }
}
