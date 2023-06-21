package guru.qa.niffler.test.web;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import com.github.tomakehurst.wiremock.client.WireMock;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.GenerateUser;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static guru.qa.niffler.condition.SpendCondition.spends;

public class SpendsWebTest extends BaseWebTest {


    private WireMock wireMock = new WireMock("userdata.niffler.dc", 9000);

    @ApiLogin(user = @GenerateUser(
            categories = @Category("Обучение"),
            spends = @GenerateSpend(
                    description = "QA GURU ADVANCED VOL 2",
                    currency = CurrencyValues.RUB,
                    amount = 52000.00
            )
    ))
    @AllureId("107")
    @Test
    void spendShouldBeDeletedByActionInTable(UserJson user) {
        final SpendJson spend = user.getSpends().get(0);

        Selenide.open(CFG.getFrontUrl() + "/main");

        $(".spendings-table tbody").$$("tr")
                .find(text(spend.getDescription()))
                .$$("td").first()
                .scrollTo()
                .click();

        $$(".button_type_small").find(text("Delete selected"))
                .click();

        $(".spendings-table tbody")
                .$$("tr")
                .shouldHave(CollectionCondition.size(0));
    }

    @ApiLogin(user = @GenerateUser(
            categories = @Category("Обучение"),
            spends = @GenerateSpend(
                    description = "QA GURU ADVANCED VOL 2",
                    currency = CurrencyValues.RUB,
                    amount = 52000.00
            )
    ))
    @AllureId("108")
    @Test
    void spendInTableShouldBeEqualToGiven(UserJson user) {
        final SpendJson spend = user.getSpends().get(0);

        Selenide.open(CFG.getFrontUrl() + "/main");

        $(".spendings-table tbody")
                .$$("tr")
                .shouldHave(spends(spend));
    }
}
