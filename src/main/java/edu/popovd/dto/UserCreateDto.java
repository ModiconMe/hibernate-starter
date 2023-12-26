package edu.popovd.dto;

import edu.popovd.entity.PersonalInfo;
import edu.popovd.entity.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UserCreateDto(@Valid PersonalInfo personalInfo,
                            @NotNull String username,
                            String info,
                            Role role,
                            Long companyId) {
}
