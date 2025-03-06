package com.workintech.Twitter.repository;

import com.workintech.Twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {


    //Kullanıcı adına göre kullanıcıyı bulmasını sağlar
    @Query("SELECT u FROM User u WHERE u.username = username")
    User findByUsername(String username);
}
