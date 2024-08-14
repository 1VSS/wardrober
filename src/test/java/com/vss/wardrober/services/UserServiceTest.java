package com.vss.wardrober.services;

import com.vss.wardrober.DTOs.UserDTO;
import com.vss.wardrober.models.UserModel;
import com.vss.wardrober.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserModel> userModelArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    @Nested
    class saveUser {


        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUser() {

            // Arrange
            var input = new UserModel(
                    1L,
                    "username",
                    "email",
                    null,
                    null

            );
            doReturn(input).when(userRepository).save(userModelArgumentCaptor.capture());

            // Act
            var output = userService.save(input);
            // Assert

            assertNotNull(output);

            var userCaptured = userModelArgumentCaptor.getValue();
            assertEquals(input.getUsername(), userCaptured.getUsername());
        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("should get user by id with success when optional is present")
        void shouldGetUserByIdWhenOptionalIsPresent() {

            // Arrange
            var input = new UserModel(
                    1L,
                    "username",
                    "email",
                    null,
                    null

            );
            doReturn(Optional.of(input)).when(userRepository).findById(longArgumentCaptor.capture());

            // Act
            var output = userService.findById(input.getId());

            // Assert]
            assertTrue(output.isPresent());
            assertEquals(input.getId(), longArgumentCaptor.getValue());

        }

        @Test
        @DisplayName("should get user by id with success when optional is empty")
        void shouldGetUserByIdWhenOptionalIsEmpty() {

            // Arrange
            Long id = 1L;
            doReturn(Optional.empty()).when(userRepository).findById(longArgumentCaptor.capture());

            // Act
            var output = userService.findById(id);

            // Assert]
            assertTrue(output.isEmpty());
            assertEquals(id, longArgumentCaptor.getValue());

        }
    }

    @Nested
    class getUsers {

        @Test
        @DisplayName("should return all users with success")
        void ShouldReturnAllUsersWithSuccess() {

            // Arrange
            var input = new UserModel(
                    1L,
                    "username",
                    "email",
                    null,
                    null

            );
            var userList = List.of(input).size();
            doReturn(List.of(input)).when(userRepository).findAll();

            // Act
            var output =userService.getAll();

            // Assert
            assertNotNull(output);
            assertEquals(userList, output.size());
        }
    }

    @Nested
    class deleteUserById {

        @Test
        @DisplayName("should delete user with success")
        void shouldDeleteUserWithSuccess() {

            // Arrange
            var input = new UserModel(
                    1L,
                    "username",
                    "email",
                    null,
                    null

            );
            doReturn(Optional.of(input)).when(userRepository).findById(longArgumentCaptor.capture());

            // Act
            var output = userService.findById(input.getId());

            // Assert]
            assertTrue(output.isPresent());
            assertEquals(input.getId(), longArgumentCaptor.getValue());




        }
    }

}