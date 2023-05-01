package niffler.ui;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class InvitationTableForm {

    public void checkInvitationBtnOfUser(String username) {
        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
                + username + "')]//following-sibling::td//button[@class='button-icon button-icon_type_submit']"))
                .shouldBe(Condition.visible);
    }

    public void checkThatInvitationListIsEmpty() {
        $$(".table tbody").shouldHave(sizeGreaterThan(0));
    }
}
