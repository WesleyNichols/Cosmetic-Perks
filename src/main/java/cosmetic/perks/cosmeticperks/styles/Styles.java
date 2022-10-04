package cosmetic.perks.cosmeticperks.styles;

import cosmetic.perks.cosmeticperks.util.VectorUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Styles {

    public static double[][] styleValues(double[][]... styles) {
        int step = 0;
        int length = 0;
        for (double[][] list: styles) {
            length+=list.length;
        }
        double[][] values = new double[length][3];
        for (double[][] list: styles) {
            for(double[] loc: list) {
                values[step] = loc;
                step++;
            }
        }
        return values;
    }


    public static double[] checkOffset(@Nullable double[] offset) {
        if (offset == null) {return new double[]{0, 0, 0};}
        else if (offset.length != 3) {throw new IllegalArgumentException("Offset has more values than expected!");}
        return offset;
    }

    public static double[] checkAngleOffset(@Nullable double[] angleOffset) {
        if (angleOffset == null) {angleOffset = new double[]{0, 0, 0};}
        else if (angleOffset.length != 3) {throw new IllegalArgumentException("AngleOffset has more values than expected!");}
        return angleOffset;
    }

    public static double[][] circle(double radius, int points, @Nullable double[] angleOffset, @Nullable double[] positionOffset) {
        angleOffset = checkAngleOffset(angleOffset);
        positionOffset = checkOffset(positionOffset);
        double[][] styleValues = new double[points][3];
        double amountToAdd = 2*Math.PI/points;
        for(int i = 0; i < points; i++){
            double currentValue = amountToAdd * i;
            styleValues[i] = VectorUtils.rotateVector(new double[]{radius * Math.cos(currentValue) + positionOffset[0],
                    positionOffset[1],
                    radius * Math.sin(currentValue) + positionOffset[2]}, angleOffset[0], angleOffset[1]);
        }
        return styleValues;
    }

    public static double[][] square(double size, int pointsPerSide, @Nullable double[] angleOffset, @Nullable double[] positionOffset) {
        angleOffset = checkAngleOffset(angleOffset);
        positionOffset = checkOffset(positionOffset);
        List<List<Double>> styleValuesTemp = new ArrayList<>();
        double amountToAdd = 2*(size)/(pointsPerSide*4);
        double min = -size/2;
        double max = size/2;
        styleValuesTemp.add(Arrays.asList(min+positionOffset[0], positionOffset[1], min+positionOffset[2]));
        styleValuesTemp.add(Arrays.asList(min+positionOffset[0], positionOffset[1], max+positionOffset[2]));
        styleValuesTemp.add(Arrays.asList(max+positionOffset[0], positionOffset[1], min+positionOffset[2]));
        styleValuesTemp.add(Arrays.asList(max+positionOffset[0], positionOffset[1], max+positionOffset[2]));
        for (double x = min+amountToAdd; x <= max-amountToAdd; x+=amountToAdd) {
            styleValuesTemp.add(Arrays.asList(x+positionOffset[0], positionOffset[1], min+positionOffset[2]));
            styleValuesTemp.add(Arrays.asList(x+positionOffset[0], positionOffset[1], max+positionOffset[2]));
        }
        for (double z = min+amountToAdd; z <= max-amountToAdd; z+=amountToAdd) {
            styleValuesTemp.add(Arrays.asList(min+positionOffset[0], positionOffset[1], z+positionOffset[2]));
            styleValuesTemp.add(Arrays.asList(max+positionOffset[0], positionOffset[1], z+positionOffset[2]));
        }
        double[][] styleValues = new double[styleValuesTemp.size()][3];
        for(int i = 0; i < styleValuesTemp.size(); i++){
            List<Double> doubleList = styleValuesTemp.get(i);
            styleValues[i] = VectorUtils.rotateVector(new double[]{doubleList.get(0), doubleList.get(1), doubleList.get(2)}, angleOffset[0], angleOffset[1]);
        }
        return styleValues;
    }
}
