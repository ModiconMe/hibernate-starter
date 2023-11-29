package edu.popovd.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "public", name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.DETACH, orphanRemoval = true)
    @OrderBy("username DESC, personalInfo.lastname")
    private List<User> users = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
//    @AttributeOverride(name = "lang", column = @Column(name = "language"))
    private List<LocaleInfo> locales = new ArrayList<>();

//    @Builder.Default
//    @ElementCollection
//    @CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
//    @Column(name = "description")
//    private List<String> locales = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setCompany(this);
    }

}
