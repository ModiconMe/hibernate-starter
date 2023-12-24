package edu.popovd.entity.listener;

import edu.popovd.entity.Revision;
import org.hibernate.envers.RevisionListener;

public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        // SecurityContext.getAuthority()
        ((Revision) revisionEntity).setUsername("Dimasik");
    }
}
