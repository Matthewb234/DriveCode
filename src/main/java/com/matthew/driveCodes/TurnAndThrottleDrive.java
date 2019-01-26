package com.matthew.driveCodes;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class TurnAndThrottleDrive {
	private Joystick throttle = new Joystick(1);
	private Joystick wheel = new Joystick(0);
	private TalonSRX rightMaster = new TalonSRX(1);
	private TalonSRX leftMaster = new TalonSRX(0);
	double rightPower;
	double leftPower;

	public void tTPeriodic(){
		calculatePower();
		rightMaster.set(ControlMode.PercentOutput, rightPower);
		leftMaster.set(ControlMode.PercentOutput, leftPower);
	}

	public void calculatePower() {
		double outsidePower = throttle.getRawAxis(1);
		double insidePower = (1 - wheel.getRawAxis(1))* throttle.getRawAxis(1);
		if(wheel.getRawAxis(1) < 0) {
			leftPower = insidePower;
			rightPower = outsidePower;
		
		}else {
			leftPower = outsidePower;
			rightPower = insidePower;
			
		}
	}
}

