/* ======================================================================== */
/* Copyright (c) 2025 Steel Hawks Robotics Inc. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */
/* ======================================================================== */

package org.steelhawks.lib.math;

import edu.wpi.first.math.geometry.Translation2d;

public final class HawkMath {

    private HawkMath() {}

    /**
     * Returns the base raised to the exponent power without affecting the sign of the base.
     *
     * @param base The number to get the absolute value of.
     * @param exponent The exponent to raise the base to.
     * @return The result of raising the base to the exponent power, preserving the sign of the
     *     base.
     */
    public static double copyPow(double base, double exponent) {
        return Math.pow(base, exponent) * Math.signum(base);
    }

    /**
     * @param angle angle in degrees
     * @return angle in the range of 0 to 360
     */
    public static double continuous180To360(double angle) {
        return (angle + 360) % 360;
    }

    /**
     * @param angle angle in radians
     * @return returns a continuous angle from 0-2pi to an angle that is -pi to pi
     */
    public static double convert360To180Rad(double angle) {
        return (angle + Math.PI) % (2 * Math.PI) - Math.PI;
    }

    public static double convert360To180(double angle) {
        return (angle + 180) % 360 - 180;
    }

    /**
     * @param wheelRPS Wheel Velocity: (in Rotations per Second)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Velocity: (in Meters per Second)
     */
    public static double RPSToMPS(double wheelRPS, double circumference) {
        return wheelRPS * circumference;
    }

    /**
     * @param wheelMPS Wheel Velocity: (in Meters per Second)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Velocity: (in Rotations per Second)
     */
    public static double MPSToRPS(double wheelMPS, double circumference) {
        return wheelMPS / circumference;
    }

    /**
     * @param wheelRotations Wheel Position: (in Rotations)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Distance: (in Meters)
     */
    public static double rotationsToMeters(double wheelRotations, double circumference) {
        return wheelRotations * circumference;
    }

    /**
     * @param wheelMeters Wheel Distance: (in Meters)
     * @param circumference Wheel Circumference: (in Meters)
     * @return Wheel Position: (in Rotations)
     */
    public static double metersToRotations(double wheelMeters, double circumference) {
        return wheelMeters / circumference;
    }

    /**
     * Converts a Pose2d array to a Translation2d array.
     *
     * @param vectors The Pose2d array to convert.
     * @return The converted Translation2d array.
     */
    public static Translation2d[] toTranslation2dArray(Vector2d[] vectors) {
        Translation2d[] translation = new Translation2d[vectors.length];
        for (int i = 0; i < vectors.length; i++) {
            translation[i] = new Translation2d(vectors[i].x, vectors[i].y);
        }
        return translation;
    }
}
