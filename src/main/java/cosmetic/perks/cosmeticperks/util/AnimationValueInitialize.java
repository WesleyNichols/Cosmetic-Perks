package cosmetic.perks.cosmeticperks.util;

import cosmetic.perks.cosmeticperks.managers.AnimationValueManager;
import cosmetic.perks.cosmeticperks.structures.AnimationValues;
import cosmetic.perks.cosmeticperks.structures.Animations;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AnimationValueInitialize {
    private final String Name;
    private final String[] EquationList;
    private final int TicksToComplete;
    private final boolean ReversingAnimation;
    private final Animations Animation;
    private final double[][] StyleValues;

    public AnimationValueInitialize(String name, double[][] styleValues, Animations animation) {
        this.Name = name;
        this.Animation = animation;
        this.StyleValues = styleValues;
        this.EquationList = animation.getEquationList();
        this.TicksToComplete = animation.getTickToComplete();
        this.ReversingAnimation = animation.isReversingAnimation();
        initialize();
    }

    public AnimationValueInitialize(String name, double[][] styleValues) {
        this.Name = name;
        this.Animation = null;
        this.StyleValues = styleValues;
        this.EquationList = null;
        this.TicksToComplete = 0;
        this.ReversingAnimation = false;
        initialize();
    }

    public AnimationValueInitialize(String name, Animations animation) {
        this.Name = name;
        this.Animation = animation;
        this.StyleValues = new double[][]{};
        this.EquationList = animation.getEquationList();
        this.TicksToComplete = animation.getTickToComplete();
        this.ReversingAnimation = animation.isReversingAnimation();
        initialize();
    }

    public void initialize() {
        AnimationValueManager.addParticleAnimation(Name, new AnimationValues(StyleValues, generateEquationValueList()));
    }

    private double[][][] generateEquationValueList() {
        if(this.Animation == null) {return new double[][][]{};}
        int length = TicksToComplete * (ReversingAnimation ? 2 : 1);
        double[][][] finalList = new double[EquationList.length/3][length][3];
        int exprNumber;
        for (int i=0; i<EquationList.length/3; i++) {
            for(int j=0; j<length; j++) {
                exprNumber = (i) * 3;
                Animation.addToCurrentDistance();
                for (int k = 0; k<3; k++) {
                    finalList[i][j][k] = BigDecimal.valueOf(new ExpressionBuilder(EquationList[exprNumber]).variable("x").build().setVariable("x", Animation.getCurrentDistance()).evaluate()).setScale(5, RoundingMode.UP).doubleValue();
                    exprNumber++;
                }
            }
        }
        return finalList;
    }
}


