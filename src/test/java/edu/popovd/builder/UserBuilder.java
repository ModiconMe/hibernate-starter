package edu.popovd.builder;

import edu.popovd.GenericBuilder;
import edu.popovd.TestCommons;
import edu.popovd.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserBuilder implements GenericBuilder<User> {

    private Long id;
    private String username = "name-" + TestCommons.getUniqString();
    private PersonalInfo personalInfo = new PersonalInfo("firstname-" + TestCommons.getUniqString(), "lastname-" + TestCommons.getUniqString(), new Birthday(LocalDate.now()));
    private Role role = Role.USER;
    private String info;
    private String password = "password-" + TestCommons.getUniqString();
    private Company company;
    private Profile profile;
    private List<UserChat> userChats = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public UserBuilder setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public UserBuilder setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public Company getCompany() {
        return company;
    }

    public UserBuilder setCompany(Company company) {
        this.company = company;
        return this;
    }

    public Profile getProfile() {
        return profile;
    }

    public UserBuilder setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public List<UserChat> getUserChats() {
        return userChats;
    }

    public UserBuilder setUserChats(List<UserChat> userChats) {
        this.userChats = userChats;
        return this;
    }

    @Override
    public User build() {
        return new User(id, username, personalInfo, role, info, password, company, profile, userChats, payments);
    }
}
