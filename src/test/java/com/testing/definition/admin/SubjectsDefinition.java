package com.testing.definition.admin;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.testing.page.admin.LoginPage;
import com.testing.page.admin.SubjectPage;
import com.testing.utils.DriverUtil;
import com.testing.utils.ScreenshotUtil;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class SubjectsDefinition {
    private SubjectPage subjectPage;
    private LoginPage loginPage;
    private com.testing.page.admin.components.SidebarComponent sidebarComponent;
    private Scenario scenario;

    public SubjectsDefinition() {
        this.subjectPage = new SubjectPage(DriverUtil.getInstance());
        this.loginPage = new LoginPage(DriverUtil.getInstance());
        this.sidebarComponent = new com.testing.page.admin.components.SidebarComponent(DriverUtil.getInstance());
    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    private void addScreenshot(String name) {
        // 1. Simpan sebagai file lokal
        ScreenshotUtil.takeScreenshot(DriverUtil.getInstance(), name);
        // 2. Lampirkan ke Cucumber Report
        final byte[] screenshot = ((TakesScreenshot) DriverUtil.getInstance()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", name);
    }

    @Given("Admin sudah login dan berada di halaman Subjects")
    public void adminSudahLoginDanBeradaDiHalamanSubjects() {
        DriverUtil.getInstance().get("http://localhost:8074/SIMNS/");
        loginPage.inputUsername("admin");
        loginPage.inputPassword("admin123");
        loginPage.clickButtonLogin();
        
        sidebarComponent.clickSubjects();
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @When("Admin mengisi subject name {string}")
    public void adminMengisiSubjectName(String name) {
        subjectPage.inputSubjectName(name);
    }

    @And("Admin mengisi subject code {string}")
    public void adminMengisiSubjectCode(String code) {
        subjectPage.inputSubjectCode(code);
    }

    @And("Admin mengisi description {string}")
    public void adminMengisiDescription(String desc) {
        subjectPage.inputDescription(desc);
    }

    @And("Admin klik tombol Add Subject")
    public void adminKlikTombolAddSubject() {
        subjectPage.clickAddSubject();
    }

    @Then("Subject baru {string} seharusnya muncul di daftar tabel")
    public void subjectBaruSeharusnyaMunculDiDaftarTabel(String name) {
        Assert.assertTrue(subjectPage.isSubjectInTable(name), "Subject " + name + " tidak ditemukan di tabel!");
        addScreenshot("Add_Subject_Success");
    }

    @When("Admin klik tombol Edit pada subject {string}")
    public void adminKlikTombolEditPadaSubject(String name) {
        subjectPage.clickEditSubject(name);
    }

    @And("Admin mengubah subject name menjadi {string}")
    public void adminMengubahSubjectNameMenjadi(String newName) {
        subjectPage.clearAndInputEditSubject(newName);
    }

    @And("Admin klik tombol Update Subject")
    public void adminKlikTombolUpdateSubject() {
        subjectPage.clickUpdateSubject();
    }

    @Then("Data user {string} seharusnya terupdate dengan name {string}")
    public void dataUserSeharusnyaTerupdateDenganName(String oldName, String newName) {
        String actualName = subjectPage.getSubjectName(newName);
        Assert.assertTrue(actualName.toLowerCase().contains(newName.toLowerCase()), 
            "Subject name tidak terupdate dengan benar! Expected to contain: " + newName + " but found: " + actualName);
        addScreenshot("Update_Subject_Success");
    }

    @When("Admin klik tombol delete pada name {string}")
    public void adminKlikTombolDeletePadaName(String name) {
        subjectPage.clickDeleteSubject(name);
    }

    @Then("Admin klik ok untuk hapus data subject")
    public void adminKlikOkUntukHapusDataSubject() {
        subjectPage.acceptAlert();
        addScreenshot("Delete_Subject_Success");
    }
}
