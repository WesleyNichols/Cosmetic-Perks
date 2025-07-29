package me.wesleynichols.cosmeticperks.animation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnimationStyles {

    public static double[][] styleValues(double[][]... styles) {
        int length = 0;
        for (double[][] list : styles) {
            length += list.length;
        }

        double[][] values = new double[length][3];
        int index = 0;
        for (double[][] list : styles) {
            for (double[] loc : list) {
                values[index++] = loc;
            }
        }
        return values;
    }

    public static double[] checkOffset(@Nullable double[] offset) {
        if (offset == null) return new double[]{0, 0, 0};
        if (offset.length != 3) throw new IllegalArgumentException("Offset must have exactly 3 values");
        return offset;
    }

    public static double[] checkAngleOffset(@Nullable double[] angleOffset) {
        if (angleOffset == null) return new double[]{0, 0};
        if (angleOffset.length != 2) throw new IllegalArgumentException("AngleOffset must have exactly 2 values");
        return angleOffset;
    }

    public static double[][] circle(@Nullable double[] offset, @Nullable double[] angleOffset, double radius, int points) {
        offset = checkOffset(offset);
        angleOffset = checkAngleOffset(angleOffset);

        double[][] styleValues = new double[points][3];
        double step = 2 * Math.PI / points;

        for (int i = 0; i < points; i++) {
            double angle = step * i;
            double[] point = new double[]{
                    radius * Math.cos(angle) + offset[0],
                    offset[1],
                    radius * Math.sin(angle) + offset[2]
            };
            styleValues[i] = AnimationVectors.rotateVector(point, angleOffset[0], angleOffset[1]);
        }

        return styleValues;
    }

    public static double[][] square(@Nullable double[] offset, @Nullable double[] angleOffset, double size, int pointsPerSide) {
        offset = checkOffset(offset);
        angleOffset = checkAngleOffset(angleOffset);

        List<List<Double>> tempPoints = new ArrayList<>();

        double halfSize = size / 2.0;
        double step = size / (pointsPerSide + 1);
        double min = -halfSize;
        double max = halfSize;

        // Corners
        tempPoints.add(Arrays.asList(min, 0.0, min));
        tempPoints.add(Arrays.asList(min, 0.0, max));
        tempPoints.add(Arrays.asList(max, 0.0, min));
        tempPoints.add(Arrays.asList(max, 0.0, max));

        // Points along edges (excluding corners)
        for (double x = min + step; x < max; x += step) {
            tempPoints.add(Arrays.asList(x, 0.0, min));
            tempPoints.add(Arrays.asList(x, 0.0, max));
        }
        for (double z = min + step; z < max; z += step) {
            tempPoints.add(Arrays.asList(min, 0.0, z));
            tempPoints.add(Arrays.asList(max, 0.0, z));
        }

        double[][] styleValues = new double[tempPoints.size()][3];
        for (int i = 0; i < tempPoints.size(); i++) {
            List<Double> p = tempPoints.get(i);
            double[] rotated = AnimationVectors.rotateVector(new double[]{p.get(0), p.get(1), p.get(2)}, angleOffset[0], angleOffset[1]);
            styleValues[i] = new double[]{
                    rotated[0] + offset[0],
                    rotated[1] + offset[1],
                    rotated[2] + offset[2]
            };
        }

        return styleValues;
    }
}
