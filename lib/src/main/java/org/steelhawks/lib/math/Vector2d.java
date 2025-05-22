/* ======================================================================== */
/* Copyright (c) 2025 Steel Hawks Robotics Inc. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */
/* ======================================================================== */

package org.steelhawks.lib.math;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

/**
 * A 2D vector class representing a point in 2D space. This class is used for various mathematical
 * operations and calculations.
 *
 * @author Farhan Jamil
 */
public class Vector2d {

    /** The origin point (0,0) in 2D space. */
    public static final Vector2d kOrigin = new Vector2d(0.0, 0.0);
    /** The unit vector in the X direction (1,0). */
    public static final Vector2d kNormX = new Vector2d(1.0, 0.0);
    /** The unit vector in the Y direction (0,1). */
    public static final Vector2d kNormY = new Vector2d(0.0, 1.0);

    /** X coordinate of the vector. */
    public final double x;
    /** Y coordinate of the vector. */
    public final double y;

    /** Creates a new Vector2d object with the specified x and y coordinates of (0, 0). */
    public Vector2d() {
        this(0.0, 0.0);
    }

    /**
     * Creates a new Vector2d object with the specified Translation2d object.
     *
     * @param translation The Translation2d object representing the vector.
     */
    public Vector2d(Translation2d translation) {
        this(translation.getX(), translation.getY());
    }

    /**
     * Creates a new Vector2d object with the specified x and y coordinates.
     *
     * @param x The x coordinate of the vector.
     * @param y The y coordinate of the vector.
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds another Vector2d object to this Vector2d object.
     *
     * @param other The other Vector2d object to add.
     * @return A new Vector2d object representing the sum of the two vectors.
     */
    public Vector2d add(Vector2d other) {
        return add(other.x, other.y);
    }

    /**
     * Adds the specified x and y coordinates to this Vector2d object.
     *
     * @param x The x coordinate to add.
     * @param y The y coordinate to add.
     * @return A new Vector2d object representing the sum of the two vectors.
     */
    public Vector2d add(double x, double y) {
        return new Vector2d(this.x + x, this.y + y);
    }

    /**
     * Subtracts another Vector2d object from this Vector2d object.
     *
     * @param other The other Vector2d object to subtract.
     * @return A new Vector2d object representing the difference of the two vectors.
     */
    public Vector2d subtract(Vector2d other) {
        return subtract(other.x, other.y);
    }

    /**
     * Subtracts the specified x and y coordinates from this Vector2d object.
     *
     * @param x The x coordinate to subtract.
     * @param y The y coordinate to subtract.
     * @return A new Vector2d object representing the difference of the two vectors.
     */
    public Vector2d subtract(double x, double y) {
        return new Vector2d(this.x - x, this.y - y);
    }

    /**
     * Multiplies this Vector2d object by a scalar value.
     *
     * @param scalar The scalar value to multiply by.
     * @return A new Vector2d object representing the scaled vector.
     */
    public Vector2d multiply(double scalar) {
        return new Vector2d(this.x * scalar, this.y * scalar);
    }

    /**
     * Multiplies this Vector2d object by another Vector2d object.
     *
     * @param other The other Vector2d object to multiply by.
     * @return A new Vector2d object representing the scaled vector.
     */
    public Vector2d multiply(Vector2d other) {
        return new Vector2d(this.x * other.x, this.y * other.y);
    }

    /**
     * Divides this Vector2d object by a scalar value.
     *
     * @param scalar The scalar value to divide by.
     * @return A new Vector2d object representing the scaled vector.
     */
    public Vector2d divide(double scalar) {
        return new Vector2d(this.x / scalar, this.y / scalar);
    }

    /**
     * Divides this Vector2d object by another Vector2d object.
     *
     * @param other The other Vector2d object to divide by.
     * @return A new Vector2d object representing the scaled vector.
     */
    public Vector2d divide(Vector2d other) {
        return new Vector2d(this.x / other.x, this.y / other.y);
    }

    /**
     * Raises this Vector2d object to the power of the specified exponent.
     *
     * @param exponent The exponent to raise the vector to.
     * @return A new Vector2d object representing the vector raised to the power of the exponent.
     */
    public Vector2d pow(double exponent) {
        return multiply(Math.pow(getMagnitude(), exponent));
    }

    /**
     * Calculates the dot product of this Vector2d object with another Vector2d object.
     *
     * @param other The other Vector2d object to calculate the dot product with.
     * @return The dot product of the two vectors.
     */
    public double dotProduct(Vector2d other) {
        return (this.x * other.x) + (this.y * other.y);
    }

    /**
     * Calculates the cross product of this Vector2d object with another Vector2d object.
     *
     * @param other The other Vector2d object to calculate the cross product with.
     * @return The cross product of the two vectors.
     */
    public double crossProduct(Vector2d other) {
        return (this.x * other.y) - (this.y * other.x);
    }

    /**
     * Rotates this Vector2d object by the specified angle in radians.
     *
     * @param angle The angle in radians to rotate the vector.
     * @return A new Vector2d object representing the rotated vector.
     */
    public Vector2d rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector2d((x * cos) - (y * sin), (x * sin) + (y * cos));
    }

    /**
     * Rotates this Vector2d object around the specified origin by the specified angle in radians.
     *
     * @param angle The angle in radians to rotate the vector.
     * @param origin The origin point to rotate around.
     * @return A new Vector2d object representing the rotated vector.
     */
    public Vector2d rotateAround(Rotation2d angle, Vector2d origin) {
        double cos = angle.getCos();
        double sin = angle.getSin();
        return new Vector2d(
                (x - origin.x) * cos - (y - origin.y) * sin + origin.x,
                (x - origin.x) * sin + (y - origin.y) * cos + origin.y);
    }

    /**
     * Normalizes this Vector2d object to a unit vector.
     *
     * @return A new Vector2d object representing the normalized vector.
     */
    public Vector2d normalized() {
        final double magnitude = getMagnitude();
        if (magnitude <= 1e-9) {
            return Vector2d.kNormX;
        } else {
            return divide(magnitude);
        }
    }

    /**
     * Negates the specified Vector2d object.
     *
     * @param vector The Vector2d object to negate.
     * @return A new Vector2d object representing the negated vector.
     */
    public static Vector2d negate(Vector2d vector) {
        return new Vector2d(-vector.x, -vector.y);
    }

    /**
     * Negates this Vector2d object.
     *
     * @return A new Vector2d object representing the negated vector.
     */
    public Vector2d negate() {
        return Vector2d.negate(this);
    }

    /**
     * Gets the distance of the Vector2d
     *
     * @return The distance from this Vector2d object to the origin.
     */
    public double getDistance() {
        return Math.hypot(x, y);
    }

    /**
     * Gets the magnitude of this Vector2d
     *
     * @return The magnitude of the vector
     */
    public double getMagnitude() {
        return getDistance();
    }

    /**
     * Creates a new Vector2d object with the specified x and y coordinates.
     *
     * @param vector The Vector2d object representing the vector.
     */
    /**
     * Converts the specified Vector2d object to a Translation2d object.
     *
     * @param vector The Vector2d object to convert
     * @return A Translation2d object representing the same point in 2D space
     */
    public static Translation2d toTranslation2d(Vector2d vector) {
        return new Translation2d(vector.x, vector.y);
    }

    /**
     * Converts this Vector2d object to a Translation2d object.
     *
     * @return A Translation2d object representing the same point in 2D space.
     */
    public Translation2d toTranslation2d() {
        return Vector2d.toTranslation2d(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vector2d vector2d = (Vector2d) obj;
        return Double.compare(vector2d.x, x) == 0 && Double.compare(vector2d.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }
}
