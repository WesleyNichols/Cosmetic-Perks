package cosmetic.perks.cosmeticperks.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public class VectorUtils {

    public static double[] rotateAroundAxisX(double[] v, double angle) {
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v[1] * cos - v[2] * sin;
        z = v[1] * sin + v[2] * cos;
        return new double[]{v[0], y, z};
    }

    public static double[] rotateVector(double[] v, double yawDegrees, double pitchDegrees) {
        double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = Math.cos(yaw);
        double cosPitch = Math.cos(pitch);
        double sinYaw = Math.sin(yaw);
        double sinPitch = Math.sin(pitch);

        double initialX, initialY, initialZ;
        double x, y, z;

        // Z_Axis rotation (Pitch)
        initialX = v[0];
        initialY = v[1];
        x = initialX * cosPitch - initialY * sinPitch;
        y = initialX * sinPitch + initialY * cosPitch;

        // Y_Axis rotation (Yaw)
        initialZ = v[2];
        initialX = x;
        z = initialZ * cosYaw - initialX * sinYaw;
        x = initialZ * sinYaw + initialX * cosYaw;

        return new double[]{x,y,z};
    }

    public static double magnitude(double[] v) {
        return Math.sqrt(Math.pow(v[0], 2) + Math.pow(v[1], 2) + Math.pow(v[2], 2));
    }
}
