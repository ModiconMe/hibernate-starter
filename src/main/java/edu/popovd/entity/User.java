package edu.popovd.entity;

import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сущности Hibernate должны быть POJO
 * 1) getters + setters
 * 2) класс не должен быть final
 * 3) NoArgsConstructor
 * 4) equals and hashcode
 * 5) toString
 */
@FetchProfile(name = "withCompany", fetchOverrides = {
        @FetchProfile.FetchOverride(
                entity = User.class, association = "company", mode = FetchMode.JOIN
        )
})
@NamedEntityGraph(name = "withChat",
        attributeNodes = {
                @NamedAttributeNode(value = "userChats", subgraph = "chats"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "chats",
                        attributeNodes = {
                                @NamedAttributeNode("chat")
                        }
                )})
@NamedQuery(name = "findUserByFirstName", query = """
        select u from User u
                         join u.company c 
                         where u.personalInfo.firstname = :firstname
        """)
@Data
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "public", name = "users")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Users")
public class User implements BaseEntity<Long> {

    @Id // id должен быть Serializable
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Builder.Default
    private String username = UUID.randomUUID().toString();

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

//    @OneToOne(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
//    )
//    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<UserChat> userChats = new ArrayList<>();

    @Builder.Default
//    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    public String fullName() {
        return personalInfo.getFirstname() + " " + personalInfo.getLastname();
    }
}
