package com.testing.page.teacher.components;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testing.page.admin.IPageComponent;

public class BaseComponent implements IPageComponent{
    public static final int WAITING_TIME_IN_SECONDS = 50;
    public static final int WAITING_TIME_IN_SECONDS_FOR_DISPLAYED = 5;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait waitDisplayed;

    public BaseComponent(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAITING_TIME_IN_SECONDS));
        waitDisplayed = new WebDriverWait(driver, Duration.ofSeconds(WAITING_TIME_IN_SECONDS_FOR_DISPLAYED));
    }

    public WebElement waitingElementReady(By elementBy) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated((elementBy)));
        return element;
    }

    public WebElement waitingElementForCheckingDisplay(By elementBy) {
        WebElement element = waitDisplayed.until(ExpectedConditions.visibilityOfElementLocated((elementBy)));
        return element;
    }

    @Override
    public List<WebElement> waitingElementsReady(By elementsBy) {
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((elementsBy)));
        return elements;
    }
}
