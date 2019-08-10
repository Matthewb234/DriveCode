package com.matthew.driveCodes;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class DrivingSetPercent {

private Joystick controller = new Joystick(1);
private TalonSRX motor = new TalonSRX(1);
private void setOutput(){
    if (controller.getRawButton(1)) {
        motor.set(ControlMode.PercentOutput, .5);
    }
}
}
