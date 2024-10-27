package org.example.authhashbcrypt.repository;

public interface PasswordRepository {

    String getSaltFromUser(String username);
    boolean verPasswd(String password);

    String getPasswordHash(String username);
}
