package yaroslav.sharipov.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yaroslav.sharipov.model.UserDAO;

@Repository
public interface UserDAORepository extends JpaRepository<UserDAO, Long> {

    @Query("SELECT user from UserDAO user where user.username = :username")
    UserDAO findByUsername(String username);
}
