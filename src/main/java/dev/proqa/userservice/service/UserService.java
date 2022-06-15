package dev.proqa.userservice.service;

import dev.proqa.userservice.domain.AppUser;
import dev.proqa.userservice.domain.Role;

import java.util.List;

public interface UserService {

    AppUser saveUser(AppUser appUser);

    Role saveRole(Role role);

    void addRoleToUser(String userName, String roleName);

    AppUser getUserByName(String userName);

    List<AppUser> getUsers();

}
