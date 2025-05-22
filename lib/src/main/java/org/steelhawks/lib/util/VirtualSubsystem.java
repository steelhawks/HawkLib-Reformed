/* ======================================================================== */
/* Copyright (c) 2025 Steel Hawks Robotics Inc. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */
/* ======================================================================== */

package org.steelhawks.lib.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create virtual subsystems to be able to access a periodic function. Should
 * be used for non-physical subsystems that need to run a periodic function. This is separate from
 * the normal SubsystemBase class, meaning you do not clog up the CommandScheduler.
 *
 * <p>For this to work, make sure periodicAll() is being called in robotPeriodic()
 *
 * @see #periodicAll()
 * @author Farhan Jamil
 */
public abstract class VirtualSubsystem {

    private static final List<VirtualSubsystem> mSubsystems = new ArrayList<>();
    private final String subsystemName;
    private static long lastPrintTime = 0; // Last time stats were printed
    private static long lastOverrunTime = 0; // Last time an overrun was logged

    /**
     * Periodic method that will be called repeatedly for this subsystem. Override this method to
     * define the subsystem's behavior.
     */
    public abstract void periodic();

    /**
     * Creates a new VirtualSubsystem with the class name as the subsystem name. The subsystem is
     * automatically registered for periodic updates.
     */
    public VirtualSubsystem() {
        mSubsystems.add(this);
        subsystemName = getClass().getSimpleName();
    }

    /**
     * Creates a new VirtualSubsystem with the specified name. The subsystem is automatically
     * registered for periodic updates.
     *
     * @param name The name of this subsystem, used for logging
     */
    public VirtualSubsystem(String name) {
        mSubsystems.add(this);
        subsystemName = name;
    }

    /**
     * Calls the periodic method for all registered VirtualSubsystems. This method also monitors and
     * logs execution time for each subsystem, and reports warnings if any subsystem takes too long
     * to execute.
     */
    public static void periodicAll() {
        long currentTime = RobotController.getFPGATime();
        boolean shouldPrint = (currentTime - lastPrintTime) >= 10_000_000; // 10 sec
        boolean shouldPrintOverrun = (currentTime - lastOverrunTime) >= 5_000_000; // 5 sec

        StringBuilder logMessage = new StringBuilder("VirtualSubsystem Loop Times:\n");
        StringBuilder overrunMessage =
                new StringBuilder("WARNING: The following subsystems exceeded 20ms:\n");

        boolean overrunOccurred = false;

        for (VirtualSubsystem subsystem : mSubsystems) {
            long startTime = RobotController.getFPGATime();
            subsystem.periodic();
            long endTime = RobotController.getFPGATime();

            double loopTimeMs = (endTime - startTime) / 1_000.0; // convert to ms

            if (loopTimeMs > 20) {
                overrunOccurred = true;
                overrunMessage.append(
                        String.format("  - %s: %.3f ms%n", subsystem.subsystemName, loopTimeMs));
            }

            if (shouldPrint) {
                logMessage.append(
                        String.format("  - %s: %.3f ms%n", subsystem.subsystemName, loopTimeMs));
            }
        }

        if (overrunOccurred && shouldPrintOverrun) {
            DriverStation.reportError(overrunMessage.toString(), false);
            System.out.println("VirtualSubsystem Loop Overrun");
            lastOverrunTime = currentTime;
        }

        if (shouldPrint) {
            DriverStation.reportWarning(logMessage.toString(), false);
            lastPrintTime = currentTime;
        }
    }
}
