package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonSuckerPlooper_Ploop extends Command {
	double m_ploopPower;
    public AutonSuckerPlooper_Ploop(double thisPloopPower, double thisPloopTime) {
    	requires(Robot.kSuckerPlooper);
    	setTimeout(thisPloopTime);
    	m_ploopPower = thisPloopPower;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kSuckerPlooper.PloopPower(.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
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
