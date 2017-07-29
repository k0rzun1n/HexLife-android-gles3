package com.example.krz.android_opengl_t0;

import java.util.HashMap;

/**
 * Created by krz on 29-Jul-17.
 */

public class HexLifeGame {
    HashMap<Pair, Byte> Cells;

    //x y
    //sffffccc  0-1 state, 0-12 far neighbours, 0-6 close neighbours

    public HexLifeGame() {
        Cells = new HashMap<Pair, Byte>(100, 0.8f);
        Cells.put(new Pair(0, 0), (byte) (1 << 7));
        Cells.put(new Pair(1, 0), (byte) (1 << 7));
        Cells.put(new Pair(2, 0), (byte) (1 << 7));
        Cells.put(new Pair(0, 1), (byte) (1 << 7));
        Cells.put(new Pair(0, 2), (byte) (1 << 7));
    }

    public int fieldSize(int radius) {
        return 1 + ((6 + 6*radius) / 2 * radius);
    }

    public int pathLen(int x1, int y1, int x2, int y2) {
        int ydiff = Math.abs(y2 - y1);
        int xdiff = Math.abs(x2 - x1);
        //odd-even transitions
        int oet = (ydiff + (x2 - x1 < 0 ? y2 % 2 : y1 % 2)) / 2; //+into odd to the left or from odd to the right
        if (xdiff > oet)
            xdiff -= oet;
        else
            xdiff = 0;
//        Log.d("hlgv", Integer.toString(xdiff + ydiff));
        return xdiff + ydiff;
    }

    public void step() {
//        Log.d("hlg", Boolean.toString(fieldSize(3)==37));
//        Log.d("hlg", Boolean.toString(pathLen(0, 0, 0, 0) == 0));
//        Log.d("hlg", Boolean.toString(pathLen(0, 0, 2, 3) == 4));
//        Log.d("hlg", Boolean.toString(pathLen(0, 0, 1, 3) == 3));
//        Log.d("hlg", Boolean.toString(pathLen(0, 0, 1, 1) == 2));
//        Log.d("hlg", Boolean.toString(pathLen(-1, 3, 3, 0) == 5));
//        Log.d("hlg", Boolean.toString(pathLen(3, 0, -1, 3) == 5));
//        Log.d("hlg", Boolean.toString(pathLen(3, 0, 1, 3) == 3));
//        Log.d("hlg", Boolean.toString(pathLen(2, 1, -1, 3) == 4));
//        Log.d("hlg", Boolean.toString(pathLen(1, 2, 0, 2) == 1));
//        Log.d("hlg", Boolean.toString(pathLen(1, 2, 2, 2) == 1));
//        Log.d("hlg", Boolean.toString(pathLen(1, 2, 1, 1) == 1));
//        Log.d("hlg", Boolean.toString(pathLen(1, 2, 1, 3) == 1));
//        Log.d("hlg", Boolean.toString(pathLen(1, 2, 0, 3) == 1));
//        Log.d("hlg", Boolean.toString(pathLen(1, 2, 0, 1) == 1));
//        Log.d("hlg", Boolean.toString(pathLen(1, 2, 1, 2) == 0));
//        for(c )

    }
}
