package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonLift_LiftToMax extends Command {

	double m_liftPower;
	
    public AutonLift_LiftToMax(double setLiftTime, double thisLiftPower) {
    	requires(Robot.kLift);
    	setTimeout(setLiftTime);
    	m_liftPower = thisLiftPower;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kLift.TurnOnMaintain();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kLift.Raise_Lower(m_liftPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || Robot.kLift.AtEndOfTravelUp();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kLift.Raise_Lower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
