package me.arhan.aidemo.custom;

import me.arhan.aidemo.math.D;

import java.util.*;
import java.util.stream.Collectors;

public class AnotherAlgorithm {
    public static List<D> run(List<D> data) {
        if (data.size() < 2) throw new IllegalArgumentException("Need more data");
        Set<D> dataSet = new HashSet<>();
        for (D datum : data) {
            List<D> list = filterDataWithinRange(datum, data);
            dataSet.addAll(list);
        }
        dataSet.forEach(AnotherAlgorithm::printData);
        return new ArrayList<>(dataSet);
    }

    private static List<D> filterDataWithinRange(D dt, List<D> data) {
        return data.stream()
                .filter(d -> {
                    int delta = Math.abs((dt.getX() - d.getX()) + (dt.getY() - d.getY()));
                    return delta < 5;
                })
                .collect(Collectors.toList());
    }

    private static void printData(D temp) {
        System.out.println("(" + temp.getX() + ", " + temp.getY() + ")");
    }
}