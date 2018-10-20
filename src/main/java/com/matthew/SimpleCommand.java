package com.matthew;

import edu.wpi.first.wpilibj.command.Command;

public class SimpleCommand extends Command {

    private SimpleSubsystem simpleSubsystem = new SimpleSubsystem();

    public SimpleCommand() {
        super("SimpleCommand");
        requires(simpleSubsystem);
    }

    @Override
    protected void execute() {
        simpleSubsystem.move();
    }

    @Override
    protected void end() {
        simpleSubsystem.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
