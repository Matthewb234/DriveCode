package com.matthew.driveCodes;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class TurnAndThrottleDrive {
	private Joystick throttle = new Joystick(2);
	private Joystick wheel = new Joystick(0);
	private TalonSRX rightMaster = new TalonSRX(1);
	private TalonSRX leftMaster = new TalonSRX(0);
	private double rightPower;
	private double leftPower;
	private double outsidePower;
	private double insidePower;

	public void tTPeriodic(){
		outsidePower = throttle.getRawAxis(1);
		if(wheel.getRawAxis(0) <= 0) {
			insidePower = (1 + wheel.getRawAxis(0)) * throttle.getRawAxis(1);
		}else if(wheel.getRawAxis(0) >= 0){
			insidePower = (1 - wheel.getRawAxis(0)) * throttle.getRawAxis(1);
		}
		calculatePower();
		rightMaster.set(ControlMode.PercentOutput, rightPower);
		leftMaster.set(ControlMode.PercentOutput, leftPower);
	}

	public void calculatePower() {
		System.out.println("hi");
		if(wheel.getRawAxis(0) >= 0) {
			leftPower = insidePower;
			rightPower = outsidePower;
		
		}else {
			leftPower = outsidePower;
			rightPower = insidePower;
			
		}
	}
}

