package com.challenger.demo.users;

import com.challenger.demo.users.models.UserDatabaseModel;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDatabaseModel addUser(UserDatabaseModel user) {
        return userRepository.save(user);
    }

    public void deleteUser(UserDatabaseModel user) {
        userRepository.delete(user);
    }

    public UserDatabaseModel findByEmail(UserDatabaseModel user) {
        Optional<UserDatabaseModel> userByEmail = userRepository.findUserByEmail(user.getEmail());
        return userByEmail.orElseGet(UserDatabaseModel::emptyEntity);
    }

    public UserDatabaseModel findByEmail(String email) {
        Optional<UserDatabaseModel> userByEmail = userRepository.findUserByEmail(email);
        return userByEmail.orElseGet(UserDatabaseModel::emptyEntity);
    }
}
