package cosmetic.perks.cosmeticperks.util;

import com.google.common.primitives.Doubles;
import cosmetic.perks.cosmeticperks.managers.AnimationValueManager;
import cosmetic.perks.cosmeticperks.structures.AnimationValues;
import cosmetic.perks.cosmeticperks.structures.Animations;
import net.kyori.adventure.text.Component;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AnimationValueInitialize {
    private final String Name;
    private final Animations[] AnimationList;
    private final double[][] StyleValues;
    private double[][][][] FinalAList;

    public AnimationValueInitialize(String name, double[][] styleValues, Animations[] animationList) {
        this.Name = name;
        this.AnimationList = animationList;
        this.StyleValues = styleValues;
        initialize();
    }

    public AnimationValueInitialize(String name, double[][] styleValues) {
        this.Name = name;
        this.AnimationList = null;
        this.FinalAList = new double[][][][]{};
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
        double[][][] equationValues = generateEquationValueList();
        Bukkit.broadcast(Component.text("Length: " + FinalAList.length));
        Bukkit.broadcast(Component.text("equation-Length: " + equationValues.length));
        AnimationValueManager.addParticleAnimation(Name, new AnimationValues(StyleValues, equationValues, FinalAList));
    }

    private double[][][] generateEquationValueList() {
        //No animations present
        if(this.FinalAList == null) {this.FinalAList = new double[][][][]{};}
        if(this.AnimationList == null) {return new double[][][]{};}
        //Equation Value List
        List<double[][]> storedEValues = new ArrayList<>();
        //Animated Equation Value List
        List<double[][][]> storedAValues = new ArrayList<>();
        for(Animations animation: AnimationList) {
            String[] equationList = animation.getEquationList();
            double[] offset = animation.getOffset();
            int length = animation.getTickToComplete() * (animation.isReversingAnimation() ? 2 : 1);
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
                    Bukkit.broadcast(Component.text("temp-length: " + temp.length));
                    for(int l = 0; l < temp.length; l++) {
                        double magnitude = VectorUtils.magnitude(temp[l]);
                        double xAngle = Math.toDegrees(Math.acos(temp[l][1]/magnitude));
                        double zAngle = Math.toDegrees(Math.acos(temp[l][2]/magnitude));
                        //Bukkit.broadcast(Component.text("xangle: " + xAngle));
                        //Bukkit.broadcast(Component.text("zangle: " + zAngle));
                        for(int s = 0; s < styleValues.length; s++) {
                            currentAnimationValues[l][s] = VectorUtils.rotateVector(styleValues[s], zAngle, xAngle);
                        }
                    }
                    storedAValues.add(currentAnimationValues);
                }
            }
        }

        double[][][] finalList = new double[storedEValues.size()][][];
        for(int c = 0; c < storedEValues.size(); c++) {
            finalList[c] = storedEValues.get(c);
        }

        double[][][][] finalAList = new double[storedAValues.size()][][][];
        for(int c = 0; c < storedAValues.size(); c++) {
            finalAList[c] = storedAValues.get(c);
        }
        this.FinalAList = finalAList;

        return finalList;
    }
}


