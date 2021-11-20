package util;

import java.io.Serializable;

public class Pair<T,V> implements Serializable {

    private static final long serialVersionUID = 9057214404509237417L;

    public final T left;

    public final V right;

    public Pair(T left, V right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {

        return left;
    }

    public V getRight() {

        return right;
    }

    @Override
    public String toString() {
        return left + ":" + right;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  Pair)) {
            return false;
        }

        Pair otherPair = (Pair)obj;

        return (left == null ? otherPair.left == null  : left.equals(otherPair.left))
                && (right == null ? otherPair.right == null  : right.equals(otherPair.right));
    }

    @Override
    public int hashCode() {
        return (left == null ? 0 : left.hashCode()) + (right == null ? 0 : right.hashCode());
    }
}
