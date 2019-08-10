package com.matthew.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.matthew.command.*;
import com.matthew.driveCodes.MatthewDrive;
import com.matthew.driveCodes.TankDrive;
import com.matthew.driveCodes.TurnAndThrottleDrive;
import com.matthew.subsystem.SimpleSubsystem;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.command.Scheduler;

public class SimpleRobot extends IterativeRobot {



    private static Joystick controller = new Joystick(3);
    private static SimpleSubsystem simpleSubsystem = new SimpleSubsystem(controller);
    private JoystickButton x = new JoystickButton(controller, 1);
    private JoystickButton a = new JoystickButton(controller, 2);
    private JoystickButton y = new JoystickButton(controller, 4);
    private JoystickButton b = new JoystickButton(controller, 3);
//    private POVButton up = new POVButton(controller,0);
//    private POVButton down = new POVButton(controller, 180);
//    private POVButton left = new POVButton(controller, 270);
//    private POVButton right = new POVButton(controller, 90);
    private JoystickButton rectangle = new JoystickButton(controller, 7);
    private JoystickButton platButton = new JoystickButton(controller,8);

//    private SimpleCommand simpleCommand = new SimpleCommand();
    private PulseCommand pulseCommand = new PulseCommand();
    private ReverseCommand  reverseCommand= new ReverseCommand();
    private CruiseCommand cruiseCommand = new CruiseCommand();
    private DeadzoneCommand deadzoneCommand = new DeadzoneCommand();
    private CutSpeedCommand cutSpeedCommand = new CutSpeedCommand();
    private AutoMoveCommand autoMoveCommand = new AutoMoveCommand();
    private TickReverseCommand tickReverseCommand = new TickReverseCommand();
    private CruiseOverrideCommand cruiseOverrideCommand = new CruiseOverrideCommand();
    private MatthewDrive matthewDrive = new MatthewDrive();
    private TankDrive tankDrive = new TankDrive();
    private TurnAndThrottleDrive turnAndThrottleDrive = new TurnAndThrottleDrive();

    @Override
    public void robotInit() {
//        x.whileHeld(simpleCommand);
        x.whileHeld(pulseCommand);
        a.whileHeld(reverseCommand);
        y.toggleWhenPressed(cruiseCommand);
        b.whileHeld(cutSpeedCommand);
//        up.whileHeld(deadzoneCommand);
//        down.toggleWhenPressed(cruiseOverrideCommand);
//        left.toggleWhenPressed(tickReverseCommand);
//        right.whileHeld(autoMoveCommand);
//        rectangle.toggleWhenPressed;



    }
    @Override
    public void robotPeriodic() {
        super.robotPeriodic();
        Scheduler.getInstance().run();
//        turnAndThrottleDrive.tTPeriodic();
//        matthewDrive.Periodic();
//        tankDrive.TankPeriodic();

    }

    public static SimpleSubsystem getSimpleSubsystem() {
        return simpleSubsystem;
    }
}
