package com.matthew.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Ultrasonic;


public class UltraSonic extends TimedRobot {
    private TalonSRX theTalon = new TalonSRX(4);

    Ultrasonic ultra;
    private static Joystick controller = new Joystick(1);
    private double output;

    public void robotInit() {
        ultra = new Ultrasonic(0, 1);
        ultra.setAutomaticMode(true);
    }

    @Override
    public void robotPeriodic() {
        super.robotPeriodic();
        double range = ultra.getRangeInches();
        SmartDashboard.putNumber("foo",range);

        if(controller.getRawButton(1)){
            output = range / 100;
        } else {
            output = 0;
        }

        theTalon.set(ControlMode.PercentOutput, output);
    }
}
