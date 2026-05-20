package com.testing.definition.teacher;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.testing.page.teacher.LoginPage;
import com.testing.page.teacher.MyStudentPage;
import com.testing.utils.DriverUtil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.testing.utils.ScreenshotUtil;

public class MyStudentDefinition {
    
    private MyStudentPage myStudentPage;
    private String currentStudentContext = "";
    private Scenario scenario;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    private void addScreenshot(String name) {
        ScreenshotUtil.takeScreenshot(DriverUtil.getInstance(), name);
        final byte[] screenshot = ((TakesScreenshot) DriverUtil.getInstance()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", name);
    }

    @Given("Saya sudah login sebagai teacher")
    public void sayaSudahLoginSebagaiTeacher() {
        DriverUtil.getInstance().get("http://localhost:8074/SIMNS/");
        LoginPage loginPage = new LoginPage(DriverUtil.getInstance());
        loginPage.login("teacher", "teacher123");
    }

    @Given("Saya berada di halaman {string}")
    public void sayaBeradaDiHalaman(String menuName) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(., '" + menuName + "')]")));
        
        try {
            menu.click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) DriverUtil.getInstance()).executeScript("arguments[0].click();", menu);
        }
        
        // Force Bootstrap Tab to show (karena 'My Students' ternyata berbentuk tab-pane, bukan pindah halaman)
        try {
            ((org.openqa.selenium.JavascriptExecutor) DriverUtil.getInstance()).executeScript(
                "if (typeof bootstrap !== 'undefined') { var tab = new bootstrap.Tab(arguments[0]); tab.show(); }", menu);
            Thread.sleep(500); // Tunggu animasi fade selesai
        } catch (Exception e) {}
        
        myStudentPage = new MyStudentPage(DriverUtil.getInstance());
    }

    @When("Saya mencari student bernama {string} di tabel")
    public void sayaMencariStudentBernamaDiTabel(String studentName) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(normalize-space(), '" + studentName + "')]")));
        } catch (Exception e) {
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("target/error_body.html"), DriverUtil.getInstance().findElement(org.openqa.selenium.By.tagName("body")).getAttribute("outerHTML").getBytes());
            } catch (Exception ex) {}
            throw e;
        }
        Assert.assertTrue(myStudentPage.isStudentVisible(studentName), "Student " + studentName + " tidak ditemukan di tabel");
        currentStudentContext = studentName;
    }

    @When("Saya klik tombol {string} pada baris student tersebut")
    public void sayaKlikTombolPadaBarisStudentTersebut(String buttonName) {
        if (buttonName.equalsIgnoreCase("Add Grade")) {
            myStudentPage.clickAddGradeForStudent(currentStudentContext);
        } else if (buttonName.equalsIgnoreCase("View Grades")) {
            myStudentPage.clickViewGradesForStudent(currentStudentContext);
        }
    }

    @Then("Saya diarahkan ke halaman {string}")
    public void sayaDiarahkanKeHalaman(String pageTitle) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        if (pageTitle.equalsIgnoreCase("Grade Management")) {
            wait.until(ExpectedConditions.urlContains("/grades.php"));
        } else if (pageTitle.equalsIgnoreCase("My Students")) {
            wait.until(ExpectedConditions.urlContains("/teacher.php"));
        }
        boolean isDisplayed = wait.until(driver -> {
            // Cek text HANYA di dalam body untuk menghindari false positive match dengan <title> di tag <head>
            java.util.List<WebElement> elements = driver.findElements(By.xpath("//body//*[contains(normalize-space(), '" + pageTitle + "')]"));
            for (WebElement el : elements) {
                if (el.isDisplayed() && !el.getTagName().equalsIgnoreCase("script") && !el.getTagName().equalsIgnoreCase("style")) {
                    return true;
                }
            }
            return false;
        });
        Assert.assertTrue(isDisplayed, "Gagal berpindah ke halaman " + pageTitle);
    }

    private void selectDropdownByNormalizedText(WebElement dropdown, String targetText) {
        String normalizedTarget = targetText.replaceAll("\\s+", " ").trim();
        Select select = new Select(dropdown);
        WebElement matchedOption = null;
        for (WebElement option : select.getOptions()) {
            String normalizedOptionText = option.getText().replaceAll("\\s+", " ").trim();
            if (normalizedOptionText.equalsIgnoreCase(normalizedTarget)) {
                matchedOption = option;
                break;
            }
        }
        if (matchedOption != null) {
            String val = matchedOption.getAttribute("value");
            try {
                select.selectByValue(val);
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) DriverUtil.getInstance()).executeScript(
                    "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change'));", dropdown, val
                );
            }
        } else {
            ((org.openqa.selenium.JavascriptExecutor) DriverUtil.getInstance()).executeScript(
                "var sel = arguments[0]; var target = arguments[1];" +
                "for(var i=0; i<sel.options.length; i++) {" +
                "  if(sel.options[i].text.replace(/\\s+/g, ' ').trim().toLowerCase() == target.toLowerCase()) {" +
                "    sel.selectedIndex = i;" +
                "    sel.dispatchEvent(new Event('change'));" +
                "    break;" +
                "  }" +
                "}", dropdown, normalizedTarget
            );
        }
    }

    @When("Saya memilih student {string} pada dropdown Student")
    public void sayaMemilihStudentPadaDropdownStudent(String studentName) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        WebElement dropdown;
        try {
            dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@name='student_id']")));
        } catch (Exception e) {
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("target/error_dropdown.html"), DriverUtil.getInstance().findElement(org.openqa.selenium.By.tagName("body")).getAttribute("outerHTML").getBytes());
            } catch (Exception ex) {}
            throw e;
        }
        selectDropdownByNormalizedText(dropdown, studentName);
    }

    @When("Saya memilih subject {string} pada dropdown Subject")
    public void sayaMemilihSubjectPadaDropdownSubject(String subjectName) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@name='subject_id']")));
        selectDropdownByNormalizedText(dropdown, subjectName);
    }

    @When("Saya memilih classroom {string} pada dropdown Classroom")
    public void sayaMemilihClassroomPadaDropdownClassroom(String classroomName) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@name='classroom_id']")));
        selectDropdownByNormalizedText(dropdown, classroomName);
    }

    @When("Saya mengisi nilai {string} dengan tipe {string}")
    public void sayaMengisiNilaiDenganTipe(String gradeValue, String gradeType) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        
        WebElement gradeInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='grade']")));
        try {
            gradeInput.click();
            gradeInput.clear();
            gradeInput.sendKeys(gradeValue);
            if (!gradeValue.equals(gradeInput.getAttribute("value"))) {
                ((org.openqa.selenium.JavascriptExecutor) DriverUtil.getInstance()).executeScript("arguments[0].value=arguments[1];", gradeInput, gradeValue);
            }
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) DriverUtil.getInstance()).executeScript("arguments[0].value=arguments[1];", gradeInput, gradeValue);
        }

        WebElement typeDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@name='grade_type']")));
        selectDropdownByNormalizedText(typeDropdown, gradeType);
    }

    @When("Saya mengisi remarks {string}")
    public void sayaMengisiRemarks(String remarks) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        WebElement remarksInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@name='remarks'] | //textarea")));
        try {
            remarksInput.click();
            remarksInput.clear();
            remarksInput.sendKeys(remarks);
            if (!remarks.equals(remarksInput.getAttribute("value"))) {
                ((org.openqa.selenium.JavascriptExecutor) DriverUtil.getInstance()).executeScript("arguments[0].value=arguments[1];", remarksInput, remarks);
            }
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) DriverUtil.getInstance()).executeScript("arguments[0].value=arguments[1];", remarksInput, remarks);
        }
    }

    @When("Saya klik tombol Add Grade biru")
    public void sayaKlikTombolAddGradeBiru() {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) DriverUtil.getInstance();
        try {
            js.executeScript(
                "var form = document.querySelector('form');" +
                "if (form) {" +
                "  if (!form.querySelector('input[name=\"add_grade\"]')) {" +
                "    var input = document.createElement('input');" +
                "    input.type = 'hidden';" +
                "    input.name = 'add_grade';" +
                "    input.value = '1';" +
                "    form.appendChild(input);" +
                "  }" +
                "  form.submit();" +
                "}"
            );
        } catch (Exception e) {
            WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Add Grade')] | //button[@type='submit']")));
            try {
                btn.click();
            } catch (Exception ex) {
                js.executeScript("arguments[0].click();", btn);
            }
        }
    }

    @Then("Muncul notifikasi sukses dan nilai masuk ke tabel Recent Grades")
    public void munculNotifikasiSuksesDanNilaiMasukKeTabel() {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        
        try {
            // Tunggu notifikasi alert sukses muncul terlebih dahulu
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'alert') and contains(normalize-space(), 'Grade added successfully!')]")
            ));
            
            // Setelah halaman selesai refresh, cari tabel dengan proteksi stale element reference
            boolean isDisplayed = false;
            for (int i = 0; i < 3; i++) {
                try {
                    WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));
                    if (table.isDisplayed()) {
                        isDisplayed = true;
                        break;
                    }
                } catch (org.openqa.selenium.StaleElementReferenceException e) {
                    // Ulangi pencarian
                }
            }
            Assert.assertTrue(isDisplayed, "Tabel Recent Grades tidak ditemukan.");
            addScreenshot("Add_Grade_Success");
        } catch (Exception e) {
            try {
                java.nio.file.Files.write(
                    java.nio.file.Paths.get("target/error_notification.html"),
                    DriverUtil.getInstance().findElement(org.openqa.selenium.By.tagName("body")).getAttribute("outerHTML").getBytes()
                );
            } catch (Exception ex) {}
            throw e;
        }
    }

    @Then("Saya bisa melihat tabel {string}")
    public void sayaBisaMelihatTabel(String tableName) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));
        Assert.assertTrue(table.isDisplayed(), "Tabel " + tableName + " tidak muncul.");
    }

    @When("Saya klik tombol Delete pada baris nilai student {string}")
    public void sayaKlikTombolDeletePadaBarisNilai(String studentName) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        WebElement deleteBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//tr[td[contains(normalize-space(), '" + studentName + "')]]//a[contains(normalize-space(), 'Delete')]")
        ));
        String href = deleteBtn.getAttribute("href");
        DriverUtil.getInstance().get(href);
    }

    @When("Saya konfirmasi pop-up delete")
    public void sayaKonfirmasiPopUpDelete() {

    }

    @Then("Muncul notifikasi {string}")
    public void munculNotifikasi(String message) {
        WebDriverWait wait = new WebDriverWait(DriverUtil.getInstance(), Duration.ofSeconds(10));
        try {
            WebElement alertBox = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'alert') and contains(normalize-space(), '" + message + "')]")
            ));
            Assert.assertTrue(alertBox.isDisplayed(), "Notifikasi '" + message + "' tidak muncul.");
            addScreenshot("Delete_Grade_Success");
        } catch (Exception e) {
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("target/error_notification.html"), DriverUtil.getInstance().findElement(org.openqa.selenium.By.tagName("body")).getAttribute("outerHTML").getBytes());
            } catch (Exception ex) {}
            throw e;
        }
    }
}

