package edu.popovd.dto;

import edu.popovd.entity.LocaleInfo;

import java.util.List;

public record CompanyReadDto(Long id, String name, List<LocaleInfo> locales) {
}
