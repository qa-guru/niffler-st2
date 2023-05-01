package niffler.test;


import com.codeborne.selenide.Configuration;
import niffler.jupiter.annotation.WebTest;
import niffler.ui.InvitationTableForm;
import niffler.ui.LoginPage;
import niffler.ui.NavigationPanel;

@WebTest
public abstract class BaseWebTest {

  static {
    Configuration.browser = "firefox";
    Configuration.browserSize = "1920x1080";
    Configuration.timeout = 10000;
  }

  LoginPage loginPage = new LoginPage();
  NavigationPanel navigationPanel = new NavigationPanel();

  InvitationTableForm invitationTableForm = new InvitationTableForm();

}
