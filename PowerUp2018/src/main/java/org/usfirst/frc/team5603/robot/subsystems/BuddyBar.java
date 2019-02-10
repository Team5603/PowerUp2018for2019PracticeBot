package org.usfirst.frc.team5603.robot.subsystems;

import org.usfirst.frc.team5603.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BuddyBar extends Subsystem {

	public Spark m_BuddyBarMotor;
	
	public BuddyBar() {
		//m_BuddyBarMotor = new Spark(RobotMap.BUDDY_BAR_MOTOR_PWM);
		
	}
	
	public void setPower(double buddyBarPower) {
		//m_BuddyBarMotor.set(buddyBarPower);
	}
	
    public void initDefaultCommand() {
   
    }
}

