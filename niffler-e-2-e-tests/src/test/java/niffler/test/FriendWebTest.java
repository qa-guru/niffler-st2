package niffler.test;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.User;
import niffler.jupiter.extension.UsersQueueExtension;
import niffler.model.UserJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@ExtendWith(UsersQueueExtension.class)
public class FriendWebTest extends BaseWebTest {

    @AllureId("102")
    @Test
    void friendsShouldBeVisible0(@User(userType = User.UserType.WITH_FRIENDS)UserJson user) {
        Selenide.open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(user.getUsername());
        $("input[name='password']").setValue(user.getPassword());
        $("button[type='submit']").click();

        $("a[href*= 'friends']").click();
        $$(".table tbody tr").shouldHave(sizeGreaterThan(0));
    }

    @AllureId("103")
    @Test
    void friendsShouldBeVisible1(@User(userType = User.UserType.INVITATION_SENT)UserJson user) {
        Selenide.open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(user.getUsername());
        $("input[name='password']").setValue(user.getPassword());
        $("button[type='submit']").click();

        $("a[href*= 'people']").click();
        $$(".table tbody tr").find(text("Pending invitation"))
                .should(visible);
    }


}
