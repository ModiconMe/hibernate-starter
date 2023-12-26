package edu.popovd.service;

import edu.popovd.dao.UserRepository;
import edu.popovd.dto.UserCreateDto;
import edu.popovd.dto.UserReadDto;
import edu.popovd.entity.User;
import edu.popovd.mapper.UserCreateMapper;
import edu.popovd.mapper.UserReadMapper;
import jakarta.persistence.EntityGraph;
import jakarta.transaction.Transactional;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    @Transactional
    public Long create(UserCreateDto userDto) {
        // validation
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<UserCreateDto>> validationResult = validator.validate(userDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }

        return userRepository.save(userCreateMapper.mapFrom(userDto)).getId();
    }

    @Transactional
    public Optional<UserReadDto> findById(Long id) {
        EntityGraph<?> entityGraph = userRepository.getEntityManager().getEntityGraph("withCompanyAndLocales");
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJakartaHintName(), entityGraph
        );
        return userRepository.findById(id, properties).map(userReadMapper::mapFrom);
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(id));
        return maybeUser.isPresent();
    }
}
