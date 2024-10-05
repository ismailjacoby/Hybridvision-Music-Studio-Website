package be.technobel.makerhub_backend.bll;

import be.technobel.makerhub_backend.bll.exceptions.AccountBlockedException;
import be.technobel.makerhub_backend.bll.exceptions.AccountWasDeactivatedException;
import be.technobel.makerhub_backend.bll.exceptions.DuplicateUserException;
import be.technobel.makerhub_backend.bll.exceptions.NotFoundException;
import be.technobel.makerhub_backend.bll.mailing.EmailSenderService;
import be.technobel.makerhub_backend.bll.services.UserService;
import be.technobel.makerhub_backend.bll.services.implementations.UserServiceImpl;
import be.technobel.makerhub_backend.dal.models.entities.NewsletterEmailEntity;
import be.technobel.makerhub_backend.dal.models.entities.ShoppingCartEntity;
import be.technobel.makerhub_backend.dal.models.entities.UserEntity;
import be.technobel.makerhub_backend.dal.models.enums.UserRole;
import be.technobel.makerhub_backend.dal.repositories.NewsletterEmailRepository;
import be.technobel.makerhub_backend.dal.repositories.ShoppingCartRepository;
import be.technobel.makerhub_backend.dal.repositories.UserRepository;
import be.technobel.makerhub_backend.pl.config.security.JWTProvider;
import be.technobel.makerhub_backend.pl.models.dtos.AuthDto;
import be.technobel.makerhub_backend.pl.models.forms.EmailForm;
import be.technobel.makerhub_backend.pl.models.forms.LoginForm;
import be.technobel.makerhub_backend.pl.models.forms.UpdateUserForm;
import be.technobel.makerhub_backend.pl.models.forms.UserForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private JWTProvider jwtProvider;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private NewsletterEmailRepository newsletterEmailRepository;
    @Mock
    private EmailSenderService emailSenderService;
    @InjectMocks
    private UserServiceImpl userService;
    private UserEntity testUser;
    private final String testToken = "testToken";

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setUsername("testUser");
        testUser.setPassword("Test1234=");
        testUser.setRole(UserRole.CLIENT);
        testUser.setActive(true);
        testUser.setBlocked(false);
    }

    // ========== Login Tests  ==========

    //Login - Successful Login
    @Test
    void successfulLogin() {
        LoginForm loginForm = new LoginForm(testUser.getUsername(), testUser.getPassword());
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));
        when(jwtProvider.generateToken(testUser.getUsername(),UserRole.CLIENT)).thenReturn(testToken);

        AuthDto result = userService.login(loginForm);

        assertNotNull(result);
        assertEquals(testToken, result.getToken());
        assertEquals("testUser", result.getUsername());
        assertEquals(UserRole.CLIENT, result.getRole());
    }

    //Login - User not found
    @Test
    void userNotFoundDuringLogin() {
        // Setup
        String username = "nonExistingUser";
        LoginForm loginForm = new LoginForm(username, "somePassword");

        // Mock the behavior to simulate user not found
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.login(loginForm),
                "Expected login to throw NotFoundException for non-existing user, but it didn't.");

        // Verify that findByUsername was called with the expected username
        verify(userRepository).findByUsername(username);
    }

    //Login - Test Account Inactif
    @Test
    void userAccountInactive() {
        testUser.setActive(false);
        LoginForm loginForm = new LoginForm("testUser", "testPass");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));

        Exception exception = assertThrows(AccountWasDeactivatedException.class, () -> {
            userService.login(loginForm);
        });

        String expectedMessage = "Account is inactive. Please contact support to get your account back.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //Login - Test Account Blocked
    @Test
    void userAccountBlocked() {
        testUser.setBlocked(true);
        LoginForm loginForm = new LoginForm("testUser", "testPass");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));

        Exception exception = assertThrows(AccountBlockedException.class, () -> {
            userService.login(loginForm);
        });

        String expectedMessage = "Your account was blocked. Please contact support for more informations.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    // ========== SignUp Tests  ==========

    // SignUp - Successful
    @Test
    void successfulSignUp() {
        UserForm userForm = new UserForm("newUser", "New", "User", "newuser@example.com", "password");
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(shoppingCartRepository.save(any(ShoppingCartEntity.class))).thenReturn(shoppingCartEntity);

        assertDoesNotThrow(() -> userService.signUp(userForm));
        verify(userRepository, times(2)).save(any(UserEntity.class));
        verify(shoppingCartRepository).save(any(ShoppingCartEntity.class));
        verify(newsletterEmailRepository).save(any(NewsletterEmailEntity.class));
    }

    // SignUp - with null UserForm
    @Test
    void signUpWithNullFormThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userService.signUp(null),
                "Expected IllegalArgumentException to be thrown when UserForm is null");
    }

    // SignUp - with existing email
    @Test
    void signUpWithExistingEmailThrowsDuplicateUserException() {
        UserForm userForm = new UserForm("newUser", "New", "User", "newuser@example.com", "password");
        when(userRepository.existsByEmail(userForm.getEmail())).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> userService.signUp(userForm),
                "Expected DuplicateUserException to be thrown when email already exists");
    }

    // SignUp - with existing username
    @Test
    void signUpWithExistingUsernameThrowsDuplicateUserException() {
        UserForm userForm = new UserForm("newUser", "New", "User", "newuser@example.com", "password");
        when(userRepository.existsByUsername(userForm.getUsername())).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> userService.signUp(userForm),
                "Expected DuplicateUserException to be thrown when username already exists");
    }

    // ========== Password Reset Tests  ==========

    @Captor
    private ArgumentCaptor<UserEntity> userCaptor;

    // Forgot password - successful password reset
    @Test
    void successfulPasswordReset() {
        // Setup
        String existingEmail = "user@example.com";
        EmailForm form = new EmailForm(existingEmail);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setEmail(existingEmail);

        when(userRepository.findByEmail(existingEmail)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");

        // Act
        assertDoesNotThrow(() -> userService.forgotPassword(form));

        // Assert
        verify(userRepository).save(userCaptor.capture());
        UserEntity savedUser = userCaptor.getValue();
        assertNotNull(savedUser.getPassword(), "The password should be updated.");
        verify(emailSenderService).forgotPasswordEmail(eq(existingEmail), eq("testUser"), anyString());
    }

    // Forgot password - EmailForm null
    @Test
    void forgotPasswordWithNullFormThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userService.forgotPassword(null),
                "Expected IllegalArgumentException to be thrown when EmailForm is null");
    }

    // Forgot password - email not found
    @Test
    void forgotPasswordWithEmailNotFoundThrowsNotFoundException() {
        // Setup
        String nonExistingEmail = "nonexistent@example.com";
        EmailForm form = new EmailForm(nonExistingEmail);

        when(userRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.forgotPassword(form),
                "Expected NotFoundException to be thrown when email is not found in the database");
    }

    // ========== Edit Account Tests  ==========

    // Edit Account - successful account update
    @Test
    void successfulAccountUpdate() {
        UpdateUserForm form = new UpdateUserForm("existingUsername", "NewFirstName", "NewLastName", "newemail@example.com");
        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("existingUsername");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingUser));

        Optional<UserEntity> updatedUser = userService.editAccount(form);

        assertTrue(updatedUser.isPresent(), "Expected a non-empty Optional for a successful update");
        assertEquals(form.getUsername(), updatedUser.get().getUsername());
        assertEquals(form.getFirstName(), updatedUser.get().getFirstName());
        assertEquals(form.getLastName(), updatedUser.get().getLastName());
        assertEquals(form.getEmail(), updatedUser.get().getEmail());
        verify(userRepository).save(any(UserEntity.class));
    }

    // Edit Account - null form submission
    @Test
    void editAccountWithNullFormThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userService.editAccount(null),
                "Expected IllegalArgumentException to be thrown when form is null");
    }

    // Edit Account - User non-existent
    @Test
    void editAccountForNonExistentUserThrowsNotFoundException() {
        UpdateUserForm form = new UpdateUserForm("nonExistentUsername", "FirstName", "LastName", "email@example.com");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.editAccount(form),
                "Expected NotFoundException to be thrown for a non-existent user");
    }

    // ========== Get by username Tests  ==========

    // Test for retrieving a user when the user is found
    @Test
    void getUserByUsernameWhenUserFound() {
        String username = "existingUser";
        UserEntity foundUser = new UserEntity();
        foundUser.setUsername(username);
        foundUser.setEmail("user@example.com");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(foundUser));

        Optional<UserEntity> result = userService.getUserByUsername(username);

        assertTrue(result.isPresent(), "Expected non-empty Optional for an existing user");
        assertEquals(username, result.get().getUsername(), "Expected usernames to match");
    }

    // Test for retrieving a user when the user is not found
    @Test
    void getUserByUsernameWhenUserNotFound() {
        String nonExistentUsername = "nonExistentUser";

        when(userRepository.findByUsername(nonExistentUsername)).thenReturn(Optional.empty());

        Optional<UserEntity> result = userService.getUserByUsername(nonExistentUsername);

        assertFalse(result.isPresent(), "Expected an empty Optional for a non-existent user");
    }

    // ========== Deactivate Account Tests  ==========

    // Deactivate Account - successfully deactivate an active user account
    @Test
    void deactivateActiveUserAccount() {
        String username = "activeUser";
        UserEntity activeUser = new UserEntity();
        activeUser.setUsername(username);
        activeUser.setActive(true);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(activeUser));

        userService.deactivateAccount(username);

        verify(userRepository).save(userCaptor.capture());
        UserEntity savedUser = userCaptor.getValue();
        assertFalse(savedUser.isActive(), "Expected the user's account to be deactivated.");
    }

    // Deactivate Account - successfully reactivate an inactive user account
    @Test
    void reactivateInactiveUserAccount() {
        String username = "inactiveUser";
        UserEntity inactiveUser = new UserEntity();
        inactiveUser.setUsername(username);
        inactiveUser.setActive(false);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(inactiveUser));

        userService.deactivateAccount(username);

        verify(userRepository).save(userCaptor.capture());
        UserEntity savedUser = userCaptor.getValue();
        assertTrue(savedUser.isActive(), "Expected the user's account to be reactivated.");
    }

    // Deactivate Account - User not found
    @Test
    void deactivateAccountUserNotFound() {
        String nonExistentUsername = "nonExistentUser";

        when(userRepository.findByUsername(nonExistentUsername)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deactivateAccount(nonExistentUsername),
                "Expected NotFoundException to be thrown for a non-existent user");
    }

    // ========== Block Account Tests  ==========

    // Block Account - successfully blocks an unblocked user account
    @Test
    void blockUnblockedUserAccount() {
        String username = "unblockedUser";
        UserEntity unblockedUser = new UserEntity();
        unblockedUser.setUsername(username);
        unblockedUser.setBlocked(false);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(unblockedUser));

        userService.blockAccount(username);

        verify(userRepository).save(userCaptor.capture());
        UserEntity savedUser = userCaptor.getValue();
        assertTrue(savedUser.isBlocked(), "Expected the user's account to be blocked.");
    }

    // Block Account - Successfully unblocks a blocked user account
    @Test
    void unblockBlockedUserAccount() {
        String username = "blockedUser";
        UserEntity blockedUser = new UserEntity();
        blockedUser.setUsername(username);
        blockedUser.setBlocked(true);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(blockedUser));

        userService.blockAccount(username);

        verify(userRepository).save(userCaptor.capture());
        UserEntity savedUser = userCaptor.getValue();
        assertFalse(savedUser.isBlocked(), "Expected the user's account to be unblocked.");
    }

    // Block Account - User not found
    @Test
    void blockAccountUserNotFound() {
        String nonExistentUsername = "nonExistentUser";

        when(userRepository.findByUsername(nonExistentUsername)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.blockAccount(nonExistentUsername),
                "Expected NotFoundException to be thrown for a non-existent user");
    }

    // ========== Get All Users ==========

    // Get All Users - retrieves all clients when multiple clients exist
    @Test
    void getAllClientsWithMultipleClients() {
        List<UserEntity> clients = List.of(
                createUserEntity("client1", UserRole.CLIENT),
                createUserEntity("client2", UserRole.CLIENT)
        );

        when(userRepository.findByRole(UserRole.CLIENT)).thenReturn(clients);

        List<UserEntity> result = userService.getAllClients();

        assertNotNull(result, "Expected non-null list of clients");
        assertEquals(2, result.size(), "Expected list size to match number of clients");
        assertTrue(result.stream().allMatch(user -> user.getRole() == UserRole.CLIENT), "Expected all users to have CLIENT role");
    }

    // Get All Users - retrieves all clients when no clients exist
    @Test
    void getAllClientsWithNoClients() {
        when(userRepository.findByRole(UserRole.CLIENT)).thenReturn(Collections.emptyList());

        List<UserEntity> result = userService.getAllClients();

        assertNotNull(result, "Expected non-null list of clients");
        assertTrue(result.isEmpty(), "Expected an empty list when no clients exist");
    }

    // ========== Helper Methods ==========

    // Helper method to create UserEntity instances for testing
    private UserEntity createUserEntity(String username, UserRole role) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setRole(role);
        return user;
    }
}
