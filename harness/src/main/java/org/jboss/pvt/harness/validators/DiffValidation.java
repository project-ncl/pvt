package org.jboss.pvt.harness.validators;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yyang@redhat.com">Yong Yang</a>
 */
public class DiffValidation extends Validation {

    private List<File> added = new ArrayList<>();
    private List<File> removed = new ArrayList<>();
    private List<File> changed = new ArrayList<>();
    private List<File> unChanged = new ArrayList<>();

    public DiffValidation(List<File> added, List<File> removed, List<File> changed, List<File> unChanged) {
        super(false);
        this.added = added;
        this.removed = removed;
        this.changed = changed;
        this.unChanged = unChanged;
    }

    @Override
    public boolean isValid() {
        return added.isEmpty() && removed.isEmpty() && changed.isEmpty();
    }

    public List<File> getAdded() {
        return added;
    }

    public void setAdded(List<File> added) {
        this.added = added;
    }

    public List<File> getRemoved() {
        return removed;
    }

    public void setRemoved(List<File> removed) {
        this.removed = removed;
    }

    public List<File> getChanged() {
        return changed;
    }

    public void setChanged(List<File> changed) {
        this.changed = changed;
    }

    public List<File> getUnChanged() {
        return unChanged;
    }

    public void setUnChanged(List<File> unChanged) {
        this.unChanged = unChanged;
    }
}
