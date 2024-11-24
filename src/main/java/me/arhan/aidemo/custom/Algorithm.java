package me.arhan.aidemo.custom;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Algorithm {

    int MINIMUM_POINTS_FOR_HULL = 3;

    @NotNull List<Coordinate> run(@NotNull List<Coordinate> dts);
}
