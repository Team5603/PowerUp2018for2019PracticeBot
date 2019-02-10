package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import Util.UtilityFunctions;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonTurnAngle extends Command {
 
	//Turn Factors
	private double m_TurnTolerance=1;
	private double m_TurnFactor1Tolerance=7;
	private double m_TurnFactor1Speed=.35;
	private double m_TurnFactor2Tolerance=20;
	private double m_TurnFactor2Speed=.35;
	private double m_TurnFactor3Tolerance=40;
	private double m_TurnFactor3Speed=.4;
	private double m_TurnFactor4Speed=.5;

	private double m_destAngle;
	private boolean m_there;
	
    public AutonTurnAngle(double targetAngle, double timeout) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.kDriveTrain);
        setTimeout(timeout);
        m_destAngle = targetAngle;
        m_there = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_there = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		double degreesToTurn = UtilityFunctions.AngleDiff(m_destAngle);
		double motorSpeed;
		
		//Robot.logDebug("TurnAngle " + Double.toString(degreesToTurn),1);
		
		//Check if angle is higher than Factor 3 tolerance 
		if (java.lang.Math.abs(degreesToTurn)>m_TurnFactor3Tolerance)
			motorSpeed = m_TurnFactor4Speed;
		else if (java.lang.Math.abs(degreesToTurn)>m_TurnFactor2Tolerance)
			motorSpeed = m_TurnFactor3Speed;
		else if (java.lang.Math.abs(degreesToTurn)>m_TurnFactor1Tolerance)
			motorSpeed = m_TurnFactor2Speed;
		else if (java.lang.Math.abs(degreesToTurn)>m_TurnTolerance)
			motorSpeed = m_TurnFactor1Speed;
		else
		{
			//reached our target
			motorSpeed = 0;
			m_there=true;
			//Robot.logDebug("Reached our Angle... " + Robot.kDriveTrain.getAngle(),1);
		}
			
		if (degreesToTurn<0)
		{
			//Robot.logDebug("Turning CCW at " + Double.toString(motorSpeed) +" motor speed.", 1);
			//move CCW, so right side forward , left side backward (negative)
			Robot.kDriveTrain.autonAbsoluteDrive(-motorSpeed, motorSpeed);
		}
		else
		{
			//Robot.logDebug("Turning CW at " + Double.toString(motorSpeed) +" motor speed.", 1);
			//move CW, so left side forward (positive), and right side backward (positive)
			Robot.kDriveTrain.autonAbsoluteDrive(motorSpeed, -motorSpeed);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_there || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDriveTrain.autonAbsoluteDrive(0, 0);
    	//Robot.logDebug("Auton Turn Angle END", 1);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}
