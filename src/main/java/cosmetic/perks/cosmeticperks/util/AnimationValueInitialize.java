package cosmetic.perks.cosmeticperks.util;

import cosmetic.perks.cosmeticperks.managers.AnimationValueManager;
import cosmetic.perks.cosmeticperks.structures.AnimationValues;
import cosmetic.perks.cosmeticperks.structures.Animations;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AnimationValueInitialize {
    private final String Name;
    private final Animations[] Animation;
    private final double[][] StyleValues;

    public AnimationValueInitialize(String name, double[][] styleValues, Animations[] animation) {
        this.Name = name;
        this.Animation = animation;
        this.StyleValues = styleValues;
        initialize();
    }

    public AnimationValueInitialize(String name, double[][] styleValues) {
        this.Name = name;
        this.Animation = null;
        this.StyleValues = styleValues;
        initialize();
    }

    public AnimationValueInitialize(String name, Animations[] animation) {
        this.Name = name;
        this.Animation = animation;
        this.StyleValues = new double[][]{};
        initialize();
    }

    public void initialize() {
        AnimationValueManager.addParticleAnimation(Name, new AnimationValues(StyleValues, generateEquationValueList()));
    }

    private double[][][] generateEquationValueList() {
        if(this.Animation == null) {return new double[][][]{};}
        int finalListLength = 0;
        double[][][][] storedValues = new double[Animation.length][][][];
        for(int a = 0; a < Animation.length; a++) {
            String[] equationList = Animation[a].getEquationList();
            double[] offset = Animation[a].getOffset();
            finalListLength += equationList.length/3;
            int length = Animation[a].getTickToComplete() * (Animation[a].isReversingAnimation() ? 2 : 1);
            double[][][] tempList = new double[equationList.length/3][length][3];
            int exprNumber;
            for (int i=0; i<equationList.length/3; i++) {
                for(int j=0; j<length; j++) {
                    exprNumber = (i) * 3;
                    Animation[a].addToCurrentDistance();
                    for (int k = 0; k<3; k++) {
                        tempList[i][j][k] = BigDecimal.valueOf(new ExpressionBuilder(equationList[exprNumber+k]).variable("x").build().setVariable("x", Animation[a].getCurrentDistance()).evaluate() + offset[k]).setScale(5, RoundingMode.UP).doubleValue();
                    }
                }
            }
            storedValues[a] = tempList;
        }
        double[][][] finalList = new double[finalListLength][][];
        for (double[][][] storedValue : storedValues) {
            for (int c = 0; c < storedValue.length; c++) {
                finalList[c] = storedValue[c];
            }
        }
        return finalList;
    }
}


