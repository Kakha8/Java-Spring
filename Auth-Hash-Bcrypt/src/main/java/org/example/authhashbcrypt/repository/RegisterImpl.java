package org.example.authhashbcrypt.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.authhashbcrypt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterImpl implements RegisterRepository{

    private EntityManager entityManager;

    @Autowired
    public RegisterImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public boolean passwordsMatch(String password, String retype) {
        return password.equals(retype);
    }

    @Override
    @Transactional
    public void registerUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }
}
