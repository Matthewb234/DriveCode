package com.matthew;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SimpleSubsystem extends Subsystem {

    private TalonSRX myTalon = new TalonSRX(2);
    private TalonSRX theTalon = new TalonSRX(3);

    public void move() {
        myTalon.set(ControlMode.PercentOutput, .3);
        theTalon.set(ControlMode.PercentOutput, .3);
    }

    public void stop() {
        myTalon.set(ControlMode.PercentOutput, 0);
        theTalon.set(ControlMode.PercentOutput, 0);
    }
    @Override
    protected void initDefaultCommand() {

    }
}
