package com.testing.page.teacher;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyStudentPage extends BasePage {
    
    public MyStudentPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isStudentVisible(String studentName) {
        // Mencari baris yang mengandung nama student (pakai contains agar lebih kebal)
        By studentCell = By.xpath("//td[contains(normalize-space(), '" + studentName + "')]");
        return waitingElementReady(studentCell).isDisplayed();
    }

    public void clickAddGradeForStudent(String studentName) {
        // Mencari tombol 'Add Grade' di baris (tr) yang sama dengan nama student
        By addGradeBtn = By.xpath("//tr[td[contains(normalize-space(), '" + studentName + "')]]//a[contains(normalize-space(), 'Add Grade')]");
        org.openqa.selenium.WebElement btn = waitingElementReady(addGradeBtn);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void clickViewGradesForStudent(String studentName) {
        // Mencari tombol 'View Grades' di baris (tr) yang sama dengan nama student
        By viewGradesBtn = By.xpath("//tr[td[contains(normalize-space(), '" + studentName + "')]]//a[contains(normalize-space(), 'View Grades')]");
        org.openqa.selenium.WebElement btn = waitingElementReady(viewGradesBtn);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }
}
