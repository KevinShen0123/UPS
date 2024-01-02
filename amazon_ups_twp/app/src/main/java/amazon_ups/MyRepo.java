package amazon_ups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface MyRepo{
    List<Object[]> findUserByName(String name) ;
    List<UserEntity> findUserByEmail(String name) ;
    void saveUser(UserEntity user);
}
