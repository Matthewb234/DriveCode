package com.matthew.command;

import com.matthew.robot.SimpleRobot;
import edu.wpi.first.wpilibj.command.Command;

public class ReverseCommand extends Command {

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        SimpleRobot.getSimpleSubsystem().setReverseActive(true);
    }

    @Override
    protected void end() {
        SimpleRobot.getSimpleSubsystem().setReverseActive(false);
    }
}
