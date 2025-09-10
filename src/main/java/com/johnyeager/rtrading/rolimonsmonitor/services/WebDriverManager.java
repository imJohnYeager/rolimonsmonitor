package com.johnyeager.rtrading.rolimonsmonitor.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

@Service
public class WebDriverManager {
    private WebDriver driver;
    private boolean driverInicializado = false;

    public WebDriver getDriver() {
        if (!driverInicializado) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu", "--no-sandbox");
            driver = new ChromeDriver(options);
            driverInicializado = true;
        }
        return driver;
    }

    public void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            driverInicializado = false;
        }
    }

    public void restartDriver() {
        closeDriver();
        getDriver(); // Recria o driver
    }
}