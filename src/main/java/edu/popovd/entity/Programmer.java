package edu.popovd.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@PrimaryKeyJoinColumn(name = "id")
public class Programmer extends User {

    @Enumerated(EnumType.STRING)
    private Language language;

//    @Builder
//    public Programmer(Long id, String username, PersonalInfo personalInfo, Role role, String info, String password, Company company, Profile profile, List<UserChat> userChats, Language language) {
//        super(id, username, personalInfo, role, info, password, company, profile, userChats);
//        this.language = language;
//    }
}
