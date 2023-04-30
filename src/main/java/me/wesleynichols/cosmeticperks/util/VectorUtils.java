package me.wesleynichols.cosmeticperks.util;

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
        x = initialX * cosPitch;
        y = initialX * sinPitch + initialY;

        // Y_Axis rotation (Yaw)
        initialZ = v[2];
        initialX = x;
        z = initialZ * cosYaw - initialX * sinYaw;
        x = initialZ * sinYaw + initialX * cosYaw;

        return new double[]{x,y,z};
    }
}
