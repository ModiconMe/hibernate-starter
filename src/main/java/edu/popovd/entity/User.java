package edu.popovd.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
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
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "public", name = "users")
public class User {

    @Id // id должен быть Serializable
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Embedded
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JdbcTypeCode(SqlTypes.JSON)
    private String info;

    @ColumnTransformer(write = "md5(?)")
    // есть еще @Formula, но эта аннотация более предпочтительна, тк позволяет делать тоже самое
    // как на запись так и на чтение
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
