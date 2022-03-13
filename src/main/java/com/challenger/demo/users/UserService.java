package com.challenger.demo.users;

import com.challenger.demo.users.models.UserDatabaseModel;
import com.challenger.demo.users.models.UserRequest;
import com.challenger.demo.users.transformers.UserTransformer;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransformer userTransformer;

    public UserDatabaseModel addUser(UserRequest userRequest) {
        UserDatabaseModel userDatabaseModel = userTransformer.toDatabaseEntity(userRequest);
        return userRepository.save(userDatabaseModel);
    }

    public void deleteUser(UserDatabaseModel user) {
        userRepository.delete(user);
    }

    public UserDatabaseModel findByEmail(UserRequest userRequest) {
        UserDatabaseModel userDatabaseModel = userTransformer.toDatabaseEntity(userRequest);
        Optional<UserDatabaseModel> userByEmail = userRepository.findUserByEmail(userDatabaseModel.getEmail());
        return userByEmail.orElseGet(UserDatabaseModel::emptyEntity);
    }

    public UserDatabaseModel findByEmail(String email) {
        Optional<UserDatabaseModel> userByEmail = userRepository.findUserByEmail(email);
        return userByEmail.orElseGet(UserDatabaseModel::emptyEntity);
    }
}
