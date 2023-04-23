package niffler.ui;

import com.codeborne.selenide.SelenideElement;
import niffler.model.UserJson;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement username = $("input[name='username']");
    private final SelenideElement goToLoginFormBtn = $("a[href*='redirect']");
    private final SelenideElement password = $("input[name='password']");
    private final SelenideElement loginBtn = $("button[type='submit']");

    public void login(UserJson user) {
        goToLoginFormBtn.click();
        username.setValue(user.getUsername());
        password.setValue(user.getPassword());
        loginBtn.click();
    }
}
