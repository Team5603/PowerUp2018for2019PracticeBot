package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ElbowOperateJoystick extends Command {

    public ElbowOperateJoystick() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.kElbow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.logDebug("Elbow Joystick Execute : " + Robot.m_oi.getElbowValue(), 1);
    	if (!RobotState.isAutonomous()) {
        	double elbowValue = Robot.m_oi.getElbowValue();
        	//Robot.logDebug("Elbow Value " +  Double.toString(elbowValue), 4);
        	if (elbowValue!=0) {
        		// First thing, whenever they move the stick, we turn maintain back on!!
        		Robot.kElbow.TurnOnMaintain();
        		Robot.kElbow.Raise_Lower(elbowValue);
        	} else {
        		// So stick has no value or within deadband, if we have maintain on then maintain
        		// if not, then don't
        		if (Robot.kElbow.GetMaintain())
        			Robot.kElbow.Maintain();
        		else
        			Robot.kElbow.Stop();
        	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kElbow.Stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
