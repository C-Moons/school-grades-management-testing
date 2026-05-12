package com.testing.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SubjectPage extends BasePage {
    // Locators for Add Form
    private By subjectName = By.xpath("//h5[contains(., 'Add New Subject')]/ancestor::div[contains(@class, 'card')]//input[@name='name' or @id='name']");
    private By subjectCode = By.xpath("//h5[contains(., 'Add New Subject')]/ancestor::div[contains(@class, 'card')]//input[@name='code' or @id='code']");
    private By descInput = By.xpath("//h5[contains(., 'Add New Subject')]/ancestor::div[contains(@class, 'card')]//input[@name='description' or @id='description' or @name='desc']");
    private By addSubjectButton = By.xpath("//button[contains(., 'Add Subject')]");

    // Locators for Edit Modal
    private By editSubjectName = By.xpath("//div[contains(@class, 'modal')]//input[@name='name' or @id='name' or @id='edit_subject_name']");
    private By updateSubjectButton = By.xpath("//*[@id='editSubjectModal']//button[@type='submit' or contains(., 'Update')]");

    public SubjectPage(WebDriver driver) {
        super(driver);
    }

    // Methods input subject
    public void inputSubjectName(String data) {
        WebElement ele = waitingElementReady(subjectName);
        ele.clear();
        ele.sendKeys(data);
    }

    public void inputSubjectCode(String data) {
        WebElement ele = waitingElementReady(subjectCode);
        ele.clear();
        ele.sendKeys(data);
    }

    public void inputDescription(String data) {
        WebElement ele = waitingElementReady(descInput);
        ele.clear();
        ele.sendKeys(data);
    }

    public void clickAddSubject() {
        WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(addSubjectButton));
        scrollToElement(ele);
        
        try {
            ele.click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
        }
        
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        
        // Robust check for alerts and redirects
        boolean isDashboard = driver.getCurrentUrl().contains("dashboard") || driver.findElements(By.xpath("//*[contains(text(), 'Admin Dashboard')]")).size() > 0;
        
        try {
            WebElement alert = driver.findElement(By.xpath("//div[contains(@class, 'alert')]"));
            String msg = alert.getText();
            
            if (msg.toLowerCase().contains("already exists")) {
                System.out.println("CRITICAL: Subject already exists! Failing test.");
                throw new RuntimeException("Add Subject failed: " + msg);
            } else if (msg.toLowerCase().contains("successfully")) {
                System.out.println("Success: " + msg);
            } else {
                System.out.println("Alert detected: " + msg);
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // No alert found, proceed normally if not on dashboard
        }

        if (isDashboard) {
            refreshAndNavigateToSubjects();
        }
    }

    // Methods for edit subject
    public void clickEditSubject(String name) {
        refreshAndNavigateToSubjects();
        String lowerName = name.toLowerCase();
        By subjectRowXpath = By.xpath("//tr[td[translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + lowerName + "']]");
        By editBtnXpath = By.xpath(".//button[contains(., 'Edit')] | .//a[contains(., 'Edit')] | .//*[contains(@class, 'edit')]");
        
        // Pagination support
        boolean found = false;
        for (int i = 0; i < 5; i++) {
            if (!driver.findElements(subjectRowXpath).isEmpty()) {
                WebElement row = driver.findElement(subjectRowXpath);
                WebElement editBtn = row.findElement(editBtnXpath);
                scrollToElement(editBtn);
                try {
                    editBtn.click();
                } catch (Exception e) {
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);
                }
                found = true;
                break;
            }
            // Try next page
            try {
                WebElement next = driver.findElement(By.xpath("//li[contains(@class, 'next')]/a | //a[contains(., 'Next')]"));
                if (next.isDisplayed() && !next.getAttribute("class").contains("disabled")) {
                    scrollToElement(next);
                    next.click();
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                } else break;
            } catch (Exception e) { break; }
        }
        
        if (!found) throw new RuntimeException("Subject " + name + " not found in table even after pagination");

        // Verify modal opened
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editSubjectModal")));
        } catch (Exception e) {
            // Debug: Print row content
            try {
                System.out.println("Row content for " + name + ": " + driver.findElement(subjectRowXpath).getAttribute("innerHTML"));
            } catch (Exception e2) {}
            
            // Retry click with even broader locator after a short wait
            try { Thread.sleep(1000); } catch (InterruptedException ex) {}
            try {
                WebElement editBtn = driver.findElement(subjectRowXpath).findElement(By.xpath(".//*[contains(text(), 'Edit')]"));
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);
            } catch (Exception e3) {}
            
            // Final check with broader locator
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'show')] | //div[@id='editSubjectModal']")));
        }
        
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    public void clearAndInputEditSubject(String data) {
        // Wait for specific modal to be fully visible before interacting
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editSubjectModal")));
        WebElement ele = waitingElementReady(editSubjectName);
        scrollToElement(ele);
        
        // Robust clearing
        ele.clear();
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        if (ele.getAttribute("value").length() > 0) {
            ele.sendKeys(org.openqa.selenium.Keys.CONTROL + "a");
            ele.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
        }
        
        ele.sendKeys(data);
    }

    public void clickUpdateSubject() {
        WebElement ele = waitingElementReady(updateSubjectButton);
        scrollToElement(ele);
        try {
            ele.click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
        }
        
        // Wait and check if modal still there (indicator of failure)
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        try {
            if (!driver.findElements(By.xpath("//div[contains(@class, 'modal')]")).isEmpty()) {
                // If modal still there, try ENTER on the name field
                driver.findElement(editSubjectName).sendKeys(org.openqa.selenium.Keys.ENTER);
            }
        } catch (Exception e) {}

        // Wait for modal to disappear
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class, 'modal')]")));
        } catch (Exception e) {}
        
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
    }

    // Methods for Delete Subject
    public void clickDeleteSubject(String name) {
        String lowerName = name.toLowerCase();
        By subjectRowXpath = By.xpath("//tr[td[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + lowerName + "')]]");
        By deleteBtnXpath = By.xpath(".//button[contains(@class, 'danger') or contains(., 'Delete')] | .//a[contains(@class, 'danger') or contains(., 'Delete')]");
        
        // Pagination support
        boolean found = false;
        for (int i = 0; i < 5; i++) {
            if (!driver.findElements(subjectRowXpath).isEmpty()) {
                WebElement row = driver.findElement(subjectRowXpath);
                WebElement deleteBtn = row.findElement(deleteBtnXpath);
                scrollToElement(deleteBtn);
                try {
                    deleteBtn.click();
                } catch (Exception e) {
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
                }
                found = true;
                break;
            }
            // Try next page
            try {
                WebElement next = driver.findElement(By.xpath("//li[contains(@class, 'next')]/a | //a[contains(., 'Next')]"));
                if (next.isDisplayed() && !next.getAttribute("class").contains("disabled")) {
                    scrollToElement(next);
                    next.click();
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                } else break;
            } catch (Exception e) { break; }
        }
        
        if (!found) throw new RuntimeException("Subject " + name + " not found for deletion even after pagination");
    }

    private void refreshAndNavigateToSubjects() {
        driver.navigate().refresh();
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        
        // Ensure we are on Subjects page by clicking the sidebar link
        try {
            By sidebarSubjects = By.xpath("//*[@id='sidebar']//a[contains(., 'Subjects')] | //nav//a[contains(., 'Subjects')] | //a[contains(@href, 'subjects')]");
            WebElement subjectsLink = wait.until(ExpectedConditions.elementToBeClickable(sidebarSubjects));
            scrollToElement(subjectsLink);
            try {
                subjectsLink.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", subjectsLink);
            }
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
        } catch (Exception e) {
            System.out.println("Could not click Subjects sidebar link: " + e.getMessage());
        }
    }

    public boolean isSubjectInTable(String name) {
        // Ensure we are on the subjects page
        if (driver.findElements(By.xpath("//table")).isEmpty()) {
            refreshAndNavigateToSubjects();
        }
        
        String lowerName = name.toLowerCase();
        By subjectRow = By.xpath("//table//tr[td[translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + lowerName + "']]");
        
        // Pagination support
        for (int i = 0; i < 5; i++) {
            if (!driver.findElements(subjectRow).isEmpty()) {
                return true;
            }
            // Try next page
            try {
                WebElement next = driver.findElement(By.xpath("//li[contains(@class, 'next')]/a | //a[contains(., 'Next')]"));
                if (next.isDisplayed() && !next.getAttribute("class").contains("disabled")) {
                    scrollToElement(next);
                    next.click();
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                } else break;
            } catch (Exception e) { break; }
        }
        
        // Last resort: refresh and try once more
        refreshAndNavigateToSubjects();
        return !driver.findElements(subjectRow).isEmpty();
    }

    public String getSubjectName(String name) {
        refreshAndNavigateToSubjects();
        String lowerName = name.toLowerCase();
        
        // Target the specific cell that matches the name exactly
        By nameCell = By.xpath("//table//td[translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + lowerName + "']");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(nameCell)).getText().trim();
        } catch (Exception e) {
            // Fallback: search across pages
            if (isSubjectInTable(name)) {
                 return wait.until(ExpectedConditions.visibilityOfElementLocated(nameCell)).getText().trim();
            }
            throw e;
        }
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }
}
