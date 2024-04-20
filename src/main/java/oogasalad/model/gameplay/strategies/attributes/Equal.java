package oogasalad.model.gameplay.strategies.attributes;

import java.util.Objects;

public abstract class Equal {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Since we know obj is an instance of the same class, they are considered equal
        return true;
    }

    @Override
    public int hashCode() {
        // Use the class of the object to produce the hash code
        return Objects.hash(getClass());
    }
}
