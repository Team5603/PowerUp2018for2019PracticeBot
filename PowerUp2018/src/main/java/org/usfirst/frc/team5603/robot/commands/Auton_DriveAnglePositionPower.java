package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import Util.UtilityFunctions;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */

public class Auton_DriveAnglePositionPower extends Command {

	//Drive Angle Factors
	private double m_DriveAngleTolerance=.5; 
	private double m_DriveAngleDegreesIncrement=.2;
	private double m_DriveAngleIncrementAdjust=.01;

	private double m_driveAngle;
	private double m_drivePower;
	private int    m_encoderPosition;
	
    public Auton_DriveAnglePositionPower(double driveAngle, int drivePosition, double drivePower) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.kDriveTrain);
    	m_driveAngle = driveAngle;
        m_drivePower = drivePower;
        m_encoderPosition = drivePosition;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double degreesToTurn;
		double LMotorValue;
		double RMotorValue;
		double MotorAdjust;
		
		//Robot.logDebug("Driving " + Double.toString(timeSinceInitialized()),1);
		LMotorValue = m_drivePower;
		RMotorValue = LMotorValue; // initially the same
		// drive here
		degreesToTurn = UtilityFunctions.AngleDiff(m_driveAngle);
		if (java.lang.Math.abs(degreesToTurn)>m_DriveAngleTolerance)
		{
			//Outside Tolerance, so need to adjust
			// first subtract the tolerance from the degreesToTurn, then divide by the degrees increment
			// then multiply by increment adjustment amount to give us a motor adjustment amount
			MotorAdjust = ((java.lang.Math.abs(degreesToTurn)-m_DriveAngleTolerance)/m_DriveAngleDegreesIncrement)*m_DriveAngleIncrementAdjust;
			if (degreesToTurn>0)
			{
				// drive a little more clockwise (faster left motor, slower right motor)
				LMotorValue = LMotorValue + MotorAdjust;
				if (LMotorValue>1) LMotorValue=1;
				RMotorValue = RMotorValue - MotorAdjust;
				if (RMotorValue<0) RMotorValue=0;
			}
			else
			{
				// drive a little more counter clockwise, slower left, faster right
				LMotorValue = LMotorValue - MotorAdjust;
				if (LMotorValue<0) LMotorValue=0;
				RMotorValue = RMotorValue + MotorAdjust;
				if (RMotorValue>1) RMotorValue=1;
			}
		
		}
		//Robot.logDebug("Left Motor : " + Double.toString(LMotorValue) + " Right Motor:" + Double.toString(RMotorValue)+" Degrees To Turn : " + Double.toString(degreesToTurn),1);
		
		Robot.kDriveTrain.autonAbsoluteDrive(LMotorValue, RMotorValue);
		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.kDriveTrain.GetEncoderPosition('L') >= m_encoderPosition;
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
