package cosmetic.perks.cosmeticperks.styles;

import org.yaml.snakeyaml.util.ArrayUtils;

import javax.annotation.Nullable;

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
        else if (offset.length != 3) {throw new IllegalArgumentException("Offset has more values than expected");}
        return offset;
    }

    public static double[][] circle(double radius, int points, @Nullable double[] angleOffset, @Nullable double[] positionOffset) {
        if (angleOffset == null) {angleOffset = new double[]{0, 0, 0};}
        positionOffset = checkOffset(positionOffset);
        double[][] styleValues = new double[points][3];
        double amountToAdd = 2*Math.PI/points;
        for(int i = 0; i < points; i++){
            double currentValue = amountToAdd * i;
            styleValues[i] = new double[]{Math.cos(Math.toRadians(angleOffset[0])) * radius * Math.cos(currentValue) + positionOffset[0],
                    Math.cos(Math.toRadians(90-angleOffset[0])) * radius * Math.cos(currentValue) + positionOffset[1],
                    radius * Math.sin(currentValue) + positionOffset[2]};
        }
        return styleValues;
    }
}
