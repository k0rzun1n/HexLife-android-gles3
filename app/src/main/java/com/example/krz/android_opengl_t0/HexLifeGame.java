package com.example.krz.android_opengl_t0;

import android.os.SystemClock;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by krz on 29-Jul-17.
 */

public class HexLifeGame {
    HashMap<Pair, Integer> Cells;
    public long startTime;

    //x y
    //byte: ffffcccs  0-1 state, 0-12 far neighbours, 0-6 close neighbours
    //int:  starttime byte   //more than 4 hours

    private int CLOSE_NEIGHBOUR = 1 << 1;
    private int FAR_NEIGHBOUR = 1 << 4;

    public HexLifeGame() {
        startTime = SystemClock.uptimeMillis();
        Cells = new HashMap<Pair, Integer>(100, 0.8f);
        Cells.put(new Pair(0, 0), 1);
    }

    public int[] getField(int cx, int cy, int radius) {
        int[] res = new int[fieldSize(radius) * 3];
        int stx, sty;
        Pair p = new Pair(0, 0);
        sty = cy + radius;
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
        }
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
        return xdiff + ydiff;
    }

    private void addToCell(int x, int y, int n) {
        Pair tmp = new Pair(x, y);
        Integer val = Cells.get(tmp);
        if (val == null) val = n;
        else val += n;
        Cells.put(tmp, val);
    }

    public void switchCell(int x, int y) {
        Pair tmp = new Pair(x, y);
        Integer val = Cells.get(tmp);
        if (val == null) Cells.put(tmp, 1);
        else Cells.remove(tmp);
    }

    public void step() {
        Log.d("Step", Long.toString(SystemClock.uptimeMillis()));
        Pair t = new Pair(0, 0);
        Integer val;
        Pair[] ks = Cells.keySet().toArray(new Pair[0]);
        for (Pair p : ks) {
            val = Cells.get(p);
            if (val != null && ((val & 1) == 1)) {
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
        }
        ks = Cells.keySet().toArray(new Pair[0]);

        int newTime = (int) (SystemClock.uptimeMillis() - startTime);
        Integer newState = newTime << 8; //pack 24 bits of millisecs into first 3 bytes of int

        newState |= 1; //alive
        for (Pair p : ks) {
            val = Cells.get(p);
            int closeNeighbours = (val >> 1) & 7;
            int farNeighbours = (val >> 4) & 15;
            boolean alive = ((val & 1) == 1);
            int cellTime = (val >> 8) & ((1 << 24) - 1);
            int timeDiff = Math.min(1000, Math.max(0, newTime - cellTime));
            //todo rearrange and figure out rules for far neighbours
            if (closeNeighbours >= 2 && closeNeighbours <= 3 && alive)
                Cells.put(p, val & ~((1 << 8) - 2)); //keep timer, remove neighbours, if staying alive
            else if (closeNeighbours >= 2 && closeNeighbours <= 2 && !alive)
                Cells.put(p, newState);
            else if (alive) {
                //if alive but is going to die
                //reverse timer, so transition becomes 0.3->0.7, 0.9->0.1...
                //1000ms length of transition, changing here change in shader too
                int newCellState = (newTime - 1000 + timeDiff) << 8;
                Cells.put(p, newCellState);
            } else if (timeDiff < 1000)
                Cells.put(p, val & ~((1 << 8) - 2)); //keep timer, remove neighbours, if dying in transition
            else
                Cells.remove(p);
        }
    }
}
