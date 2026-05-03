package com.testing.page.student;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.testing.page.student.components.SidebarComponent;

public class MygradesPage extends BasePage{

    private SidebarComponent sidebarComponent;

    private By menuMyGrades = By.xpath("//a[contains(., 'My Grades')]");

    public MygradesPage(WebDriver driver) {
        super(driver);
    }
        public SidebarComponent getSidebarComponent() {
        return sidebarComponent;
    }

    public void selectMyGrade(){
        waitingElementReady(menuMyGrades).click();
    }
}
