package com.jmurilloc.pfc.scouts.entities;

import com.jmurilloc.pfc.scouts.validation.ExistsByUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ExistsByUsername //Creada por mi
    @NotBlank
    @Size(min = 4,max = 12)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(min = 4)
    @Column(unique = true)
    private String password;

    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "id_affiliate")
    private Affiliate affiliate;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})}
    )
    private Set<Role> roles;

    @Transient
    private boolean admin;

    @PrePersist
    public void prePersist(){
        enabled = true;
    }

    public User() {
        roles = new HashSet<>();
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public void addRole(Role role){
        this.roles.add(role);
    }
    public void deleteRole(Role role){
        this.roles.remove(role);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Affiliate getAffiliate() {
        return affiliate;
    }
    public void deleteAffiliate(Affiliate affiliate){
        affiliate.setUser(null);
        this.affiliate = null;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
    }
}
