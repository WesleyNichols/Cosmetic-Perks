package cosmetic.perks.cosmeticperks.structures;

public class AnimationValues {

    private final double[][][] EquationValues;
    private final int EquationValuesLength;
    private final double[][][][] EquationStyleValues;
    private final int EquationStyleValuesLength;
    private final double[][] StyleValues;
    private final int StyleValuesLength;
    private int CurrentStep;

    public AnimationValues(double[][] styleValues, double[][][] equationValues, double[][][][] equationStyleValues) {
        this.EquationValues = equationValues;
        this.EquationValuesLength = equationValues.length;
        this.EquationStyleValues = equationStyleValues;
        this.EquationStyleValuesLength = equationStyleValues.length;
        this.StyleValues = styleValues;
        this.StyleValuesLength = styleValues.length;
        this.CurrentStep = 0;
    }

    public double[][][] getEquationValues() {
        return EquationValues;
    }

    public int getEquationValuesLength() {
        return EquationValuesLength;
    }

    public double[][][][] getEquationStyleValues() {
        return EquationStyleValues;
    }

    public int getEquationStyleValuesLength() {
        return EquationStyleValuesLength;
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
        if(EquationValues.length != 0) {
            if(CurrentStep >= EquationValues[0].length) {
                CurrentStep = 0;
            }
        } else if (CurrentStep >= EquationStyleValues[0].length) {
            CurrentStep = 0;
        }
    }
}
