package com.testing.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverUtil {
    public static WebDriver driver;

    public static WebDriver getInstance() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-allow-origins=*");
            
            // Set window size secara eksplisit untuk headless mode
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--start-maximized");

            // Matikan pop-up password manager
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-password-manager-reauthentication");
            options.addArguments("--disable-features=PasswordLeakDetection");
            
            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public static void destroy() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
