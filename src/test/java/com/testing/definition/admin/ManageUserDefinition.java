package com.testing.definition.admin;

import com.testing.page.admin.LoginPage;
import com.testing.page.admin.ManageuserPage;
import com.testing.utils.DriverUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class ManageUserDefinition {

    private ManageuserPage manageuserPage;
    private LoginPage loginPage;
    private com.testing.page.admin.components.SidebarComponent sidebarComponent;

    public ManageUserDefinition() {
        this.manageuserPage = new ManageuserPage(DriverUtil.getInstance());
        this.loginPage = new LoginPage(DriverUtil.getInstance());
        this.sidebarComponent = new com.testing.page.admin.components.SidebarComponent(DriverUtil.getInstance());
    }

    @Given("Admin sudah login dan berada di halaman Manage Users")
    public void adminSudahLoginDanBeradaDiHalamanManageUsers() {
        DriverUtil.getInstance().get("http://localhost:8074/SIMNS/");
        loginPage.inputUsername("admin");
        loginPage.inputPassword("admin123");
        loginPage.clickButtonLogin();
        
        // Ensure we are on Manage Users page by clicking sidebar
        sidebarComponent.clickManageUser();
    }

    @When("Admin mengisi first name {string}")
    public void adminMengisiFirstName(String firstName) {
        manageuserPage.inputFirstName(firstName);
    }

    @And("Admin mengisi last name {string}")
    public void adminMengisiLastName(String lastName) {
        manageuserPage.inputLastName(lastName);
    }

    @And("Admin mengisi username {string}")
    public void adminMengisiUsername(String username) {
        manageuserPage.inputUsername(username);
    }

    @And("Admin mengisi email {string}")
    public void adminMengisiEmail(String email) {
        manageuserPage.inputEmail(email);
    }

    @And("Admin memilih role {string}")
    public void adminMemilihRole(String role) {
        manageuserPage.selectRole(role);
    }

    @And("Admin mengisi password {string}")
    public void adminMengisiPassword(String password) {
        manageuserPage.inputPassword(password);
    }

    @And("Admin klik tombol Add User")
    public void adminKlikTombolAddUser() {
        manageuserPage.clickAddUser();
    }

    @Then("User baru {string} seharusnya muncul di daftar tabel")
    public void userBaruSeharusnyaMunculDiDaftarTabel(String username) {
        Assert.assertTrue(manageuserPage.isUserInTable(username), "Username " + username + " tidak ditemukan di tabel!");
    }
//Edit Feature
    @When("Admin klik tombol Edit pada user {string}")
    public void adminKlikTombolEditPadaUser(String username) {
        manageuserPage.clickEditUser(username);
    }

    @And("Admin mengubah first name menjadi {string}")
    public void adminMengubahFirstNameMenjadi(String newFirstName) {
        manageuserPage.clearAndInputEditFirstName(newFirstName);
    }

    @And("Admin klik tombol Update User")
    public void adminKlikTombolUpdateUser() {
        manageuserPage.clickUpdateUser();
    }

    @Then("Data user {string} seharusnya terupdate dengan first name {string}")
    public void dataUserSeharusnyaTerupdateDenganFirstName(String username, String expectedFirstName) {
        String actualFullName = manageuserPage.getUserFirstName(username);
        Assert.assertTrue(actualFullName.contains(expectedFirstName), 
            "First name '" + expectedFirstName + "' tidak ditemukan dalam nama lengkap '" + actualFullName + "'!");
    }

//Change Password Feature
    @When("Admin klik tombol password pada user {string}")
    public void adminKlikTombolPasswordPadaUser(String username){
        manageuserPage.clickChangePassword(username);
    }

    @And("Admin mengisi new password menjadi {string}")
    public void adminMengubahPassword(String newPassword){
        manageuserPage.newPassword(newPassword);
    }

    @And("Admin mengisi confirm password menjadi {string}")
    public void adminConfirmPassword(String confirmPassword){
        manageuserPage.confirmPassword(confirmPassword);
    }

    @And("Admin klik tombol change password")
    public void adminKlikTombolChangePassword(){
        manageuserPage.clickChangePasswordButton();
    }

    @Then("Password user sukses diubah")
    public void passwordSuksesDiubah() {
        String alertText = manageuserPage.getSuccessMessage();
        Assert.assertTrue(alertText.toLowerCase().contains("success"), "Pesan sukses tidak muncul! Munculnya: " + alertText);
    }
}
