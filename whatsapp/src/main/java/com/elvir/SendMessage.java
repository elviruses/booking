package com.elvir;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class SendMessage {

    private final static String FIRST_PART_URL = "https://web.whatsapp.com/send/?phone=%2B";
    private final static String SECOND_PART_URL = "&text&type=phone_number&app_absent=0";

    private WebDriver browser;

    SendMessage() {
        System.setProperty("webdriver.chrome.driver", new ClassPathResource("android.png").getPath());
        browser = new ChromeDriver();
        browser.get("https://web.whatsapp.com");
    }

    @RabbitListener(queues = "test-queue")
    public void sendMessage(String phoneNumber) {
        String url = buildUrl(phoneNumber);
        browser.get(url);

        WebElement webElement = getInput();
        webElement.sendKeys("123");
        browser.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]")).click();
    }

    private String buildUrl(String phoneNumber) {
        return FIRST_PART_URL + phoneNumber + SECOND_PART_URL;
    }

    private WebElement getInput() {
        WebElement webElement = null;
        while (webElement == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            webElement = findElement("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div/p");
            if (isIncorrectNumber()) {
                return null;
            }
        }
        return webElement;
    }

    private WebElement findElement(String xpath) {
        try {
            return browser.findElement(By.xpath(xpath));
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isIncorrectNumber() {
        WebElement popUp = findElement("//*[@id=\"app\"]/div/span[2]/div/span/div/div/div/div/div/div[1]");
        return popUp != null && popUp.getText().contains("Неверный номер телефона");
    }
}
