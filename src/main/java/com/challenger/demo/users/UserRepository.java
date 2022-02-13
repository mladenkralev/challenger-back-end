package com.challenger.demo.users;

import com.challenger.demo.users.models.UserDatabaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDatabaseModel, Integer> {
    public Optional<UserDatabaseModel> findUserByEmail(String email);
}
