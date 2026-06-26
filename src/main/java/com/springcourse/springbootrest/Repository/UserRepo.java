package com.springcourse.springbootrest.Repository;


import com.springcourse.springbootrest.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

    User findByUsername(String username);
}
