package id.veintechnology.apps.library.id.veintechnology.apps.api.auth.dto;


import id.veintechnology.apps.library.id.veintechnology.apps.dao.Member.Gender;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterPayload {

    @NotNull(message = "Username cannot be null.")
    @Size(min = 6, max = 20)
    private String username;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 6, max = 20)
    private String password;

    @NotNull(message = "Name cannot be null.")
    @Size(min = 6, max = 50)
    private String name;

    @NotNull(message = "Gender cannot be null.")
    private Gender gender;

    @NotNull(message = "Phone Number cannot be null.")
    @Size(min = 10, max = 15)
    private String phone;

    @NotNull(message = "Address cannot be null.")
    private String address;

    @NotNull(message = "Email cannot be null.")
    @Email
    private String email;

    @NotNull(message = "Company cannot be null.")
    private String company;

    @NotNull(message = "EmployeeNo cannot be null.")
    private String employeeNo;

    @NotNull(message = "workUnit cannot be null.")
    private String workUnit;

    public RegisterPayload() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }
}
