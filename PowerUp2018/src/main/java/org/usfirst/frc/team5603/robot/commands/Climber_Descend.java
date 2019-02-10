package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Climber_Descend extends Command {

	private static final double LIFT_RAISE_POWER=.4;  // We do the opposite when descending Climb, we raise the lift
	
    public Climber_Descend() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.kClimber);
    	requires(Robot.kLift);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.logDebug("Descend Button", 1);
    	Robot.kClimber.Descend();
    	Robot.kLift.Raise_Lower(LIFT_RAISE_POWER);
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
