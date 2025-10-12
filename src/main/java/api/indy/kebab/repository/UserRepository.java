package api.indy.kebab.repository;

import api.indy.kebab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("NewClassNamingConvention")
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsernameOrEmail(String username, String email);
}
