package com.matthew.driveCodes;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class TankDrive {

    private static Joystick controller = new Joystick(1);
    private TalonSRX talon = new TalonSRX(1);
    private TalonSRX talon2 = new TalonSRX(0);

    private double left;
    private double right;

    public void TankPeriodic() {
        talon.set(ControlMode.PercentOutput, right);
        talon2.set(ControlMode.PercentOutput, left);
    }

        public double[] directionChanging () {
            left = controller.getRawAxis(1);
            right = controller.getRawAxis(2);

            return new double[]{left, right};
        }
}
