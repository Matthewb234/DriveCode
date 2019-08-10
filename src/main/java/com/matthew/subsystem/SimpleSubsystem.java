package com.matthew.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimpleSubsystem extends Subsystem {

    private double output = 0;
    private TalonSRX myTalon = new TalonSRX(0);
    private TalonSRX theTalon = new TalonSRX(0);
    private Joystick controller;

    private boolean pulseActive = false;
    private boolean reverseActive = false;
    private boolean cruiseActive = false;
    private boolean cruiseOverrideActive = false;
    private boolean deadzoneActive = false;
    private boolean cutSpeedActive = false;
    private boolean autoMoveActive = false;
    private boolean tickReverseActive = false;
    private double pastTime;
    private double pastTime1;
    private double time1;
    private boolean enabled = false;
    private boolean hiEnabled = false;
    private double cruiseOutput;
    private boolean cruiseControlEnabled = false;
    private boolean lTriggerPrev = false;
    private boolean rTriggerPrev = false;
    private boolean tickReverse = true;
    private double tickSet;
    private int tickNumber;
    private int ticks;
    private boolean lTrigger;
    private double differnce;
    private double finalDifference;
    private boolean isItOrIsItNot;
    private boolean previousIsItOrIsItNot;

    public SimpleSubsystem(Joystick controller) {
        this.controller = controller;
    }


    @Override
    public void periodic() {
        output = controller.getRawAxis(1);
        tickNumber = myTalon.getSelectedSensorPosition(4);
        differnce = tickNumber - tickSet;
        finalDifference = differnce / 75000;
        lTrigger = controller.getRawButton(5);
        if (output >= -.1 && output <= .1) {
            output /= 5;
        }
        if (pulseActive)
            output = applyPulse(output);
        if (reverseActive)
            output = applyReverse(output);
        if (cruiseActive) {
            if (controller.getRawButton(4)) {
                cruiseOutput = output;
            }
            output = applyCruise(output);
        }
        if (cruiseOverrideActive)
            output = applyCruiseOverride(output);
        if (deadzoneActive)
            output = applyDeadzone(output);
        if (cutSpeedActive)
            output = applyCutSpeed(output);
        if (autoMoveActive)
            output = applyAutoMove(output);
        if (tickReverseActive)
            output = applyTickReverse(output);
        output = applyVariablyDecrease(output);
        output = applyVariablyIncrease(output);
        myTalon.set(ControlMode.PercentOutput, output);
        theTalon.set(ControlMode.PercentOutput, output);
        updateSmartDashboard();
    }

    private void updateSmartDashboard() {
        SmartDashboard.putNumber("pov", controller.getPOV());
        SmartDashboard.putNumber("output", output);
        SmartDashboard.putNumber("pastTime", pastTime);
        SmartDashboard.putNumber("cruiseOutput", cruiseOutput);
        SmartDashboard.putBoolean("enabled", enabled);
        SmartDashboard.putBoolean("cruiseControlEnabled", cruiseControlEnabled);
        SmartDashboard.putBoolean("lTriggerPrev", lTriggerPrev);
        SmartDashboard.putBoolean("lTrigger", lTrigger);
//        SmartDashboard.putNumber("time", time);
        SmartDashboard.putNumber("ticknumber", tickNumber);
        SmartDashboard.putNumber("ticks", ticks);
        SmartDashboard.putNumber("tickSet", tickSet);
        SmartDashboard.putNumber("differnce", differnce);
        SmartDashboard.putNumber("finalDifference", finalDifference);
        SmartDashboard.putBoolean("deadzoneActive", deadzoneActive);
        SmartDashboard.putBoolean("tickReverseActive", tickReverseActive);
        SmartDashboard.putBoolean("autoMoveActive", autoMoveActive);
        SmartDashboard.putBoolean("cruiseActive", cruiseActive);
    }

    @Override
    protected void initDefaultCommand() {

    }


    private double applyPulse(double currentOutput) {
        double time = System.currentTimeMillis();
        double adjustedOutput = currentOutput;
        if (time - pastTime > 1000) {
            enabled = !enabled;// flip the value of enabled
            // Set a new pastTime
            pastTime = time;
        }
        if (enabled) {
        }
        if (!enabled) {
            adjustedOutput = 0;
        }
        return adjustedOutput;
    }

    private double applyReverse(double currentOutput) {
        double adjustedOutput = currentOutput;
        adjustedOutput = -adjustedOutput;
        return adjustedOutput;
    }

    private double applyCruise(double currentOutput) {
        double adjustedOutput = currentOutput;
        if (lTrigger && !lTriggerPrev) {
            if (cruiseControlEnabled)
                cruiseControlEnabled = false;
            else {
                cruiseControlEnabled = true;
            }
        }


        if (lTrigger && lTriggerPrev) {
            System.out.println("Y button released");
        }


        if (cruiseControlEnabled) {
            adjustedOutput = cruiseOutput;
        }
        return adjustedOutput;

    }

    private double applyCruiseOverride(double currentOutput) {
        double adjustedOutput = currentOutput;
        double controllerOutput = controller.getRawAxis(1);

        if (cruiseControlEnabled && Math.abs(cruiseOutput) < Math.abs(controllerOutput)) {
            if (Math.abs(cruiseOutput) < .01) {
                cruiseControlEnabled = false;
            } else if (cruiseOutput > 0 && cruiseOutput < controllerOutput)
                adjustedOutput = controllerOutput;
            else if (cruiseOutput < 0 && cruiseOutput > controllerOutput)
                adjustedOutput = controllerOutput;
        }
        return adjustedOutput;

    }

    private double applyDeadzone(double currentOutput) {
        double adjustedOutput = currentOutput;
        if (adjustedOutput < .25 && adjustedOutput > 0 || adjustedOutput > -.25 && adjustedOutput < 0) {
            adjustedOutput = 0;
        }
        return adjustedOutput;
    }

    private double applyCutSpeed(double currentOutput) {
        double adjustedOutput = currentOutput;
        adjustedOutput /= 2;
        return adjustedOutput;
    }

    private double applyAutoMove(double currentOutput) {
        double adjustedOutput = currentOutput;
        autoMoveActive = true;
        if (tickNumber >= 20000) {
            adjustedOutput = 0;
            autoMoveActive = false;
        } else if (autoMoveActive) {
            adjustedOutput = -.25;
        }
        return adjustedOutput;
    }

    private double applyTickReverse(double currentOutput) {
        double adjustedOutput = currentOutput;
        if (!areJoysticksMoved() && !areButtonsPressed()) {
//            if (tickNumber < -10000 + tickSet) {
//                System.out.println("One");
//                adjustedOutput = -.5;
//            } else if (tickNumber > 10000 + tickSet) {
//                System.out.println("Two");
//                adjustedOutput = .5;
//            } else if (tickNumber < -1000 + tickSet) {
//                System.out.println("Three");
//                adjustedOutput = -.1;
//            } else if (tickNumber > 1000 + tickSet) {
//                System.out.println("Four");a
//                adjustedOutput = .1;
//            }

//        rT
// riggerPrev = rTrigger;
            if (finalDifference >= 0) {
                adjustedOutput = Math.min(.5, finalDifference);
            } else {
                adjustedOutput = Math.max(-.5, finalDifference);
            }
        }
        return adjustedOutput;
    }

    private double applyVariablyIncrease(double currentOutput) {
        double adjustedOutput = currentOutput;
        if (currentOutput > 0) {
            adjustedOutput = currentOutput + controller.getRawAxis(3);
        } else {
            adjustedOutput = currentOutput - controller.getRawAxis(3);
        }
        return adjustedOutput;
    }

    private double applyVariablyDecrease(double currentOutput) {
        double adjustedOutput = currentOutput;
        if (currentOutput < 0) {
            adjustedOutput = currentOutput - controller.getRawAxis(2);
        } else {
            adjustedOutput = currentOutput + controller.getRawAxis(2);
        }
        return adjustedOutput;
    }

    private boolean areJoysticksMoved() {
        return controller.getRawAxis(1) >= .01 || controller.getRawAxis(1) <= -.01;
    }

    private boolean areButtonsPressed() {
        return controller.getRawButton(1) || controller.getRawButton(2) || controller.getRawButton(3) ||
                controller.getRawButton(4);
    }

    public void setPulseActive(boolean pulseActive) {
        this.pulseActive = pulseActive;
    }

    public void setReverseActive(boolean reverseActive) {
        this.reverseActive = reverseActive;
    }

    public void setCruiseActive(boolean cruiseActive) {
        this.cruiseActive = cruiseActive;

    }

    public void setCruiseOverrideActive(boolean cruiseOverrideActive) {
        this.cruiseOverrideActive = cruiseOverrideActive;
    }

    public void setDeadzoneActive(boolean deadzoneActive) {
        this.deadzoneActive = deadzoneActive;
    }

    public void setCutSpeedActive(boolean cutSpeedActive) {
        this.cutSpeedActive = cutSpeedActive;
    }

    public void setAutoMoveActive(boolean autoMoveActive) {
        this.autoMoveActive = autoMoveActive;
    }

    public void setTickReverseActive(boolean tickReverseActive) {
        this.tickReverseActive = tickReverseActive;
    }

    public void resetTickNumber() {
        this.tickSet = tickNumber;
    }
}

