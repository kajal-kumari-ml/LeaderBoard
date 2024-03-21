package com.gfg.leaderboard.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gfg.leaderboard.entity.User;

@Repository
public interface UserRepository extends  JpaRepository<User, Long>{
    
    public User findByEmail(String email);
    List<User> findAllById(Long id, Sort sort);

}
