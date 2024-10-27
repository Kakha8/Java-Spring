package org.example.authhashbcrypt.repository;

import org.example.authhashbcrypt.entity.User;

public interface RegisterRepository {
    boolean passwordsMatch(String password, String retype);
    void registerUser(User user);


}
