package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.dal.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the UserDetailsService interface to load user-specific data.
 * It is used by the Spring Security framework to handle authentication processes.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user by username for authentication.
     * @param username the username identifying the user.
     * @return UserDetails user details for the specified username.
     * @throws UsernameNotFoundException if the username is not found in the repository.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found."));
    }
}
