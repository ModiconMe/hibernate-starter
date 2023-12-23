package edu.popovd.entity;

import edu.popovd.listener.AuditDatesListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@MappedSuperclass
@EntityListeners({AuditDatesListener.class})
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T> {

    private Instant createdAt;
    private String createdBy;

    private Instant updatedAt;
    private String updatedBy;
}
