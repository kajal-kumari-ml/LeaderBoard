package com.gfg.leaderboard.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gfg.leaderboard.entity.User;

@Repository
public interface UserRepository extends  MongoRepository<User, Long>{
    
    public User findByEmail(String email);
    List<User> findAllById(Long id, Sort sort);

}
