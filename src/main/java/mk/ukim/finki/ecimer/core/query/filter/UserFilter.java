package mk.ukim.finki.ecimer.core.query.filter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;

public class UserFilter {
    private int pageNumber;
    @Min(1)
    private int pageSize;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public UserFilter() {

    }

    public UserFilter(int pageNumber, int pageSize, String email, String firstName, String lastName, String phoneNumber) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public Pageable toPageable() {
        return PageRequest.of(pageNumber, pageSize);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
