package guru.qa.niffler.test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import guru.qa.niffler.db.dao.AuthUserDAO;
import guru.qa.niffler.db.dao.UserDataUserDAO;
import guru.qa.niffler.db.model.Authority;
import guru.qa.niffler.db.model.AuthorityEntity;
import guru.qa.niffler.db.model.UserEntity;
import guru.qa.niffler.jupiter.annotation.DAO;
import guru.qa.niffler.jupiter.extension.DaoExtension;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DaoExtension.class)
//добавить в аннотацию дао и проверить сработает ли.
public class LoginTestLesson3 extends BaseWebTest {

    @DAO
    private AuthUserDAO authUserDAO;
//    private AuthUserDAO authUserDAO = AuthUserDAO.getImpl();
    @DAO
    private UserDataUserDAO userDataUserDAO;
    private UserEntity user;

    @BeforeEach
    void createUser() {
        user = new UserEntity();
        user.setUsername("rashid_1");
        user.setPassword("12345");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAuthorities(Arrays.stream(Authority.values())
            .map(a -> {
                AuthorityEntity ae = new AuthorityEntity();
                ae.setAuthority(a);
                return ae;
            }).toList()
        );
        authUserDAO.createUser(user);
        userDataUserDAO.createUserInUserData(user);
    }

    @AfterEach
    void deleteUser() {
        userDataUserDAO.deleteUserByIdInUserData(user);
        authUserDAO.deleteUserById(user.getId());
    }

    @Test
    void mainPageShouldBeVisibleAfterLogin() {
        open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(user.getUsername());
        $("input[name='password']").setValue(user.getPassword());
        $("button[type='submit']").click();
        $(".main-content__section-stats").should(visible);
    }

}
