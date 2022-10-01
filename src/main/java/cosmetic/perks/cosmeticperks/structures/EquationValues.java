package cosmetic.perks.cosmeticperks.structures;

import cosmetic.perks.cosmeticperks.managers.AnimationValueManager;

public class EquationValues {

    private final double[][][] EquationValue;
    private int CurrentStep;

    public EquationValues(String name) {
        this.EquationValue = AnimationValueManager.getEquationValues(name);
        this.CurrentStep = 0;
    }

    public double[][][] getEquationValues() {
        return EquationValue;
    }

    public int getCurrentStep() {
        return CurrentStep;
    }

    public void addStep() {
        CurrentStep++;
        if(CurrentStep >= EquationValue[0].length) {
            CurrentStep = 0;
        }
    }
}
