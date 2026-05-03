package com.testing.definition.admin;

import java.sql.Driver;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.testing.page.admin.LoginPage;
import com.testing.utils.DriverUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginAdminDefiniton {
    private LoginPage loginPage;

    @Given("Saya buka browser & akses halaman login admin.")
    public void openBrowserAndLogin(){
        loginPage = new LoginPage(DriverUtil.getInstance());
        DriverUtil.getInstance().get("http://localhost:8074/SIMNS/");
    }

    @When("Saya input username {string} & password {string} klik login.")
    public void loginWeb(String user, String pass){
        loginPage.inputUsername(user);
        loginPage.inputPassword(pass);
        loginPage.clickButtonLogin();
    }

    @Then("menampilkan tampilam Dashboard.")
    public void dashboardPage(){
                WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/admin.php"));

        String currentUrl = loginPage.getCurrentURL();
        String expectedUrl = "http://localhost:8074/SIMNS/admin.php";
        Assert.assertEquals(currentUrl, expectedUrl);
    }
}
