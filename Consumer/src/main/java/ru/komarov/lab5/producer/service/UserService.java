package ru.komarov.lab5.producer.service;

import ru.komarov.lab5.producer.dto.UserDto;
import ru.komarov.lab5.producer.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.komarov.lab5.producer.repository.OwnerRepository;
import ru.komarov.lab5.producer.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User customUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + customUser.getRole())
        );

        return org.springframework.security.core.userdetails.User.builder()
                .username(customUser.getUsername())
                .password(customUser.getPassword())
                .authorities(authorities)
                .build();
    }

    public User addUser(UserDto userDto) {
        if (userRepository.findUserByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        var optionalOwner = ownerRepository.findById(userDto.getOwnerId());
        if (optionalOwner.isPresent()) {
            var user = User.builder()
                    .id(userDto.getId())
                    .username(userDto.getUsername())
                    .password(encodedPassword)
                    .role(userDto.getRole())
                    .owner(optionalOwner.get())
                    .build();
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("Owner not found");
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
