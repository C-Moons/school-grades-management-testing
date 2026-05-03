package com.testing.hook;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.testing.utils.DriverUtil;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hook {
    @Before
    public void beforeHook() {
        DriverUtil.getInstance();
    }

    @After
    public void afterHook(Scenario scenario) {
        // Ambil screenshot untuk bukti di report (selalu ambil)
        final byte[] screenshot = ((TakesScreenshot) DriverUtil.getInstance()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName());
        
        // Selalu tutup browser agar scenario berikutnya bersih
        DriverUtil.destroy();
    }
}
