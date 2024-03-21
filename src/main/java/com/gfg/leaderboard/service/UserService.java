package com.gfg.leaderboard.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gfg.leaderboard.entity.Badge;
import com.gfg.leaderboard.entity.User;
import com.gfg.leaderboard.exception.AlreadyExistingEmailId;
import com.gfg.leaderboard.exception.InvalidArgument;
import com.gfg.leaderboard.exception.SomethingUnexpectedHappen;
import com.gfg.leaderboard.exception.UserNotFound;
import com.gfg.leaderboard.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        try {
            User existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null) {
                throw new AlreadyExistingEmailId("Email already exist");
            }
            if (user.getEmail() != null && !user.getEmail().contains("@")) {
                throw new InvalidArgument("Please provide a valid email");
            }
            return userRepository.save(user);
        }  catch (InvalidArgument e) {
            throw new InvalidArgument("Please provide a important field");
        }catch (AlreadyExistingEmailId e) {
            throw new AlreadyExistingEmailId("Email already exist");
        }catch (SomethingUnexpectedHappen e) {
            throw new SomethingUnexpectedHappen("Unexpected error occurred while saving user. Please try again.");
        }
       
    }

    /*
     * Determines the badge for a user based on their score.
     *
     * @param score The score of the user.
     * 
     * @return The badge assigned to the user based on their score.
     */
    public Badge setUserBadge(int score) {
        if (score >= 1 && score <= 30) {
            return Badge.CODE_NINJA;
        } else if (score > 30 && score <= 60) {
            return Badge.CODE_CHAMP;
        }else if(score > 60 && score <= 100) {
            return Badge.CODE_MASTER;
        }
        return null;
    }

    
    public User updateUser(int score, Long id) {
        try {
            User existingUser = getUserByIdHelper(id);

            if (score < 0 || score > 100) {
                throw new InvalidArgument("score should be in between 0 to 100");
            }
            existingUser.setScore(score);

            Badge badge= setUserBadge(score);
            Set<Badge> badges= existingUser.getBadge();
            if(badges.isEmpty()) {
                badges= new HashSet<>();             
            }
            if(badge != null){
                badges.add(badge);
                existingUser.setBadge(badges);
            }
            return userRepository.save(existingUser);
            
        } catch (SomethingUnexpectedHappen e) {
            throw new SomethingUnexpectedHappen("Unexpected error occurred while updating user. Please try again.");
        }  catch (InvalidArgument e) {
            throw new InvalidArgument("score should be in between 0 to 100");
        }catch (UserNotFound e) {
            throw new UserNotFound("User not found");
        }
    }

    
    public List<User> getUser(String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "score");
        try {
            return userRepository.findAll(sort);
        } catch (Exception e) {
            throw new SomethingUnexpectedHappen("Unexpected error occurred while fetching user. Please try again.");
        }
    }

    

    
    public void deleteUser(Long id) {
        try {
            getUserByIdHelper(id);
            userRepository.deleteById(id);
        }catch (UserNotFound e) {
            throw new UserNotFound("User not found");
        }catch (Exception e) {
            throw new SomethingUnexpectedHappen("Unexpected error occurred while deleting user. Please try again.");
        }
    }

   

    
    public User getUserById(Long id) {
        try {
            return getUserByIdHelper(id);
        } catch (UserNotFound e) {
            throw new UserNotFound("User not found");
        } catch (Exception e) {
            throw new SomethingUnexpectedHappen("Unexpected error occurred while fetching user. Please try again.");
        }
       
    }

    

    private User getUserByIdHelper(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFound("User not found");
        }
        return user.get();
    }

}
