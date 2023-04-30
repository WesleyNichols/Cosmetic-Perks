package me.wesleynichols.cosmeticperks.structures;

public class AnimationValues {

    private final double[][][] EquationValues;
    private final int EquationValuesLength;
    private final double[][] StyleValues;
    private final int StyleValuesLength;
    private int CurrentStep = 0;

    public AnimationValues(double[][] styleValues, double[][][] equationValues) {
        this.EquationValues = equationValues;
        this.EquationValuesLength = equationValues.length;
        this.StyleValues = styleValues;
        this.StyleValuesLength = styleValues.length;
    }

    public double[][][] getEquationValues() {
        return EquationValues;
    }

    public int getEquationValuesLength() {
        return EquationValuesLength;
    }

    public int getStyleValuesLength() {
        return StyleValuesLength;
    }

    public double[][] getStyleValues() {
        return StyleValues;
    }

    public int getCurrentStep() {
        return CurrentStep;
    }

    public void addStep() {
        CurrentStep++;
        if(CurrentStep >= EquationValues[0].length) {
            CurrentStep = 0;
        }
    }
}
