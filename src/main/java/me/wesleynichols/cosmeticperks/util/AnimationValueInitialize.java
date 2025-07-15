package me.wesleynichols.cosmeticperks.util;

import me.wesleynichols.cosmeticperks.CosmeticPerks;
import me.wesleynichols.cosmeticperks.managers.AnimationValueManager;
import me.wesleynichols.cosmeticperks.structures.AnimationValues;
import me.wesleynichols.cosmeticperks.structures.Animations;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AnimationValueInitialize {
    private final CosmeticPerks plugin;
    private final String name;
    private final Animations[] animations;
    private final double[][] styleValues;

    public AnimationValueInitialize(CosmeticPerks plugin, String name, double[][] styleValues, Animations[] animations) {
        this.plugin = plugin;
        this.name = name;
        this.animations = animations;
        this.styleValues = styleValues;
        initialize();
    }

    public AnimationValueInitialize(CosmeticPerks plugin, String name, double[][] styleValues) {
        this(plugin, name, styleValues, null);
    }

    public AnimationValueInitialize(CosmeticPerks plugin, String name, Animations[] animations) {
        this(plugin, name, new double[][]{}, animations);
    }

    private void initialize() {
        plugin.getAnimationValueManager().addAnimationValues(name, new AnimationValues(styleValues, generateEquationValueList()));
    }

    private double[][][] generateEquationValueList() {
        if (animations == null) return new double[][][]{};

        List<double[][]> storedEValues = new ArrayList<>();
        for (Animations animation : animations) {
            String[] equationList = animation.getEquationList();
            double[] offset = animation.getOffset();
            double[] angleOffset = animation.getAngleOffset();
            int length = animation.getTickToComplete() * (animation.isReversingAnimation() ? 2 : 1);

            for (int i = 0; i < equationList.length / 3; i++) {
                double[][] temp = new double[length][3];
                int exprNumber = i * 3;

                for (int j = 0; j < length; j++) {
                    animation.addToCurrentDistance();
                    for (int k = 0; k < 3; k++) {
                        temp[j][k] = BigDecimal.valueOf(
                                        new ExpressionBuilder(equationList[exprNumber + k])
                                                .variable("x")
                                                .build()
                                                .setVariable("x", animation.getCurrentDistance())
                                                .evaluate() + offset[k])
                                .setScale(5, RoundingMode.UP)
                                .doubleValue();
                    }
                    temp[j] = VectorUtils.rotateVector(temp[j], angleOffset[0], angleOffset[1]);
                }

                storedEValues.add(temp);
            }
        }

        return storedEValues.toArray(new double[0][][]);
    }
}
