package com.elvir.handlers;

import com.elvir.library.mq.WhatsAppMessage;
import com.elvir.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class SendMessage {

    private WebDriver browser;

    SendMessage() {
        System.setProperty("webdriver.chrome.driver", new ClassPathResource("chromedriver.exe").getPath());
        browser = new ChromeDriver();
        browser.get("https://web.whatsapp.com");
    }

    @RabbitListener(queues = "${rabbitmq.whatsapp}")
    public void sendMessage(WhatsAppMessage whatsAppMessage) {
        String url = Utils.buildUrl(whatsAppMessage.getPhone());
        browser.get(url);

        WebElement webElement = getInput();
        webElement.sendKeys(whatsAppMessage.getMessage());
        browser.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]")).click();
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
