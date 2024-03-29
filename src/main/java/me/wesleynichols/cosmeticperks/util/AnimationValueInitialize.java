package me.wesleynichols.cosmeticperks.util;

import me.wesleynichols.cosmeticperks.managers.AnimationValueManager;
import me.wesleynichols.cosmeticperks.structures.AnimationValues;
import me.wesleynichols.cosmeticperks.structures.Animations;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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
        //No animations present
        if(this.Animation == null) {return new double[][][]{};}
        //Equation Value List
        List<double[][]> storedEValues = new ArrayList<>();
        for(Animations animation: Animation) {
            String[] equationList = animation.getEquationList();
            double[] offset = animation.getOffset();
            double[] angleOffset = animation.getAngleOffset();
            int length = animation.getTickToComplete() * (animation.isReversingAnimation() ? 2 : 1);
            for (int i=0; i<equationList.length/3; i++) {
                //Equating the equation
                double[][] temp = new double[length][3];
                int exprNumber = (i) * 3;
                for(int j=0; j<length; j++) {
                    animation.addToCurrentDistance();
                    for (int k = 0; k<3; k++) {
                        temp[j][k] = BigDecimal.valueOf(new ExpressionBuilder(equationList[exprNumber+k]).variable("x").build().setVariable("x", animation.getCurrentDistance()).evaluate() + offset[k]).setScale(5, RoundingMode.UP).doubleValue();
                    }
                    temp[j] = VectorUtils.rotateVector(temp[j], angleOffset[0], angleOffset[1]);
                }
                storedEValues.add(temp);
            }
        }

        double[][][] finalList = new double[storedEValues.size()][][];
        for(int c = 0; c < storedEValues.size(); c++) {
            finalList[c] = storedEValues.get(c);
        }

        return finalList;
    }
}


