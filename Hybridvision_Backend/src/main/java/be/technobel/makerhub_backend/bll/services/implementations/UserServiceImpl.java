package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.bll.exceptions.AccountBlockedException;
import be.technobel.makerhub_backend.bll.exceptions.AccountWasDeactivatedException;
import be.technobel.makerhub_backend.bll.exceptions.DuplicateUserException;
import be.technobel.makerhub_backend.bll.exceptions.NotFoundException;
import be.technobel.makerhub_backend.bll.mailing.EmailSenderService;
import be.technobel.makerhub_backend.bll.services.UserService;
import be.technobel.makerhub_backend.dal.models.entities.NewsletterEmailEntity;
import be.technobel.makerhub_backend.dal.models.entities.ShoppingCartEntity;
import be.technobel.makerhub_backend.dal.models.entities.UserEntity;
import be.technobel.makerhub_backend.dal.models.enums.UserRole;
import be.technobel.makerhub_backend.dal.repositories.NewsletterEmailRepository;
import be.technobel.makerhub_backend.dal.repositories.ShoppingCartRepository;
import be.technobel.makerhub_backend.dal.repositories.UserRepository;
import be.technobel.makerhub_backend.pl.config.security.JWTProvider;
import be.technobel.makerhub_backend.pl.models.dtos.AuthDto;
import be.technobel.makerhub_backend.pl.models.forms.LoginForm;
import be.technobel.makerhub_backend.pl.models.forms.EmailForm;
import be.technobel.makerhub_backend.pl.models.forms.UpdateUserForm;
import be.technobel.makerhub_backend.pl.models.forms.UserForm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for user-related operations such as login, sign-up, password management, and user modification.
 */
@Service
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final NewsletterEmailRepository newsletterEmailRepository;
    private final EmailSenderService emailSenderService;
    private final ShoppingCartRepository shoppingCartRepository;

    public UserServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           JWTProvider jwtProvider, PasswordEncoder passwordEncoder, NewsletterEmailRepository newsletterEmailRepository, EmailSenderService emailSenderService,
                           ShoppingCartRepository shoppingCartRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.newsletterEmailRepository = newsletterEmailRepository;
        this.emailSenderService = emailSenderService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    /**
     * Authenticates a user and generates a JWT token if successful. Throws exceptions if the account is inactive or blocked.
     */
    @Override
    public AuthDto login(LoginForm form) {
        // Authenticates the user with username and password.
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(),form.getPassword()));

        // Retrieves the user entity based on username.
        Optional<UserEntity> userOptional = userRepository.findByUsername(form.getUsername());
        if (!userOptional.isPresent()) {
            throw new NotFoundException("User not found.");
        }
        UserEntity user = userOptional.get();

        // Generates JWT token for the authenticated user.
        String token = jwtProvider.generateToken(user.getUsername(), user.getRole());

        // Checks if the user account is active.
        if(!user.isActive()){
            throw new AccountWasDeactivatedException("Account is inactive. Please contact support to get your account back.");
        }

        // Checks if the user account is blocked.
        if(user.isBlocked()){
            throw new AccountBlockedException("Your account was blocked. Please contact support for more informations.");
        }

        // Constructs and returns an AuthDto with user details and JWT token.
        return AuthDto.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    /**
     * Registers a new user with the provided details from UserForm.
     * Ensures email and username uniqueness and automatically subscribes the user to the newsletter.
     */
    @Override
    public void signUp(UserForm form) {
        // Validates the form is not null.
        if(form == null){
            throw new IllegalArgumentException("Form can't be null.");
        }

        // Checks for email uniqueness.
        if(userRepository.existsByEmail(form.getEmail())){
            throw new DuplicateUserException("Email already exists.");
        }

        // Checks for username uniqueness.
        if(userRepository.existsByUsername(form.getUsername())){
            throw new DuplicateUserException("Username is already taken.");
        }

        // Creates and saves a new user.
        UserEntity client = new UserEntity();
        client.setUsername(form.getUsername());
        client.setFirstName(form.getFirstName());
        client.setLastName(form.getLastName());
        client.setEmail(form.getEmail());
        client.setPassword(passwordEncoder.encode(form.getPassword()));
        client.setActive(true);
        client.setBlocked(false);
        userRepository.save(client);

        ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
        shoppingCart.setUser(client);
        shoppingCartRepository.save(shoppingCart);

        client.setShoppingCart(shoppingCart);
        userRepository.save(client);

        // Automatically subscribes the new user to the newsletter.
        NewsletterEmailEntity newsletterEmailEntity = new NewsletterEmailEntity();
        newsletterEmailEntity.setEmail(form.getEmail());
        newsletterEmailRepository.save(newsletterEmailEntity);
    }

    /**
     * Handles password reset requests.
     * Generates a new password, updates the user's record, and sends the new password via email.
     * Throws NotFoundException if the email is not found.
     */
    @Override
    public void forgotPassword(EmailForm form) {
        // Validates the form is not null.
        if(form == null){
            throw new IllegalArgumentException("Form can't be null.");
        }

        // Checks for email uniqueness.
        String email = form.getEmail();
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            UserEntity user = userOptional.get();
            String username = user.getUsername();
            String password = generateRandomPassword(); // Generates a new random password.
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);

            emailSenderService.forgotPasswordEmail(email, username, password); // Sends the new password via email.
        } else{
            throw new NotFoundException("Email not found.");
        }
    }

    /**
     * Updates user account details based on provided UpdateUserForm.
     * Returns updated user entity or throws NotFoundException if user is not found.
     */
    @Override
    public Optional<UserEntity> editAccount(UpdateUserForm form) {
        // Checks if the form is null
        if (form == null) {
            throw new IllegalArgumentException("Form can't be null.");
        }

        // Attempts to find the user by the username provided in the form.
        String username = form.getUsername();
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        // If the user is found, updates their details.
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setUsername(form.getUsername());
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            user.setEmail(form.getEmail());
            userRepository.save(user);
            return Optional.of(user);
        } else {
            throw new NotFoundException("User not found.");
        }
    }

    /**
     * Retrieves a user by their username.
     * Returns an Optional containing the UserEntity or empty if not found.
     */
    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Deactivates or reactivates a user account based on the current status.
     * Throws NotFoundException if the user is not found.
     */
    @Override
    public void deactivateAccount(String username) {
        // Attempts to find the user by username.
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        // Checks if user is found and toggles their active status.
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if(user.isActive()){
                user.setActive(false);
            } else {
                user.setActive(true);
            }
            userRepository.save(user);
        } else {
            throw new NotFoundException("User not found.");
        }
    }

    /**
     * Blocks or unblocks a user account based on the current status.
     * Throws NotFoundException if the user is not found.
     */
    @Override
    public void blockAccount(String username) {
        // Attempts to find the user by username.
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        // Checks if user is found and toggles their active status.
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if(!user.isBlocked()){
                user.setBlocked(true);
            } else{
                user.setBlocked(false);
            }
            userRepository.save(user);
        } else {
            throw new NotFoundException("User not found.");
        }
    }

    /**
     * Retrieves all users with the CLIENT role.
     */
    @Override
    public List<UserEntity> getAllClients() {
        return userRepository.findByRole(UserRole.CLIENT);
    }

    /**
     * Generates a random alphanumeric string to use as a new password.
     */
    private String generateRandomPassword(){
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
