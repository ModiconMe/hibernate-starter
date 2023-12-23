package edu.popovd.listener;

import edu.popovd.entity.AuditableEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class AuditDatesListener {

    @PrePersist
    public void prePersist(AuditableEntity<?> entity) {
        entity.setCreatedAt(Instant.now());
        entity.setCreatedBy("Dima");
    }

    @PreUpdate
    public void preUpdate(AuditableEntity<?> entity) {
        entity.setUpdatedAt(Instant.now());
        entity.setUpdatedBy("Dimasosek");
    }
}
