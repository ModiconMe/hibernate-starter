package edu.popovd.builder;

import edu.popovd.GenericBuilder;
import edu.popovd.TestCommons;
import edu.popovd.entity.Company;
import edu.popovd.entity.LocaleInfo;
import edu.popovd.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class CompanyBuilder implements GenericBuilder<Company> {

    private Long id;
    private String name = "company-name" + TestCommons.getUniqString();
    private SortedMap<String, User> users = new TreeMap<>();
    private List<LocaleInfo> locales = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public CompanyBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CompanyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SortedMap<String, User> getUsers() {
        return users;
    }

    public CompanyBuilder setUsers(SortedMap<String, User> users) {
        this.users = users;
        return this;
    }

    public List<LocaleInfo> getLocales() {
        return locales;
    }

    public CompanyBuilder setLocales(List<LocaleInfo> locales) {
        this.locales = locales;
        return this;
    }

    @Override
    public Company build() {
        return new Company(id, name, users, locales);
    }
}
