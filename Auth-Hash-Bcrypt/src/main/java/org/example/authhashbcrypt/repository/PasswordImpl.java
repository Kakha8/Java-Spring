package org.example.authhashbcrypt.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordImpl implements PasswordRepository {

    private EntityManager entityManager;

    public PasswordImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String getSaltFromUser(String username) {
        String jpql = "SELECT u.salt FROM User u WHERE u.userName = :username";
        try {
            return entityManager.createQuery(jpql, String.class)
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null; // Authentication failed (user not found)
        }
    }

    @Override
    public boolean verPasswd(String password) {

        return false;
    }

    @Override
    public String getPasswordHash(String username) {

        return null;
    }


}
