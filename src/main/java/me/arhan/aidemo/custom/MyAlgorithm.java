package me.arhan.aidemo.custom;

import me.arhan.aidemo.math.D;

import java.util.ArrayList;
import java.util.List;
public class MyAlgorithm {
    public static List<D> run(D[] dts) {
        if (dts.length < 1) throw new IllegalArgumentException("Need more data");

        List<D> ds = new ArrayList<>();

        for (D dt : dts) {
            if (dt.x < 5 && dt.y < 5) {
                ds.add(dt);
            }
        }

        for (D temp : ds)
            System.out.println("(" + temp.x + ", " + temp.y + ")");

        return ds;
    }

}