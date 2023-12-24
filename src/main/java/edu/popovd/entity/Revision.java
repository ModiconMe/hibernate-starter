package edu.popovd.entity;

import edu.popovd.entity.listener.CustomRevisionListener;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RevisionEntity(value = CustomRevisionListener.class)
public class Revision {

    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @RevisionTimestamp
    private Long timestamp;

    private String username;
}
