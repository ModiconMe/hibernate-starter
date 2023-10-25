package edu.popovd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Сущности Hibernate должны быть POJO
 * 1) getters + setters
 * 2) класс не должен быть final
 * 3) NoArgsConstructor
 * 4) equals and hashcode
 * 5) toString
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "public", name = "users")
public class User {

    @Id // id должен быть Serializable
    private String username;
    private String firstname;
    private String lastname;

    //    @Convert(converter = BirthdayConverter.class)
    private Birthday birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JdbcTypeCode(SqlTypes.JSON)
    private String info;
}
