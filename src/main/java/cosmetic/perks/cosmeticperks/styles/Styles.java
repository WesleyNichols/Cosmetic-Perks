package cosmetic.perks.cosmeticperks.styles;

import javax.annotation.Nullable;

public class Styles {

    public static double[][][] styleValues(double[][]... styles) {
        return styles;
    } // TODO Maybe make this return a double[][]


    public static double[] checkOffset(@Nullable double[] offset) {
        if (offset == null) {return new double[]{0, 0, 0};}
        else if (offset.length != 3) {throw new IllegalArgumentException("Offset has more values than expected");}
        return offset;
    }

    public static double[][] circle(double radius, int points, @Nullable double[] offset) {
        offset = checkOffset(offset);
        double[][] styleValues = new double[points][3];
        double amountToAdd = 2*Math.PI/points;
        for(int i = 0; i < points; i++){
            double currentValue = amountToAdd * i;
            styleValues[i] = new double[]{radius * Math.cos(currentValue) + offset[0], offset[1], radius * Math.sin(currentValue) + offset[2]};
        }
        return styleValues;
    } // TODO Maybe make styles return a double[]
}
