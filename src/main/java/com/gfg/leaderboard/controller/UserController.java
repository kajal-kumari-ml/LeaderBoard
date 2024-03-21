package com.gfg.leaderboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gfg.leaderboard.entity.User;
import com.gfg.leaderboard.exception.AlreadyExistingEmailId;
import com.gfg.leaderboard.exception.InvalidArgument;
import com.gfg.leaderboard.exception.SomethingUnexpectedHappen;
import com.gfg.leaderboard.exception.UserNotFound;
import com.gfg.leaderboard.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserService userService;
    
     @PostMapping("/user")
    public ResponseEntity<?> createUser(@Validated @RequestBody User user) {
        if (user.getId()==null||user.getName() == null || user.getEmail() == null){
            return new ResponseEntity<>("Please Provide Important Information", HttpStatus.BAD_REQUEST);
        }
       
        try {
            User savedUser = userService.saveUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } catch (InvalidArgument e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (AlreadyExistingEmailId e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //update the user
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@RequestParam int score, @PathVariable Long id) {
        try {
            User savedUser = userService.updateUser(score, id);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } catch (UserNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (InvalidArgument e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(SomethingUnexpectedHappen e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUser(@RequestParam(defaultValue = "ASC") String sortOrder) {
        try {
            List<User> user = userService.getUser(sortOrder.toLowerCase());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(SomethingUnexpectedHappen e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(SomethingUnexpectedHappen e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
        } catch (UserNotFound e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(SomethingUnexpectedHappen e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
}
