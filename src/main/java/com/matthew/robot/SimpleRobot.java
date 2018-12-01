package com.matthew.robot;

import com.matthew.command.*;
import com.matthew.subsystem.SimpleSubsystem;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;

public class SimpleRobot extends IterativeRobot {



    private static Joystick controller = new Joystick(0);
    private static SimpleSubsystem simpleSubsystem = new SimpleSubsystem(controller);
    private JoystickButton x = new JoystickButton(controller, 1);
    private JoystickButton a = new JoystickButton(controller, 2);
    private JoystickButton y = new JoystickButton(controller, 4);
    private JoystickButton b = new JoystickButton(controller, 3);
//    private Joystick trigger = new Joystick(controller )

//    private SimpleCommand simpleCommand = new SimpleCommand();
    private ApplyPulseCommand applyPulseCommand = new ApplyPulseCommand();
    private ApplyReverseCommand applyReverseCommand = new ApplyReverseCommand();
    private ApplyCruiseCommand applyCruiseCommand = new ApplyCruiseCommand();
    private ApplyDeadzoneCommand applyDeadzoneCommand = new ApplyDeadzoneCommand();
    private ApplyCutSpeedCommand applyCutSpeedCommand = new ApplyCutSpeedCommand();
    private ApplyAutoMoveCommand applyAutoMoveCommand = new ApplyAutoMoveCommand();
    private ApplyTickReverseCommand applyTickReverseCommand = new ApplyTickReverseCommand();
    private ApplyAreButtonsPressedCommand applyAreButtonsPressedCommand = new ApplyAreButtonsPressedCommand();
//    private ApplyCruiseOverrideCommand applyCruiseOverrideCommand = new ApplyCruiseOverrideCommand();


    @Override
    public void robotInit() {
//        x.whileHeld(simpleCommand);
        x.whileHeld(applyPulseCommand);
        a.whileHeld(applyReverseCommand);
        y.whenPressed(applyCruiseCommand);
        b.whileHeld(applyCutSpeedCommand);
    }

    @Override
    public void robotPeriodic() {
        super.robotPeriodic();
        Scheduler.getInstance().run();
    }

    public static SimpleSubsystem getSimpleSubsystem() {
        return simpleSubsystem;
    }
}
