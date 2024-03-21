package com.gfg.leaderboard.Service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.gfg.leaderboard.entity.Badge;
import com.gfg.leaderboard.entity.User;
import com.gfg.leaderboard.exception.AlreadyExistingEmailId;
import com.gfg.leaderboard.exception.InvalidArgument;
import com.gfg.leaderboard.exception.UserNotFound;
import com.gfg.leaderboard.repository.UserRepository;
import com.gfg.leaderboard.service.UserService;


@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

     @Test
    public void testSaveUser_Success() {
        User user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).findByEmail("test@gmail.com");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testSaveUser_DuplicateEmail() {
        User user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

        assertThrows(AlreadyExistingEmailId.class, () -> userService.saveUser(user));

        verify(userRepository, times(1)).findByEmail("test@gmail.com");
        verify(userRepository, never()).save(user);
    }

  
    @Test
    public void testSaveUser_InvalidEmail() {
        User user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test");

        when(userRepository.findByEmail("test")).thenReturn(null);

        assertThrows(InvalidArgument.class, () -> userService.saveUser(user));

        verify(userRepository, times(1)).findByEmail("test");
        verify(userRepository, never()).save(user);
    }

      @Test
    public void testUpdateUser_ValidScore() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("test@gmail.com");
        existingUser.setName("test");

        int updatedScore = 75;

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser(updatedScore, 1L);

        assertEquals(updatedScore, result.getScore());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUser_InvalidScore() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setScore(50);

        int updatedScore = 150; // Invalid score

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        assertThrows(InvalidArgument.class, () -> userService.updateUser(updatedScore, 1L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(existingUser);
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> userService.updateUser(50, 1L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any());
    }

        @Test
    public void testGetUser_Success() {
        Set<Badge> badges = new HashSet<>();
        badges.add(Badge.CODE_NINJA);
        badges.add(Badge.CODE_CHAMP);
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "User1", "user1@example.com", 50,badges));
        users.add(new User(2L, "User2", "user2@example.com", 75,badges));

        when(userRepository.findAll(Sort.by(Sort.Direction.ASC, "score"))).thenReturn(users);

        List<User> result = userService.getUser("ASC");

        assertEquals(users.size(), result.size());
        assertEquals(users.get(0), result.get(0));
        assertEquals(users.get(1), result.get(1));
        verify(userRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "score"));
    }

     @Test
    public void testDeleteUser_Success() {
        User user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.deleteUser(1L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }



    
}
