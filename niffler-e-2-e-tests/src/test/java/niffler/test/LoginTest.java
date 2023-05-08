package niffler.test;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.ClassPathUser;
import niffler.model.UserJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LoginTest extends BaseWebTest {

    @ValueSource(strings = {
            "testdata/dima.json",
            "testdata/emma.json"
    })
    @AllureId("104")
    @Test
    void loginTest(@ClassPathUser UserJson user) throws IOException {
        Allure.step("Open page", () ->  Selenide.open("http://127.0.0.1:3000/main"));
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(user.getUsername());
        $("input[name='password']").setValue(user.getPassword());
        $("button[type='submit']").click();

        $("a[href*= 'people']").click();
        $(".header").should(visible).shouldHave(text("Niffler. The coin keeper."));
    }
}
