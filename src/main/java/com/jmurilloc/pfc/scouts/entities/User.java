package com.jmurilloc.pfc.scouts.entities;

import com.jmurilloc.pfc.scouts.validation.ExistsByUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Transient
    private boolean admin;

    @PrePersist
    public void prePersist(){
        enabled = true;
    }

    public User() {
        roles = new ArrayList<>();
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
    
    
    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", enabled=" + enabled + ", affiliate=" + affiliate + ", roles=" + roles + ", admin="
                + admin + '}';
    }
}
