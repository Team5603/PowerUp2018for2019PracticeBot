package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TankDriveWithJoystick extends Command {

    public TankDriveWithJoystick() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.kDriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println("TankDriveWithJoystick Execute ");
    	//Robot.logDebug("TankDrive Execute", 4);
    	if (!RobotState.isAutonomous())
    		Robot.kDriveTrain.tankDrive(Robot.m_oi.getDriverLeftStickY(), Robot.m_oi.getDriverRightStickY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDriveTrain.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
