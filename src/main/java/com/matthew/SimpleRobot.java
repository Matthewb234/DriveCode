package com.matthew;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;

public class SimpleRobot extends IterativeRobot {

    private Joystick controller = new Joystick(1);
    private JoystickButton x = new JoystickButton(controller, 1);
    private SimpleCommand simpleCommand = new SimpleCommand();

    @Override
    public void robotInit() {
        x.whileHeld(simpleCommand);

    }

    @Override
    public void robotPeriodic() {
        super.robotPeriodic();
        Scheduler.getInstance().run();
    }
}
