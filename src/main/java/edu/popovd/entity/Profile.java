package edu.popovd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Profile {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
//    @JoinColumn(name = "user_id")
    @PrimaryKeyJoinColumn
    private User user;

    private String street;

    private String language;

    public void setUser(User user) {
        this.user = user;
        user.setProfile(this);
        this.id = user.getId();
    }
}
