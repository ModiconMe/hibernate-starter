package edu.popovd.listener;

import edu.popovd.entity.UserChat;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;

public class UserChatListener {

    @PostPersist
    public void postPersist(UserChat entity) {
        entity.getChat().setCount(entity.getChat().getCount() + 1);
    }

    @PostRemove
    public void postRemove(UserChat entity) {
        entity.getChat().setCount(entity.getChat().getCount() - 1);
    }
}
