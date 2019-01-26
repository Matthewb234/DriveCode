package com.matthew.driveCodes;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class MatthewDrive {

    private static Joystick controller = new Joystick(3);
    private TalonSRX talon = new TalonSRX(1);
    private TalonSRX talon2 = new TalonSRX(0);

    private double leftOutput;
    private double rightOutput;
    private double leftTurn;
    private double rightTurn;
    private boolean leftButton;
    private boolean rightButton;
    private double motorOutput;

    public void Periodic() {
        motorOutput = controller.getRawAxis(1);
        leftTurn = controller.getRawAxis(2);
        rightTurn = controller.getRawAxis(3);
        leftButton = controller.getRawButton(5);
        rightButton = controller.getRawButton(6);
        power();
        talon.set(ControlMode.PercentOutput, rightOutput);
        talon2.set(ControlMode.PercentOutput, leftOutput);
    }

    public void power(){
        leftOutput = Math.max(motorOutput - leftTurn,0);
        rightOutput = Math.max(motorOutput - rightTurn,0);

        if(leftButton) {
            leftOutput = -.5;
            rightOutput = .5;
        }
        if(rightButton){
            leftOutput = .5;
            rightOutput = -.5;
        }
    }
}
