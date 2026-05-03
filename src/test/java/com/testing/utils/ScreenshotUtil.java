package com.testing.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ScreenshotUtil() {
        // Utility class
    }

    /**
     * Takes a screenshot of the current browser window and saves it to a file.
     *
     * @param driver   The WebDriver instance.
     * @param fileName The desired name for the screenshot file (without extension).
     */
    public static String takeScreenshot(WebDriver driver, String fileName) {
        // 1. Convert WebDriver object to TakesScreenshot
        TakesScreenshot screenshot = (TakesScreenshot) driver;

        // 2. Call getScreenshotAs method to create image file
        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

        // 3. Define the destination path
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String destinationPath = "target/screenshots/" + fileName + "_" + timestamp + ".png";
        File destinationFile = new File(destinationPath);

        try {
            // Ensure the directory exists
            destinationFile.getParentFile().mkdirs();
            // 4. Copy file to destination
            FileUtils.copyFile(sourceFile, destinationFile);
            System.out.println("Screenshot saved to: " + destinationFile.getAbsolutePath());
            return "../screenshots/" + fileName + "_" + timestamp + ".png";
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null; // Return null if screenshot failed
        }
    }
}
