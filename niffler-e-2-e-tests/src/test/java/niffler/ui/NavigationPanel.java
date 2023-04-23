package niffler.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class NavigationPanel {

    private final SelenideElement friendsTab = $("a[href*='friends']");
    private final SelenideElement peoplesTab = $(".header__navigation-item img[src*='globe']");
    private final SelenideElement logoutBtn = $(".header__logout .button-icon_type_logout");

    public NavigationPanel openTabFriends() {
        friendsTab.click();
        return this;
    }

    public NavigationPanel openTabPeoples() {
        peoplesTab.click();
        return this;
    }

    public NavigationPanel addFriend(String username) {
        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
                + username + "')]//following-sibling::td//button")).click();
        return this;
    }

    public LoginPage logout() {
        logoutBtn.click();
        return new LoginPage();
    }
}
