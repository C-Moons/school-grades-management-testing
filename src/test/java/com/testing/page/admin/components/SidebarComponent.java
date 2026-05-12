package com.testing.page.admin.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SidebarComponent extends BaseComponent {

    private By manageUsers = By.xpath("//a[contains(., 'Manage Users')]");
    private By subjects = By.xpath("//a[contains(., 'Subjects')]");

    public SidebarComponent(WebDriver driver) {
        super(driver);
    }
    
    public void clickManageUser(){
        WebElement element = waitingElementReady(manageUsers);
        element.click();
    }

    public void clickSubjects(){
        WebElement element = waitingElementReady(subjects);
        element.click();
    }
}
