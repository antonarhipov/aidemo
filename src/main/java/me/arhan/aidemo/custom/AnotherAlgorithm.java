package me.arhan.aidemo.custom;

import me.arhan.aidemo.math.D;

import java.util.*;

public class AnotherAlgorithm {
    public static List<D> run(D[] dts) {
        if (dts.length < 2) throw new IllegalArgumentException("Need more data");

        Set<D> ds = new HashSet<>();

        for (D dt : dts) {
            List<D> list = f(dt, dts);
            ds.addAll(list);
        }

        for (D temp : ds)
            System.out.println("(" + temp.x + ", " + temp.y + ")");

        return ds.stream().toList();
    }

    private static List<D> f(D dt, D[] dts) {
//        return Arrays.stream(dts).filter(d -> dt.x - d.x < 5 && dt.y - d.y < 5).toList();
        return Arrays.stream(dts).filter(d -> (dt.x - d.x) + (dt.y - d.y) < 5).toList();
    }

}