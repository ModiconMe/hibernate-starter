package edu.popovd.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SortNatural;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Getter
@Setter
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "public", name = "company")
//@BatchSize(size = 3)
@Audited
public class Company implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotAudited
    @Builder.Default
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.DETACH, orphanRemoval = true)
    @MapKey(name = "username")
    @SortNatural
    private SortedMap<String, User> users = new TreeMap<>();

    @NotAudited
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
        users.put(user.getUsername(), user);
        user.setCompany(this);
    }

}
