package niffler.test;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import niffler.jupiter.Category;
import niffler.jupiter.GenerateCategoryExtension;
import niffler.jupiter.GenerateSpend;
import niffler.jupiter.GenerateSpendExtension;
import niffler.model.CategoryJson;
import niffler.model.CurrencyValues;
import niffler.model.SpendJson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@ExtendWith({GenerateCategoryExtension.class, GenerateSpendExtension.class})
public class SpendsWebTest {

    static {
        Configuration.browser = "firefox";
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void doLogin() {
        Selenide.open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue("cifer");
        $("input[name='password']").setValue("12345");
        $("button[type='submit']").click();
    }

    @GenerateSpend(
        category = @Category(username = "cifer", category = "first category"),
        description = "first description",
        currency = CurrencyValues.RUB,
        amount = 5000.00
    )
    @Test
    void spendShouldBeDeletedByActionInTable(SpendJson spend) {

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

    @Category(username = "cifer", category = "first category")
    @Test
    void createCategory(CategoryJson category) {
        Assertions.assertNotNull(category, "Invalid query or record already exists in the DB!");
        Assertions.assertEquals("cifer", category.getUsername());
        Assertions.assertEquals("first category", category.getCategory());
    }
}
