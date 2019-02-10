package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonLift_LiftToPosition extends Command {

	private double m_liftPower;
	private int    m_encoderPosition;

	public AutonLift_LiftToPosition(double liftPower, int liftPosition, double liftTimeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.kLift);
        m_liftPower = liftPower;
        m_encoderPosition = liftPosition;
        setTimeout(liftTimeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kLift.TurnOnMaintain();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kLift.TurnOnMaintain();
    	Robot.kLift.Raise_Lower(m_liftPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.kLift.GetEncoderPosition() >= m_encoderPosition) || isTimedOut();
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
