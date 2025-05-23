/* ======================================================================== */
/* Copyright (c) 2025 Steel Hawks Robotics Inc. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */
/* ======================================================================== */

package org.steelhawks.lib.util;

import org.steelhawks.lib.math.Angle;
import org.steelhawks.lib.math.Vector2d;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Utility class for transforming field coordinates and rotations based on the current alliance
 * (Blue or Red).
 *
 * <p>Coordinates are flipped across the field centerline when on the Red alliance, allowing code to
 * work symmetrically for both sides.
 */
public class AllianceUtil {

    /** Total field length in meters, loaded from the default AprilTag field layout. */
    private static final double FIELD_LENGTH =
            AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField).getFieldLength();

    /** Total field width in meters, loaded from the default AprilTag field layout. */
    private static final double FIELD_WIDTH =
            AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField).getFieldWidth();

    /**
     * Apply X-coordinate transformation: if on Red alliance, flip X around mid-field; otherwise
     * return original X.
     *
     * @param x the original X coordinate
     * @return the transformed X coordinate
     */
    public static double applyX(double x) {
        return shouldFlip() ? FIELD_LENGTH - x : x;
    }

    /**
     * Apply Y-coordinate transformation: if on Red alliance, flip Y around mid-field; otherwise
     * return original Y.
     *
     * @param y the original Y coordinate
     * @return the transformed Y coordinate
     */
    public static double applyY(double y) {
        return shouldFlip() ? FIELD_WIDTH - y : y;
    }

    /**
     * Transform a Translation2d by flipping its X/Y if needed.
     *
     * @param translation the original translation
     * @return a new Translation2d with potentially flipped coordinates
     */
    public static Translation2d apply(Translation2d translation) {
        return new Translation2d(applyX(translation.getX()), applyY(translation.getY()));
    }

    /**
     * Transform a Vector2d by converting to Translation2d, applying alliance flip, and converting
     * back.
     *
     * @param vector the original Vector2d
     * @return a new Vector2d with potentially flipped coordinates
     */
    public static Vector2d apply(Vector2d vector) {
        return new Vector2d(apply(vector.toTranslation2d()));
    }

    /**
     * Transform a Rotation2d by rotating 180 degrees if on Red alliance.
     *
     * @param rotation the original rotation
     * @return the transformed rotation
     */
    public static Rotation2d apply(Rotation2d rotation) {
        return shouldFlip() ? rotation.rotateBy(Rotation2d.kPi) : rotation;
    }

    /**
     * Transform an Angle by applying a Rotation2d flip if needed.
     *
     * @param angle the original Angle
     * @return a new Angle with potentially flipped orientation
     */
    public static Angle apply(Angle angle) {
        return new Angle(apply(angle.toRotation2d()));
    }

    /**
     * Transform a Pose2d by flipping its position and orientation if on Red alliance.
     *
     * @param pose the original pose
     * @return a new Pose2d with potentially flipped translation and rotation
     */
    public static Pose2d apply(Pose2d pose) {
        return shouldFlip()
                ? new Pose2d(apply(pose.getTranslation()), apply(pose.getRotation()))
                : pose;
    }

    /**
     * Transform a 3D translation by flipping X/Y axes but preserving Z.
     *
     * @param translation the original 3D translation
     * @return a new Translation3d with potentially flipped X/Y and original Z
     */
    public static Translation3d apply(Translation3d translation) {
        return new Translation3d(
                applyX(translation.getX()), applyY(translation.getY()), translation.getZ());
    }

    /**
     * Transform a 3D rotation by rotating 180 degrees around Z if on Red alliance.
     *
     * @param rotation the original 3D rotation
     * @return the transformed Rotation3d
     */
    public static Rotation3d apply(Rotation3d rotation) {
        return shouldFlip() ? rotation.rotateBy(new Rotation3d(0.0, 0.0, Math.PI)) : rotation;
    }

    /**
     * Transform a 3D pose by applying both translation and rotation flips if needed.
     *
     * @param pose the original 3D pose
     * @return a new Pose3d with potentially flipped translation and rotation
     */
    public static Pose3d apply(Pose3d pose) {
        return new Pose3d(apply(pose.getTranslation()), apply(pose.getRotation()));
    }

    /**
     * Determine whether coordinates should be flipped based on current alliance.
     *
     * <p>Returns true only when the DriverStation alliance is present and equals Red.
     *
     * @return true if on Red alliance; false otherwise
     */
    public static boolean shouldFlip() {
        return DriverStation.getAlliance().isPresent()
                && DriverStation.getAlliance().get() == DriverStation.Alliance.Red;
    }
}
