package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auton_ElbowLowerToBottom extends Command {

    public Auton_ElbowLowerToBottom(double thisTimeout) {
        requires(Robot.kElbow);
        setTimeout(thisTimeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kElbow.TurnOffMaintain();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kElbow.Raise_Lower(-1);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       
    	return isTimedOut() || Robot.kElbow.AtEndOfTravelDown();
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
