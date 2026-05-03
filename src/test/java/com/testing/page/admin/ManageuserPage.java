package com.testing.page.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ManageuserPage extends BasePage {

    private By firstName = By.name("first_name");
    private By lastName = By.name("last_name");
    private By usernameInput = By.name("username");
    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");
    private By roleSelect = By.name("role");
    private By addUserButton = By.name("add_user");

    private By editFirstName = By.id("edit_first_name");
    private By updateButton = By.xpath("//div[@id='editUserModal']//button[contains(., 'Update')]");

    public ManageuserPage(WebDriver driver) {
        super(driver);
    }

    public void inputFirstName(String data) {
        waitingElementReady(firstName).sendKeys(data);
    }

    public void inputLastName(String data) {
        waitingElementReady(lastName).sendKeys(data);
    }

    public void inputUsername(String data) {
        waitingElementReady(usernameInput).sendKeys(data);
    }

    public void inputEmail(String data) {
        waitingElementReady(emailInput).sendKeys(data);
    }

    public void inputPassword(String data) {
        waitingElementReady(passwordInput).sendKeys(data);
    }

    public void selectRole(String role) {
        Select select = new Select(waitingElementReady(roleSelect));
        try {
            select.selectByVisibleText(role);
        } catch (Exception e) {
            select.selectByValue(role);
        }
    }

    public void clickAddUser() {
        waitingElementClickable(addUserButton).click();
    }

    public boolean isUserInTable(String username) {
        By userRow = By.xpath("//table//td[text()='" + username + "']");
        try {
            return waitingElementReady(userRow).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Methods for Edit
    public void clickEditUser(String username) {
        By editBtn = By.xpath("//td[text()='" + username + "']/following-sibling::td//button[contains(., 'Edit')]");
        waitingElementClickable(editBtn).click();
    }

    public void clearAndInputEditFirstName(String data) {
        waitingElementReady(editFirstName).clear();
        waitingElementReady(editFirstName).sendKeys(data);
    }

    public void clickUpdateUser() {
        waitingElementClickable(updateButton).click();
    }

    public String getUserFirstName(String username) {
        By nameCell = By.xpath("//tr[td[contains(., '" + username + "')]]/td[contains(., ' ') and not(contains(., '@'))]");
        return waitingElementReady(nameCell).getText();
    }

    // Methods for Change Password
    public void clickChangePassword(String username) {
        By changeBtn = By.xpath("//td[text()='" + username + "']/following-sibling::td//button[contains(., 'Password')]");
        waitingElementClickable(changeBtn).click();
    }

    public void newPassword(String data) {
        // Pastikan mengetik di dalam modal changePasswordModal
        By newPwField = By.xpath("//div[@id='changePasswordModal']//input[@id='new_password' or @name='new_password' or @id='change_password']");
        waitingElementReady(newPwField).clear();
        waitingElementReady(newPwField).sendKeys(data);
    }

    public void confirmPassword(String data) {
        By confirmPwField = By.xpath("//div[@id='changePasswordModal']//input[@id='confirm_password' or @name='confirm_password']");
        waitingElementReady(confirmPwField).clear();
        waitingElementReady(confirmPwField).sendKeys(data);
    }

    public void clickChangePasswordButton() {
        // Biasanya tombolnya ada di modal #changePasswordModal
        By btn = By.xpath("//div[@id='changePasswordModal']//button[contains(., 'Update') or contains(., 'Change')]");
        waitingElementClickable(btn).click();
    }

    public String getSuccessMessage() {
        By alert = By.xpath("//div[contains(@class, 'alert-success')]");
        return waitingElementReady(alert).getText();
    }
}