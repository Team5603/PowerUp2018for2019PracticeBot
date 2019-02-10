package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auton_DriveAbsolutePowerTime extends Command {

	private double m_leftPower;
	private double m_rightPower;
	
    public Auton_DriveAbsolutePowerTime(double leftPower, double rightPower, double driveTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.kDriveTrain);
        setTimeout(driveTime);
        m_leftPower = leftPower;
        m_rightPower = rightPower;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kDriveTrain.autonAbsoluteDrive(m_leftPower, m_rightPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDriveTrain.autonAbsoluteDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
