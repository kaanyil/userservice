package dev.proqa.userservice.service;

import dev.proqa.userservice.domain.AppUser;
import dev.proqa.userservice.domain.Role;
import dev.proqa.userservice.repository.AppUserRepository;
import dev.proqa.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final AppUserRepository appUserRepo;
    private final RoleRepository  roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepo.findByUsername(username);
        if(appUser == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        appUser.getRoles().forEach(role ->
                {authorities.add(new SimpleGrantedAuthority(role.getName()));
                });

        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(),
                appUser.getPassword(),
                authorities);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving new user {} to the database", appUser.getName());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepo.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        log.info("Adding new role {} to the user {}", roleName, userName);
        AppUser appUser = appUserRepo.findByUsername(userName);
        Role role = roleRepo.findByName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser getUserByName(String userName) {
        log.info("Fetching user {}", userName);
        return appUserRepo.findByUsername(userName);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return appUserRepo.findAll();
    }

}
