package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AA_TEST_COMMAND extends Command {

	double m_value;
	
    public AA_TEST_COMMAND(double value) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.kClimber);
    	m_value = value;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.kDriveTrain.InitForAuton();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.logDebug("Test Command " + m_value, 1);
    	Robot.kClimber.test1(m_value);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.kDriveTrain.autonAbsoluteDrive(0, 0);
    	Robot.kClimber.Stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
