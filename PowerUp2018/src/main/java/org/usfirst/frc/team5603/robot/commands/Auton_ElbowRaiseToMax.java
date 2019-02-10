package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auton_ElbowRaiseToMax extends Command {

	double m_liftPower;

    public Auton_ElbowRaiseToMax(double thisElbowTimeout, double thisElbowPower) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.kElbow);
    	setTimeout(thisElbowTimeout);
    	m_liftPower = thisElbowPower;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kElbow.TurnOnMaintain();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kElbow.Raise_Lower(m_liftPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isTimedOut() || Robot.kElbow.AtEndOfTravelUp();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kElbow.Raise_Lower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
