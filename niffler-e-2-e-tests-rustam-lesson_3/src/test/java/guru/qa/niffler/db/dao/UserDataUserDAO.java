package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.UserEntity;
import java.util.UUID;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserDataUserDAO { ;

  int createUserInUserData(UserEntity user);

  void deleteUserByIdInUserData(UUID userId);
}
