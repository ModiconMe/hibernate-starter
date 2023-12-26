package edu.popovd.mapper;

import edu.popovd.dao.CompanyRepository;
import edu.popovd.dto.UserCreateDto;
import edu.popovd.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    private final CompanyRepository companyRepository;

    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .personalInfo(object.personalInfo())
                .username(object.username())
                .info(object.info())
                .role(object.role())
                .company(companyRepository.findById(object.companyId()).orElse(null))
                .build();
    }
}
