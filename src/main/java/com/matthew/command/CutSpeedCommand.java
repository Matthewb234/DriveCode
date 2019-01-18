package com.matthew.command;

import com.matthew.robot.SimpleRobot;
import edu.wpi.first.wpilibj.command.Command;

    public class CutSpeedCommand extends Command {

        @Override
        protected boolean isFinished() {

            return false;
        }

        @Override
        protected void execute() {

            SimpleRobot.getSimpleSubsystem().setCutSpeedActive(true);
        }

        @Override
        protected void end() {

            SimpleRobot.getSimpleSubsystem().setCutSpeedActive(false);
        }
}

