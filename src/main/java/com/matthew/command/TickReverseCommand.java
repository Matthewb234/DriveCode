package com.matthew.command;

import com.matthew.robot.SimpleRobot;
import edu.wpi.first.wpilibj.command.Command;

public class TickReverseCommand extends Command {

    @Override
    protected void initialize() {
        SimpleRobot.getSimpleSubsystem().resetTickNumber();
    }

    @Override
    protected boolean isFinished() {

        return false;
    }



    @Override
    protected void execute() {
        System.out.println("Started");
        SimpleRobot.getSimpleSubsystem().setTickReverseActive(true);
    }

    @Override
    protected void end() {
        System.out.println("Stopped");
        SimpleRobot.getSimpleSubsystem().setTickReverseActive(false);
    }
}




