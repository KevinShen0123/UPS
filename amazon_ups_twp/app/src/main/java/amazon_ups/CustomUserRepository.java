package amazon_ups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomUserRepository extends JpaRepository<UserEntity,Long>,MyRepo {
}