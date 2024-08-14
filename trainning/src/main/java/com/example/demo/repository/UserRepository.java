package com.example.demo.repository;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    @Override
    boolean existsById(Long id);

    boolean existsUserByUsername(String username);

    Optional<Users> findByUsername(String username);
}
