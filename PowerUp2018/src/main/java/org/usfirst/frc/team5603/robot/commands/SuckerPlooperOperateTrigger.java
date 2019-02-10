package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SuckerPlooperOperateTrigger extends Command {
	private static final double VARIABLE_INTAKE_MULTIPLIER = .5;
    public SuckerPlooperOperateTrigger() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.kSuckerPlooper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (!RobotState.isAutonomous()) {
        	double intakeValue = Robot.m_oi.getIntakeVariable();
        	//Robot.logDebug("Elbow Value " +  Double.toString(elbowValue), 4);
        	//Robot.logDebug("VariableIntake value " + intakeValue, 1);
        	if (intakeValue > 0) {
        		// First thing, whenever they move the stick, we turn maintain back on!!
        		intakeValue = intakeValue*VARIABLE_INTAKE_MULTIPLIER;
        		Robot.kSuckerPlooper.SuckVariable(intakeValue);
        		
        		
        	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kSuckerPlooper.Stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
