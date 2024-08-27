package mk.ukim.finki.ecimer.web.requestbody;

import mk.ukim.finki.ecimer.common.annotations.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class User {
    @NotEmpty(message = "Email must not be empty!")
    @Email
    private String email;
    @NotEmpty(message = "Password must not be empty!")
    @ValidPassword
    private String password;
    @NotEmpty(message = "First name must not be empty!")
    private String firstName;
    @NotEmpty(message = "Last name must not be empty!")
    private String lastName;
    @NotEmpty(message = "Phone number must not be empty!")
    private String phoneNumber;

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firstName, String lastName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
