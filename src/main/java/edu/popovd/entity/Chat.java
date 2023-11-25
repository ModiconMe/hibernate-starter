package edu.popovd.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "users")
@Builder
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false, unique = true)
    private String name = UUID.randomUUID().toString();

    @Builder.Default
    @ManyToMany(mappedBy = "chats")
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getChats().add(this);
    }
}
