package amazon_ups;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Service;
@Service
public class CustomUserRepositoryImpl implements MyRepo{
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    @Override
    @Transactional
    public List<Object[]> findUserByName(String name) {
        System.out.println("find called!!!!!!!");
        List<Object[]> users=entityManager.createNativeQuery("SELECT * FROM user_entity WHERE username=?;").setParameter(1,name).getResultList();
        System.out.println("find done!!!!!!!!!!!!");
        if(users.size()==0){
           return null;
        }
        return users;
    }

    @Override
    @Transactional
    public List<UserEntity> findUserByEmail(String email) {
        List<UserEntity> users=entityManager.createNativeQuery("SELECT * FROM user_entity WHERE email=?;").setParameter(1,email).getResultList();
        if(users.size()==0){
            return null;
        }
        return users;
    }

    @Override
    @Transactional
    public void saveUser(UserEntity user) {
        System.out.println("save user called!");
        entityManager.createNativeQuery("INSERT INTO user_entity(username,password,email) VALUES(?,?,?);").setParameter(1,user.getUsername()).setParameter(2,user.getPassword()).setParameter(3,user.getEmail()).executeUpdate();
        System.out.println("save user done!");
    }
}
