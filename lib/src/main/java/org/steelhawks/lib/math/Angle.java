/* ======================================================================== */
/* Copyright (c) 2025 Steel Hawks Robotics Inc. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */
/* ======================================================================== */

package org.steelhawks.lib.math;

import edu.wpi.first.math.geometry.Rotation2d;

/**
 * A class representing an angle in radians, with methods for conversion and normalization. This
 * class provides utility methods for working with angles in various formats, including degrees and
 * rotations.
 *
 * @author Farhan Jamil
 */
public class Angle {

    private static final double TO_DEGREES = 180.0 / Math.PI;
    private static final double TO_RADIANS = Math.PI / 180.0;
    private static final double TO_ROTATIONS = 1.0 / (2 * Math.PI);

    public static final double kPi = Math.PI;
    public static final double kTwoPi = 2 * Math.PI;
    public static final double kHalfPi = Math.PI / 2;
    public static final double kQuarterPi = Math.PI / 4;

    public final double radians;
    public final double cos;
    public final double sin;

    /**
     * Creates a new Angle object with the specified angle in radians.
     *
     * @param rotation The Rotation2d object to convert to an Angle object.
     */
    public Angle(Rotation2d rotation) {
        this.radians = rotation.getRadians();
        this.cos = rotation.getCos();
        this.sin = rotation.getSin();
    }

    /**
     * Creates a new Angle object with the specified angle in radians.
     *
     * @param radians The angle in radians.
     */
    public Angle(double radians) {
        this.radians = radians;
        this.cos = Math.cos(radians);
        this.sin = Math.sin(radians);
    }

    /**
     * Creates a new Angle object with the specified angle in degrees.
     *
     * @param degrees The angle in degrees.
     */
    public static Angle fromDegrees(double degrees) {
        return new Angle(degrees * TO_RADIANS);
    }

    /**
     * Creates a new Angle object with the specified angle in rotations.
     *
     * @param rotations The angle in rotations.
     */
    public static Angle fromRotations(double rotations) {
        return new Angle(rotations * kTwoPi);
    }

    /**
     * Creates a new Angle object from a vector represented by its x and y components.
     *
     * @param x The x component of the vector.
     * @param y The y component of the vector.
     * @return A new Angle object representing the angle of the vector.
     */
    public static Angle fromVector(double x, double y) {
        return new Angle(Math.atan2(y, x));
    }

    /**
     * Creates a new Angle object from a vector.
     *
     * @param vector The Vector2d object representing the vector.
     * @return A new Angle object representing the angle of the vector.
     */
    public static Angle fromVector(Vector2d vector) {
        return fromVector(vector.x, vector.y);
    }

    /**
     * Creates a new Angle object from a slope.
     *
     * @param slope The slope of the line.
     * @return A new Angle object representing the angle of the slope.
     */
    public static Angle fromSlope(double slope) {
        return new Angle(Math.atan(slope));
    }

    public static Angle fromRotation2d(Rotation2d rotation) {
        return new Angle(rotation.getRadians());
    }

    /**
     * Converts the angle to degrees.
     *
     * @return The angle in degrees.
     */
    public double toDegrees() {
        return radians * TO_DEGREES;
    }

    /**
     * Normalize an angle to the range of -pi to pi.
     *
     * @param angle The angle in radians.
     * @return The normalized angle in radians.
     */
    public static double normalize(double angle) {
        return (angle + Math.PI) % (2 * Math.PI) - Math.PI;
    }

    /**
     * Converts from Angle to Rotation2d
     *
     * @param angle The Angle object to convert
     * @return A new Rotation2d object representing the angle
     */
    public static Rotation2d toRotation2d(Angle angle) {
        return new Rotation2d(angle.radians);
    }

    /**
     * Converts from Angle to Rotation2d
     *
     * @return A new Rotation2d object representing the angle
     */
    public Rotation2d toRotation2d() {
        return Angle.toRotation2d(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Angle angle = (Angle) obj;
        return Double.compare(angle.radians, radians) == 0;
    }
}
