package com.testing.page.student;

import org.openqa.selenium.WebDriver;
import com.testing.page.student.components.SidebarComponent;

public class DashboardPage extends BasePage{
private SidebarComponent sidebarComponent;

    public DashboardPage(WebDriver driver) {
        super(driver);
        sidebarComponent = new SidebarComponent(driver);
    }

    public SidebarComponent getSidebarComponent() {
        return sidebarComponent;
    }

    public String getPathURL() {
        String[] path = driver.getCurrentUrl().split("/");
        return "/" + path[path.length - 1];
    }
}
