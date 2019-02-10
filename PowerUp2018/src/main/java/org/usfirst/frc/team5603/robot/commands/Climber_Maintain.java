package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Climber_Maintain extends Command {

    public Climber_Maintain() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.kClimber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.kClimber.IsMaintained())
    	{
    		Robot.kClimber.Maintain();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kClimber.Stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
