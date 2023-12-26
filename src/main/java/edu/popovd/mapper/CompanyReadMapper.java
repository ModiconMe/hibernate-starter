package edu.popovd.mapper;

import edu.popovd.dto.CompanyReadDto;
import edu.popovd.entity.Company;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

    @Override
    public CompanyReadDto mapFrom(Company object) {
        return new CompanyReadDto(object.getId(), object.getName(), object.getLocales());
    }
}
