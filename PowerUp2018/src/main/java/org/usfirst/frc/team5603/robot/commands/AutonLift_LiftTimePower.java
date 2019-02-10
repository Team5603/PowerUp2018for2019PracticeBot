package org.usfirst.frc.team5603.robot.commands;

import org.usfirst.frc.team5603.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonLift_LiftTimePower extends Command {

	double m_liftPower;
	
    public AutonLift_LiftTimePower(double setLiftTime, double thisLiftPower) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.kLift);
        setTimeout(setLiftTime);  // Going to lift until it times out.  Once it times out then the joystick command will take over which will maintain
        m_liftPower = thisLiftPower;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kLift.TurnOnMaintain();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.kLift.Raise_Lower(m_liftPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.kLift.Raise_Lower(0); 
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
