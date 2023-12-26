package edu.popovd.dto;

import edu.popovd.entity.PersonalInfo;
import edu.popovd.entity.Role;

public record UserReadDto(
        Long id,
        PersonalInfo personalInfo,
        String username,
        String info,
        Role role,
        CompanyReadDto company
) {
}
