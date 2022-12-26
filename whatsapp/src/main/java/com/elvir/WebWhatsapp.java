package com.elvir;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class WebWhatsapp {

    private WebDriver browser;

    private boolean isStarted = false;

    private long sleepTime = 3000;

    WebWhatsapp() {
        System.setProperty("webdriver.chrome.driver", "C:\\\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.get("https://web.whatsapp.com");
    }

    public void openWhatsapp() {
        /**
         * wait till whatsapp is loaded after scanning the QR code then type
         * start in console to start sending messages
         */
        Scanner sc = new Scanner(System.in);
        String command = sc.next();
        if (!command.equalsIgnoreCase("start")) {
            browser.quit();
            System.exit(1);
        }
        sc.close();

        File file = ((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE);
//        String screenshotBase64 = ((TakesScreenshot) browser).getScreenshotAs(OutputType.BASE64);

        File DestFile = new File("C://jetbra/test.png");
        try {
            FileCopyUtils.copy(file, DestFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * keep checking for unread count every sleepTime milli secs.
         * If some elementFound, then click it and set isStarted to true
         * The while will check for this variable, and then will reply on that element.
         */
        while (true) {
            try {
                if (isStarted) {
                    /**
                     *  once the new messages are found
                     *  get the last message and reply to the user according to it:
                     */
                    WebElement selectedWindow = browser.findElement(By.xpath("//div[contains(@class, 'message-list')]"));
                    List<WebElement> msgList = selectedWindow.findElements(By.xpath("//div[contains(@class,'msg')]"));
                    WebElement lastMsgDiv = msgList.get(msgList.size() - 1);
                    WebElement lastMsgSpan = lastMsgDiv.findElement(
                            By.xpath("//span[contains(@class, 'selectable-text')]")
                    );
                    String msg = lastMsgSpan.getText();
                    reply("I was already chatting with you: " + msg);
                    isStarted = false;
                }

                /**
                 *  get the user who just pinged, whose 'unread-count' will be 1 or more
                 */
                List<WebElement> nonSelectedWindows = browser.findElements(By
                        .xpath("//span[contains(@class,'unread-count')]"));
                if (!Utils.isEmptyOrNull(nonSelectedWindows)) {
                    isStarted = true;
                    responseNonSelectedWindow(nonSelectedWindows);
                } else {
                    Utils.log("no new msg yet");
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        browser.quit();
    }

    /**
     * Select the user with unread-count and click on it to start chatting with him/her
     * @param elems
     */
    private void responseNonSelectedWindow(List<WebElement> elems) {
        Utils.log("new msgs found");
        for (WebElement elem : elems) {
            elem.click();
            reply("your chat was not selected. Now it is.");
        }
    }

    private void reply(String string) {
        List<WebElement> elem1 = browser.findElements(By.className("input"));
        for (int i = 0; i < 1; i++) {
            elem1.get(1).sendKeys(string);//
            browser.findElement(By.className("send-container")).click();
        }
    }
}
