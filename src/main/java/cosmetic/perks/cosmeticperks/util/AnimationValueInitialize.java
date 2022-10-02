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
    private final Particles[] StyleValues;

    public AnimationValueInitialize(String name, Particles[] styleValues, Animations animation) {
        this.Name = name;
        this.Animation = animation;
        this.StyleValues = styleValues;
        this.EquationList = animation.getEquationList();
        this.TicksToComplete = animation.getTickToComplete();
        this.ReversingAnimation = animation.isReversingAnimation();
        initialize();
    }

    public AnimationValueInitialize(String name, Particles[] styleValues) {
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
        this.StyleValues = new Particles[]{};
        this.EquationList = animation.getEquationList();
        this.TicksToComplete = animation.getTickToComplete();
        this.ReversingAnimation = animation.isReversingAnimation();
        initialize();
    }

    public void initialize() {
        AnimationValueManager.addParticleAnimation(Name, new AnimationValues(StyleValues, generateEquationValueList()));
    }

    private Particles[] generateEquationValueList() {
        if(this.Animation == null) {return new Particles[]{};}
        int length = TicksToComplete * (ReversingAnimation ? 2 : 1);
        Particles[] finalList = new Particles[EquationList.length/3];
        int exprNumber;
        for (int i=0; i<EquationList.length/3; i++) {
            double[][] tempList = new double[length][3];
            for(int j=0; j<length; j++) {
                exprNumber = (i) * 3;
                Animation.addToCurrentDistance();
                for (int k = 0; k<3; k++) {
                    tempList[j][k] = BigDecimal.valueOf(new ExpressionBuilder(EquationList[exprNumber]).variable("x").build().setVariable("x", Animation.getCurrentDistance()).evaluate()).setScale(5, RoundingMode.UP).doubleValue();
                    exprNumber++;
                }
                finalList[i] = new Particles(Animation.getParticleEffect(), tempList);
            }
        }
        return finalList;
    }
}


