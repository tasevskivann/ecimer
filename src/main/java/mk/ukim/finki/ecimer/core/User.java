package mk.ukim.finki.ecimer.core;


import lombok.Data;
import lombok.EqualsAndHashCode;
import mk.ukim.finki.ecimer.common.annotations.ValidPassword;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ec_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends mk.ukim.finki.ecimer.domain.Entity implements UserDetails {

    @Email
    private String email;
    @ValidPassword
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userUuid", referencedColumnName = "uuid")
    private List<Post> posts;
    @CollectionTable(name = "ec_user_authorities")
    @ElementCollection(fetch = FetchType.EAGER)
    Set<Role> authorities = new HashSet<>();

    public User() {

    }

    public User(String email, String password, String firstName, String lastName, String phoneNumber, PasswordEncoder passwordEncoder) {
        super();
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public void update(String email, String password, String firstName, String lastName, String phoneNumber) {
        if (email != null && !email.isEmpty()) {
            setEmail(email);
        }
        if (password != null && !password.isEmpty()) {
            setPassword(password);
        }
        if (firstName != null && !firstName.isEmpty()) {
            this.firstName = firstName;
        }
        if (lastName != null && !lastName.isEmpty()) {
            this.lastName = lastName;
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(@ValidPassword String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void appendRole(Role role) {
        this.authorities.add(role);
    }
}
