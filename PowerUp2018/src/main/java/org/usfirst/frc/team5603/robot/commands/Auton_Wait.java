package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *  THIS COMMAND JUST ALLOWS US TO PUT A PAUSE IN OUR AUTON PROCEDURES
 */
public class Auton_Wait extends Command {

    public Auton_Wait(double WaitTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setTimeout(WaitTime);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Do nothing
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
