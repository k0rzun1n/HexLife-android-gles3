package com.example.krz.android_opengl_t0;

/**
 * Created by krz on 24-Jul-17.
 */

public class Pair extends Object {
    public int x, y;

    public Pair(int a, int b) {
        super();
        x = a;
        y = b;
    }

    @Override
    public int hashCode() {
        return ((x << 16) + y);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof Pair)) return false;
        Pair p2 = (Pair) that;
        return x == p2.x
                && y == p2.y;
    }
}
