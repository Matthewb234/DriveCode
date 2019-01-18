package com.matthew.command;


import com.matthew.robot.SimpleRobot;
import edu.wpi.first.wpilibj.command.Command;

    public class DeadzoneCommand extends Command {

        @Override
        protected boolean isFinished() {

            return false;
        }

        @Override
        protected void execute() {

            SimpleRobot.getSimpleSubsystem().setDeadzoneActive(true);
        }

        @Override
        protected void end() {

            SimpleRobot.getSimpleSubsystem().setDeadzoneActive(false);
        }
    }
