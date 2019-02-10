package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auton_DriveDistanceInchesTalonPID extends Command {

	double leftPositionTarget;
	double rightPositionTarget;
	double driveInches;
	
    public Auton_DriveDistanceInchesTalonPID(double InchesToDrive) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.kDriveTrain);
    	driveInches = InchesToDrive;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(5);
    	Robot.kDriveTrain.disableSafety();
    	leftPositionTarget = Robot.kDriveTrain.GetEncoderPosition('L') + (driveInches/(Robot.kDriveTrain.WHEEL_DIAMETER*Math.PI/Robot.kDriveTrain.TICKS_PER_REV));
    	rightPositionTarget = Robot.kDriveTrain.GetEncoderPosition('R') + (driveInches/(Robot.kDriveTrain.WHEEL_DIAMETER*Math.PI/Robot.kDriveTrain.TICKS_PER_REV));
    	//Robot.logDebug("Left and Right Target : "+ Double.toString(leftPositionTarget) + " - " + Double.toString(rightPositionTarget), 1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kDriveTrain.DriveToEncoderPositions(leftPositionTarget, rightPositionTarget);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDriveTrain.enableSafety();
    	Robot.kDriveTrain.autonAbsoluteDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
