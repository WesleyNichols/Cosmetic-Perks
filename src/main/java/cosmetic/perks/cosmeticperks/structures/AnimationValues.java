package cosmetic.perks.cosmeticperks.structures;

import cosmetic.perks.cosmeticperks.util.Particles;
import org.bukkit.Particle;

public class AnimationValues {

    private final Particles[] EquationValues;
    private final int EquationValuesLength;
    private final Particles[] StyleValues;
    private final int StyleValuesLength;
    private int CurrentStep;

    public AnimationValues(Particles[] styleValues, Particles[] equationValues) {
        this.EquationValues = equationValues;
        this.EquationValuesLength = equationValues.length;
        this.StyleValues = styleValues;
        this.StyleValuesLength = styleValues.length;
        this.CurrentStep = 0;
    }

    public Particles[] getEquationValues() {
        return EquationValues;
    }

    public int getEquationValuesLength() {
        return EquationValuesLength;
    }

    public int getStyleValuesLength() {
        return StyleValuesLength;
    }

    public Particles[] getStyleValues() {
        return StyleValues;
    }

    public int getCurrentStep() {
        return CurrentStep;
    }

    public void addStep() {
        CurrentStep++;
        if(CurrentStep >= EquationValues[0].getParticleValues().length) {
            CurrentStep = 0;
        }
    }
}
