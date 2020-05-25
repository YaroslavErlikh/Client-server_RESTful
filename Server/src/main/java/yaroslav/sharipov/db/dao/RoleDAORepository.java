package yaroslav.sharipov.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yaroslav.sharipov.model.role.RoleDAO;

@Repository
public interface RoleDAORepository extends JpaRepository<RoleDAO, Long> {
}
