package org.example.authhashbcrypt.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.authhashbcrypt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserImpl implements UserRepository {

    private EntityManager entityManager;

    @Autowired
    public UserImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User authenticateUser(String username) {
        String jpql = "SELECT u FROM User u WHERE u.userName = :username";
        try {
            return entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Authentication failed (user not found)
        }
    }

    // New method to find a user by username
    public User findByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.userName = :username";
        try {
            return entityManager.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // No user found with the given username
        }
    }

    @Override
    public List<User> listUsers() {
        // JPQL query to select all
        String jpql = "SELECT u FROM User u";

        // Create the query
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);

        // Get the result list as a List<String> and return it
        return query.getResultList();
    }






}
