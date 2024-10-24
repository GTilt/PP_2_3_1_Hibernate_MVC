package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.List;


@Repository
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getUsers() {
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void deleteUser(User user) {
        if (user.getId() != null) {
            User existingUser = em.find(User.class, user.getId());
            if (existingUser != null) {
                em.remove(existingUser);
            }
        }
    }

    @Transactional
    @Override
    public User addUser(User user) {
        em.persist(user);
        return user;
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        if (user.getId() != null) {
            User existingUser = em.find(User.class, user.getId());
            if (existingUser != null) {
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmail(user.getEmail());
                existingUser.setAge(user.getAge());
                em.merge(existingUser);
            }
        }
    }


    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }
}
