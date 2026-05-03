package com.testing.page.student;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testing.page.student.components.SidebarComponent;

public class PerformancePage extends BasePage {

    private SidebarComponent sidebarComponent;

    private By PerformanceMenu = By.xpath("//a[contains(., 'Performance')]");

    public PerformancePage(WebDriver driver) {
        super(driver);
    }
        public SidebarComponent getSidebarComponent() {
        return sidebarComponent;
    }

    public void selectPerformance(){
        waitingElementReady(PerformanceMenu).click();
    }
    
}
