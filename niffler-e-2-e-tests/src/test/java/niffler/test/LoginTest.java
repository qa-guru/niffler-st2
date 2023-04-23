package niffler.test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import java.io.IOException;

import niffler.api.UserService;
import niffler.jupiter.annotation.ClasspathUser;
import niffler.model.UserJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class LoginTest extends BaseWebTest {
  
  @ValueSource(strings = {
      "testdata/dima.json",
      "testdata/emma.json"
  })
  @AllureId("104")
  @ParameterizedTest
  void loginTest(@ClasspathUser UserJson user) throws IOException {
    Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
    $("a[href*='redirect']").click();
    $("input[name='username']").setValue(user.getUsername());
    $("input[name='password']").setValue(user.getPassword());
    $("button[type='submit']").click();

    $("a[href*='friends']").click();
    $(".header").should(visible).shouldHave(text("Niffler. The coin keeper."));
  }

  @ValueSource(strings = {
          "testdata/cifer.json",
          "testdata/oldbeard.json"
  })
  @AllureId("104")
  @ParameterizedTest
  void updateUserData(@ClasspathUser UserJson user) throws IOException {

    OkHttpClient httpClient = new OkHttpClient.Builder()
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .client(httpClient)
            .baseUrl("http://127.0.0.1:8089")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    UserJson body = retrofit.create(UserService.class)
            .updateUserInfo(user)
            .execute()
            .body();
  }

}
