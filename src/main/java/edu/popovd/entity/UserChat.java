package edu.popovd.entity;

import edu.popovd.listener.UserChatListener;
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
@Table(name = "users_chat")
@EntityListeners({UserChatListener.class})
public class UserChat extends AuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    public void addUser(User user) {
        this.user = user;
        user.getUserChats().add(this);
    }

    public void addChat(Chat chat) {
        this.chat = chat;
        chat.getUserChats().add(this);
    }
}
