package edu.popovd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@PrimaryKeyJoinColumn(name = "id")
public class Manager extends User {

    private String projectName;

//    @Builder
//    public Manager(Long id, String username, PersonalInfo personalInfo, Role role, String info, String password, Company company, Profile profile, List<UserChat> userChats, String projectName) {
//        super(id, username, personalInfo, role, info, password, company, profile, userChats);
//        this.projectName = projectName;
//    }
}
