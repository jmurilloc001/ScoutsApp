package com.jmurilloc.pfc.scouts.entities.dto;

import com.jmurilloc.pfc.scouts.entities.Role;

import java.util.List;
import java.util.Set;

public class UserDto {
    private String username;
    private String name;
    private String lastname;
    private List<Role> roles;
    private boolean enabled;

    public UserDto(String username, String name, String lastname, List<Role> roles, boolean enabled) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.roles = roles;
        this.enabled = enabled;
    }

    public UserDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
