package niffler.test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static niffler.jupiter.annotation.User.UserType.INVITATION_SENT;
import static niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.User;
import niffler.jupiter.extension.UsersQueueExtension;
import niffler.model.UserJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(UsersQueueExtension.class)
public class FriendsWebTest extends BaseWebTest {

  @AllureId("102")
  @Test
  void invitationShouldBeVisible(@User(userType = WITH_FRIENDS) UserJson user1,
                               @User(userType = INVITATION_SENT) UserJson user2) {
    Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));

    loginPage.login(user1);
    navigationPanel
        .openTabFriends();
    invitationTableForm
        .checkThatInvitationListIsEmpty();
    navigationPanel
        .logout()
        .login(user2);
    navigationPanel
        .openTabPeoples()
        .addFriend(user1.getUsername())
        .logout();
    loginPage.login(user1);
    navigationPanel
        .openTabFriends();
    invitationTableForm
        .checkInvitationBtnOfUser(user2.getUsername());

  }

  @AllureId("103")
  @Test
  void friendsShouldBeVisible2(@User(userType = WITH_FRIENDS) UserJson user1,
                               @User(userType = INVITATION_SENT) UserJson user2) {
    Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
    $("a[href*='redirect']").click();
    $("input[name='username']").setValue(user1.getUsername());
    $("input[name='password']").setValue(user1.getPassword());
    $("button[type='submit']").click();

    $("a[href*='people']").click();
    $$(".table tbody tr").find(Condition.text("Pending invitation"))
        .should(Condition.visible);
  }

  @AllureId("103")
  @Test
  void friendsShouldBeVisible3(@User(userType = WITH_FRIENDS) UserJson user1,
                               @User(userType = INVITATION_SENT) UserJson user2) {
    Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
    $("a[href*='redirect']").click();
    $("input[name='username']").setValue(user1.getUsername());
    $("input[name='password']").setValue(user1.getPassword());
    $("button[type='submit']").click();

    $("a[href*='people']").click();
    $$(".table tbody tr").find(Condition.text("Pending invitation"))
            .should(Condition.visible);
  }

}
