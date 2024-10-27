package org.example.authhashbcrypt.repository;

import org.example.authhashbcrypt.entity.User;

import java.util.List;

public interface UserRepository {

    User authenticateUser(String username);
    User findByUsername(String username);

    List<User> listUsers();


}
