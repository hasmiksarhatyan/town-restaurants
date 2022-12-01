package am.itspace.townrestaurantscommon.security;

import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userByEmail = userRepository.findByEmail(username);
        if (userByEmail.isEmpty()) {
            throw new UsernameNotFoundException("Username does not exist");
        }
        return new CurrentUser(userByEmail.get());
    }
}
