package com.example.krz.android_opengl_t0;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by krz on 29-Jul-17.
 */

public class HexLifeGame {
    HashMap<Pair, Integer> Cells;

    //x y
    //byte: ffffcccs  0-1 state, 0-12 far neighbours, 0-6 close neighbours
    //?int:  8r8g8b 1byte

    private int CLOSE_NEIGHBOUR = 1 << 1;
    private int FAR_NEIGHBOUR = 1 << 4;

    public HexLifeGame() {
        Cells = new HashMap<Pair, Integer>(100, 0.8f);
        Cells.put(new Pair(0, 0), 1);
//        Cells.put(new Pair(1, 0), 1 << 7);
//        Cells.put(new Pair(2, 0), 1 << 7);
//        Cells.put(new Pair(0, 1), 1 << 7);
//        Cells.put(new Pair(0, 2), 1 << 7);
//        Cells.put(new Pair(1, -2), 1 << 7);
//        Cells.put(new Pair(0, -2), 1 << 7);
//        Cells.put(new Pair(-1, -1), 1 << 7);
    }

    public int[] getField(int cx, int cy, int radius) {
        int[] res = new int[fieldSize(radius) * 3];
        int stx, sty;
        Pair p = new Pair(0, 0);
        sty = cy + radius;
//        stx = cx - radius + 1 + (radius % 2) * ((sty + 1) % 2);
        StringBuilder sb = new StringBuilder(", ");
        // 0 0 1 1 2 1 3 2 4 2 5 3 6 3
//        int even = sty % 2 == 0 ? 1 : 2;
        int even = sty % 2 == 0 ? 1 : 0;
        int id = 0;
        for (int i = 0; i < radius; i++) {
            int row = cy + radius - i;
            int row2 = cy - radius + i;
            int colStart = cx - (radius + sty % 2) / 2 - (i + even) / 2; //can this be shorter?
            for (int j = 0; j < radius + i + 1; j++) {
                int col = colStart + j;
                p.x = col;
                p.y = row;
                Integer b = Cells.get(p);
                res[id + 0] = p.x;
                res[id + 1] = p.y;
                res[id + 2] = b == null ? 0 : b;
                id += 3;

                p.x = col;
                p.y = row2;
                b = Cells.get(p);
                res[id + 0] = p.x;
                res[id + 1] = p.y;
                res[id + 2] = b == null ? 0 : b;
                id += 3;
                sb.append(Integer.toString(col) + ":" + Integer.toString(row) + "  ");
                sb.append(Integer.toString(col) + ":" + Integer.toString(row2) + "  ");
            }
        }
        for (int i = 0; i < 2 * radius + 1; i++) {
            p.x = cx - radius + i;
            p.y = cy;
            Integer b = Cells.get(p);
            res[id + 0] = p.x;
            res[id + 1] = p.y;
            res[id + 2] = b == null ? 0 : b;
            id += 3;
            sb.append(Integer.toString(cx - radius + i) + "|" + Integer.toString(cy) + "  ");
        }
        Log.d("GetField", sb.toString());
        return res;
    }

    public int fieldSize(int radius) {
        return 1 + ((6 + 6 * radius) / 2 * radius);
    }

    public int pathLen(int x1, int y1, int x2, int y2) {
        int ydiff = Math.abs(y2 - y1);
        int xdiff = Math.abs(x2 - x1);
        //odd-even transitions
        int oet = (ydiff + ((x2 - x1) < 0 ? y2 % 2 : y1 % 2)) / 2; //+into odd to the left or from odd to the right
        if (xdiff > oet)
            xdiff -= oet;
        else
            xdiff = 0;
//        Log.d("hlgv", Integer.toString(xdiff + ydiff));
        return xdiff + ydiff;
    }

    private void addToCell(int x, int y, int n) {
        Pair tmp = new Pair(x, y);
        Integer val = Cells.get(tmp);
        if (val == null) val = n;
        else val += n;
        Cells.put(tmp, val);
    }

    public void step() {
        Pair t = new Pair(0, 0);
        Integer val;
        Pair[] ks = Cells.keySet().toArray(new Pair[0]);
        for (Pair p : ks) {
            addToCell(p.x - 1, p.y, CLOSE_NEIGHBOUR);
            addToCell(p.x + 1, p.y, CLOSE_NEIGHBOUR);
            addToCell(p.x, p.y - 1, CLOSE_NEIGHBOUR);
            addToCell(p.x, p.y + 1, CLOSE_NEIGHBOUR);
            addToCell(p.x + (p.y % 2 == 0 ? -1 : 1), p.y + 1, CLOSE_NEIGHBOUR);
            addToCell(p.x + (p.y % 2 == 0 ? -1 : 1), p.y - 1, CLOSE_NEIGHBOUR);

            addToCell(p.x, p.y + 2, FAR_NEIGHBOUR);
            addToCell(p.x - 1, p.y + 2, FAR_NEIGHBOUR);
            addToCell(p.x + 1, p.y + 2, FAR_NEIGHBOUR);
            addToCell(p.x, p.y - 2, FAR_NEIGHBOUR);
            addToCell(p.x - 1, p.y - 2, FAR_NEIGHBOUR);
            addToCell(p.x + 1, p.y - 2, FAR_NEIGHBOUR);
            addToCell(p.x + (p.y % 2 == 0 ? 1 : 2), p.y + 1, FAR_NEIGHBOUR);
            addToCell(p.x + (p.y % 2 == 0 ? 1 : 2), p.y - 1, FAR_NEIGHBOUR);
            addToCell(p.x - (p.y % 2 == 0 ? 2 : 1), p.y + 1, FAR_NEIGHBOUR);
            addToCell(p.x - (p.y % 2 == 0 ? 2 : 1), p.y - 1, FAR_NEIGHBOUR);
            addToCell(p.x - 2, p.y, FAR_NEIGHBOUR);
            addToCell(p.x + 2, p.y, FAR_NEIGHBOUR);
        }
        ks = Cells.keySet().toArray(new Pair[0]);
//        HashMap<Pair, Integer> newStep = new HashMap<>()
        for (Pair p : ks) {
            val = Cells.get(p);
            int closeNeighbours = (val>>1) & 7;
            int farNeighbours = (val >> 4) & 15;
            int curState = val & 1;

            Integer newVal = 0;

            if (closeNeighbours > 0 && curState == 0) {
                newVal = 1 ;
                Cells.put(p, newVal);
            } else
                Cells.remove(p);
        }
    }
}
