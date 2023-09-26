package me.arhan.aidemo.math;

import java.util.ArrayList;
import java.util.List;

/**
 * TheAlgorithm is a class that implements a specific algorithm.
 * This algorithm calculates the convex hull of a given set of points.
 * The convex hull is the smallest convex polygon that contains all the points.
 */
public class TheAlgorithm {

    public static int t(D p, D q, D r) {
        int val = (q.y - p.y) * (r.x - q.x) -
                  (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    public static List<D> run(D[] dts) {
        if (dts.length < 3) return null;

        List<D> ds = new ArrayList<>();

        int l = 0;
        int l1 = l;
        for (int i1 = 1; i1 < dts.length; i1++)
            if (dts[i1].x < dts[l1].x)
                l1 = i1;
        l = l1;

        int p = l, q;
        do {
            ds.add(dts[p]);
            q = (p + 1) % dts.length;

            for (int i = 0; i < dts.length; i++) {
                if (t(dts[p], dts[i], dts[q]) == 2)
                    q = i;
            }

            p = q;

        } while (p != l);

        for (D temp : ds)
            System.out.println("(" + temp.x + ", " + temp.y + ")");

        return ds;
    }

}