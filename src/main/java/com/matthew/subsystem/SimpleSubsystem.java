package com.matthew.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SimpleSubsystem extends Subsystem {

    private double output = 0;

    private boolean pulseActive = false;
    private boolean reverseActive = false;
    private boolean cruiseActive = false;
    private boolean cruiseOverrideActive = false;
    private boolean deadzoneActive = false;
    private boolean cutSpeedActive = false;
    private boolean autoMoveActive = false;
    private boolean tickReverseActive = false;
    private boolean areButtonsPressedActive = false;
    private double pastTime = System.currentTimeMillis();
    private boolean enabled = false;
    private double cruiseOutput;
    private boolean cruiseControlEnabled = false;
    private boolean yButtonPrev = false;
    private boolean rTriggerPrev = false;
    private boolean tickReverse = true;
    private double tickSet;


    private TalonSRX myTalon = new TalonSRX(1);
    private TalonSRX theTalon = new TalonSRX(0);
    private Joystick controller;

    public SimpleSubsystem(Joystick controller) {
        this.controller = controller;
    }

    @Override
    public void periodic() {
        output = controller.getRawAxis(1);
        output = applyPulse(output);
        output = applyReverse(output);
        output = applyCruise(output);
        output = applyCruiseOverride(output);
        output = applyDeadzone(output);
        output = applyCutSpeed(output);
        output = applyAutoMove(output);
        output = applyTickReverse(output);

        myTalon.set(ControlMode.PercentOutput, output * .3);
        theTalon.set(ControlMode.PercentOutput, output * .3);

        SmartDashboard.putNumber("output", output);
        SmartDashboard.putNumber("pastTime", pastTime);

    }

    @Override
    protected void initDefaultCommand() {

    }


    private double applyPulse(double currentOutput) {
        double time = System.currentTimeMillis();
        double adjustedOutput = currentOutput;
        if (pulseActive) {
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
        }
        return adjustedOutput;
    }

    private double applyReverse(double currentOutput) {
        double adjustedOutput = currentOutput;
        if (reverseActive) {
            adjustedOutput = -adjustedOutput;
        }
        return adjustedOutput;
    }

    private double applyCruise(double currentOutput) {
        double adjustedOutput = currentOutput;
        if (cruiseActive && !yButtonPrev) {
            System.out.println("Y button pressed");
            cruiseOutput = adjustedOutput;
            if (cruiseControlEnabled == true)
                cruiseControlEnabled = false;
            else {
                cruiseControlEnabled = true;
            }
        } else if (!cruiseActive && yButtonPrev) {
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
        if(cruiseOverrideActive) {
            if (cruiseControlEnabled == true && Math.abs(cruiseOutput) < Math.abs(controllerOutput)) {
                if (Math.abs(cruiseOutput) < .01) {
                    cruiseControlEnabled = false;
                } else if (cruiseOutput > 0 && cruiseOutput < controllerOutput)
                    adjustedOutput = controllerOutput;
                else if (cruiseOutput < 0 && cruiseOutput > controllerOutput)
                    adjustedOutput = controllerOutput;
            }
        }
        return adjustedOutput;

    }

    private double applyDeadzone(double currentOutput) {
        double adjustedOutput = currentOutput;
        if (deadzoneActive) {
            if (adjustedOutput < .05 && adjustedOutput > 0 || adjustedOutput > -.05 && adjustedOutput < 0) {
                adjustedOutput = 0;
            }
        }
        return adjustedOutput;
    }
    private double applyCutSpeed(double currentOutput) {
        double adjustedOutput = currentOutput;
        if (cutSpeedActive) {
            adjustedOutput /= 2;
        }
        return adjustedOutput;
    }

    private double applyAutoMove(double currentOutput) {
        double adjustedOutput = currentOutput;
        int tickNumber = myTalon.getSelectedSensorPosition(0);
        if (autoMoveActive) {
            autoMoveActive = true;
        }
        if (tickNumber >= 20000) {
            adjustedOutput = currentOutput;
            autoMoveActive = false;
        } else if (autoMoveActive == true) {
            adjustedOutput = -.5;
        }
        return adjustedOutput;
    }

    private double applyTickReverse(double currentOutput) {
        double adjustedOutput = currentOutput;
        int tickNumber = myTalon.getSelectedSensorPosition(0);
        if (tickReverseActive) {
            tickSet = tickNumber;
        }
        boolean rTrigger = controller.getRawButton(6);
        if (rTrigger && !rTriggerPrev) {
            if (tickReverse == true)
                tickReverse = false;
            else
                tickReverse = true;
            if (tickReverse == true) {
                myTalon.setSelectedSensorPosition(0, 0, 0);
                theTalon.setSelectedSensorPosition(0, 0, 0);
            }
        }
        if (adjustedOutput == 0 && !areButtonsPressed() && tickReverse) {
            if (tickNumber < -6000 + tickSet) {
                adjustedOutput = -.5;
            } else if (tickNumber > 6000 + tickSet) {
                adjustedOutput = .5;
            } else if (tickNumber < -200 + tickSet) {
                adjustedOutput = -.1;
            } else if (tickNumber > 200 + tickSet) {
                adjustedOutput = .1;
            }
        }
        rTriggerPrev = rTrigger;
        return adjustedOutput;
    }
    
    private boolean areButtonsPressed() {
        if(areButtonsPressedActive)
        if (controller.getRawButton(1) || controller.getRawButton(2) || controller.getRawButton(3) ||
                controller.getRawButton(4)) {
            return true;
        }
        return false;
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
    public void setCutSpeedActive(boolean cutSpeedActive){
        this.cutSpeedActive = cutSpeedActive;
    }

    public void setAutoMoveActive(boolean autoMoveActive){
        this.autoMoveActive = autoMoveActive;
    }

    public void setTickReverseActive(boolean tickReverseActive){
        this.tickReverseActive = tickReverseActive;
    }
    public void setAreButtonsPressedActive(boolean areButtonsPressedActive){
        this.areButtonsPressedActive = areButtonsPressedActive;
    }
}

