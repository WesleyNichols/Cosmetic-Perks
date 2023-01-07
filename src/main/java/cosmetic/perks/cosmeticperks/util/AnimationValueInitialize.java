package cosmetic.perks.cosmeticperks.util;

import com.google.common.primitives.Doubles;
import cosmetic.perks.cosmeticperks.managers.AnimationValueManager;
import cosmetic.perks.cosmeticperks.structures.AnimationValues;
import cosmetic.perks.cosmeticperks.structures.Animations;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AnimationValueInitialize {
    private final String Name;
    private final Animations[] AnimationList;
    private final double[][] StyleValues;

    public AnimationValueInitialize(String name, double[][] styleValues, Animations[] animationList) {
        this.Name = name;
        this.AnimationList = animationList;
        this.StyleValues = styleValues;
        initialize();
    }

    public AnimationValueInitialize(String name, double[][] styleValues) {
        this.Name = name;
        this.AnimationList = null;
        this.StyleValues = styleValues;
        initialize();
    }

    public AnimationValueInitialize(String name, Animations[] animationList) {
        this.Name = name;
        this.AnimationList = animationList;
        this.StyleValues = new double[][]{};
        initialize();
    }

    public void initialize() {

        AnimationValueManager.addParticleAnimation(Name, new AnimationValues(StyleValues, generateEquationValueList()));
    }

    private double[][][] generateEquationValueList() {
        //Equation Value List
        List<double[][]> storedEValues = new ArrayList<>();
        //Animated Equation Value List
        List<double[][][]> storedAValues = new ArrayList<>();
        if(this.AnimationList == null) {return new double[][][]{};}
        //int finalListLength = 0;
        //double[][][][] storedValues = new double[Animation.length][][][];
        for(Animations animation: AnimationList) {
            String[] equationList = animation.getEquationList();
            double[] offset = animation.getOffset();
            //finalListLength += equationList.length/3;
            int length = animation.getTickToComplete() * (animation.isReversingAnimation() ? 2 : 1);
            //double[][][] tempList = new double[equationList.length/3][length][3];
            int exprNumber;
            for (int i=0; i<equationList.length/3; i++) {
                //Equating the equation
                double[][] temp = new double[length][3];
                for(int j=0; j<length; j++) {
                    exprNumber = (i) * 3;
                    animation.addToCurrentDistance();
                    for (int k = 0; k<3; k++) {
                        temp[j][k] = BigDecimal.valueOf(new ExpressionBuilder(equationList[exprNumber+k]).variable("x").build().setVariable("x", animation.getCurrentDistance()).evaluate() + offset[k]).setScale(5, RoundingMode.UP).doubleValue();
                    }
                }
                if(animation.getStyleValues() == null) {
                    storedEValues.add(temp);
                } else {
                    double[][] styleValues = animation.getStyleValues();
                    double[][][] currentAnimationValues = new double[temp.length][styleValues.length][3];
                    for(int l = 0; l < temp.length; l++) {
                        double magnitude = VectorUtils.magnitude(temp[l]);
                        double xAngle = Math.acos(temp[l][0]/magnitude);
                        double zAngle = Math.acos(temp[l][2]/magnitude);
                        for(int s = 0; s < styleValues.length; s++) {
                            currentAnimationValues[l][s] = VectorUtils.rotateVector(styleValues[s], xAngle, zAngle);
                        }
                    }
                    storedAValues.add(currentAnimationValues);
                }
            }
            //storedValues[a] = tempList;
        }

        double[][][] finalList = new double[storedEValues.size()][][];
        for(int c = 0; c < storedEValues.size(); c++) {
            finalList[c] = storedEValues.get(c);
        }

        double[][][][] finalAList = new double[storedAValues.size()][][][];
        for(int c = 0; c < storedAValues.size(); c++) {
            finalAList[c] = storedAValues.get(c);
        }
        /*
        for (double[][][] storedValue : storedValues) {
            for (int c = 0; c < storedValue.length; c++) {
                finalList[c] = storedValue[c];
            }
        }
        */
        return finalList;
    }
}


