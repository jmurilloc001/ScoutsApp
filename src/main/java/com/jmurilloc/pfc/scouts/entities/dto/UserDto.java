package com.jmurilloc.pfc.scouts.entities.dto;


import java.util.List;

public class UserDto {
    private Long id;
    private Long affiliateId;
    private String username;
    private String name;
    private String lastname;
    private List<RoleDto> roles;
    private boolean enabled;

    public UserDto(Long id,String username, String name, String lastname, List<RoleDto> roles, boolean enabled) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.roles = roles;
        this.enabled = enabled;
    }

    public UserDto() {
    }

    public UserDto(String username) {
        this.username = username;
    }

    public Long getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(Long affiliateId) {
        this.affiliateId = affiliateId;
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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
