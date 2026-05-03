package com.testing.page.admin;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    public static final int WAITING_TIME_IN_SECONDS = 10;
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAITING_TIME_IN_SECONDS));
    }

    public WebElement waitingElementReady(By elementBy) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated((elementBy)));
        return element;
    }

    public WebElement waitingElementClickable(By elementBy) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable((elementBy)));
        return element;
    }

    public List<WebElement> waitingElementsReady(By elementsBy) {
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((elementsBy)));
        return elements;
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
