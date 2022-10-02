package cosmetic.perks.cosmeticperks.util;

import cosmetic.perks.cosmeticperks.managers.AnimationValueManager;
import cosmetic.perks.cosmeticperks.structures.Animations;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AnimationValueInitialize {
    private final String Name;
    private final String[] EquationList;
    private final int TicksToComplete;
    private final boolean ReversingAnimation;
    private Animations Animation;

    public AnimationValueInitialize(String name, Animations animation) {
        this.Name = name;
        this.EquationList = animation.getEquationList();
        this.TicksToComplete = animation.getTickToComplete();
        this.ReversingAnimation = animation.isReversingAnimation();
        this.Animation = animation;
        initialize();
    }
    public void initialize() {
        AnimationValueManager.addParticleAnimation(Name, generateEquationValueList());
    }

    private double[][][] generateEquationValueList() {
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


