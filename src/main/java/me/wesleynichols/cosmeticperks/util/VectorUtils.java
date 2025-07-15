package me.wesleynichols.cosmeticperks.util;

public class VectorUtils {

    public static double[] rotateAroundAxisX(double[] v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double y = v[1] * cos - v[2] * sin;
        double z = v[1] * sin + v[2] * cos;
        return new double[]{v[0], y, z};
    }

    public static double[] rotateVector(double[] v, double yawDegrees, double pitchDegrees) {
        double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = Math.cos(yaw);
        double sinYaw = Math.sin(yaw);
        double cosPitch = Math.cos(pitch);
        double sinPitch = Math.sin(pitch);

        // Pitch rotation around Z axis
        double x1 = v[0] * cosPitch;
        double y1 = v[0] * sinPitch + v[1];

        // Yaw rotation around Y axis
        double z1 = v[2] * cosYaw - x1 * sinYaw;
        double x2 = v[2] * sinYaw + x1 * cosYaw;

        return new double[]{x2, y1, z1};
    }
}
