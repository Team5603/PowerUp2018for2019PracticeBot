/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5603.robot;

import org.usfirst.frc.team5603.robot.commands.BuddyBar_Down;
import org.usfirst.frc.team5603.robot.commands.Climber_Climb;
import org.usfirst.frc.team5603.robot.commands.Climber_Descend;
import org.usfirst.frc.team5603.robot.commands.ElbowTurnOffMaintain;
import org.usfirst.frc.team5603.robot.commands.LiftTurnOffMaintain;
import org.usfirst.frc.team5603.robot.commands.SetSlow;
import org.usfirst.frc.team5603.robot.commands.SetTurbo;
import org.usfirst.frc.team5603.robot.commands.SuckerPlooper_Ploop_High;
import org.usfirst.frc.team5603.robot.commands.SuckerPlooper_Ploop_Low;
import org.usfirst.frc.team5603.robot.commands.SuckerPlooper_Suck;

import Util.JoystickMap;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	/*
	 * Joysticks
	 */
	// we may run with different configurations
	// 2 actual joysticks for driver and gamepad for operator
	// 1 gamepad for driver and 1 gamepad for operator
	// Plus the gamepad could be Logitech or XBox controller, however the logitech maps the same as the xbox (newer logitech anyway)
	public static final int OPERATOR_GAMEPAD_ID = 0;  //ALWAYS MAKE OPERATOR GAMEPAD 0, SO WE DONT HAVE TO CHANGE ANYTHING, I.E. THERE
													  // WILL ALWAYS BE AN OPERATOR GAMEPAD, BUT DRIVER STUFF CAN BE DIFFERENT
	//public static final int DRIVER_GAMEPAD_ID = 1;
	public static final int DRIVER_LEFT_JOYSTICK_ID = 1;
	public static final int DRIVER_RIGHT_JOYSTICK_ID = 2;
	
    private static final double JOYSTICK_DEADBAND = 0.01;
    private static final double GAMEPAD_DEADBAND = 0.01;

	//Controllers
    public Joystick opPad; 
	public Joystick driverPad;
	public Joystick driverLeftJoystick;
	public Joystick driverRightJoystick;
	
    private boolean m_turbo;
    private boolean m_slow;
	
	// BUTTONS
	public Button turboButton;
	public Button slowButton;
	public Button suckerplooperSuckButton;
	public Button suckerplooperPloopLowButton;
	public Button suckerplooperPloopLowButton2;
	public Button suckerplooperPloopHighButton;
	
	public Button climberClimbButton;
	public Button climberDescendButton;
	public Button liftTurnOffMaintainButton;
	public Button elbowTurnOffMaintainButton;
	public Button buddyBarDown;
	
	public OI() {

		initializeJoysticks();
		m_turbo = false;
		m_slow = false;
	}

	public void initializeJoysticks()
	{
		opPad = new Joystick(OPERATOR_GAMEPAD_ID);  // Don't initialize operator gamepad if Chassis only
		suckerplooperSuckButton = new JoystickButton(opPad, JoystickMap.gamepad.A);
		suckerplooperSuckButton.whileHeld(new SuckerPlooper_Suck());
		suckerplooperPloopLowButton = new JoystickButton(opPad, JoystickMap.gamepad.B);
		suckerplooperPloopLowButton.whileHeld(new SuckerPlooper_Ploop_Low());
		suckerplooperPloopLowButton2 = new JoystickButton(opPad, JoystickMap.gamepad.RB);
		suckerplooperPloopLowButton2.whileHeld(new SuckerPlooper_Ploop_Low());
		suckerplooperPloopHighButton = new JoystickButton(opPad, JoystickMap.gamepad.Y);
		suckerplooperPloopHighButton.whileHeld(new SuckerPlooper_Ploop_High());
		climberClimbButton = new JoystickButton(opPad, JoystickMap.gamepad.LB);
		climberClimbButton.whileHeld(new Climber_Climb());
		climberDescendButton = new JoystickButton(opPad, JoystickMap.gamepad.X);
		climberDescendButton.whileHeld(new Climber_Descend());
		liftTurnOffMaintainButton = new JoystickButton(opPad, JoystickMap.gamepad.L3);
		liftTurnOffMaintainButton.whenPressed(new LiftTurnOffMaintain());
		elbowTurnOffMaintainButton = new JoystickButton(opPad, JoystickMap.gamepad.R3);
		elbowTurnOffMaintainButton.whenPressed(new ElbowTurnOffMaintain());
		buddyBarDown = new JoystickButton(opPad, JoystickMap.gamepad.START);
		buddyBarDown.whenPressed(new BuddyBar_Down(1)); 
		driverLeftJoystick = new Joystick(DRIVER_LEFT_JOYSTICK_ID);
		driverRightJoystick = new Joystick(DRIVER_RIGHT_JOYSTICK_ID);
		turboButton = new JoystickButton(driverLeftJoystick, JoystickMap.joystickGeneric.TRIGGER);
		turboButton.whileHeld(new SetTurbo());
		slowButton = new JoystickButton(driverRightJoystick, JoystickMap.joystickGeneric.TRIGGER);
		slowButton.whileHeld(new SetSlow());
		//Robot.logDebug("Operator Interface Message : initialized to Joysticks",1);
	}
	
    public void setTurbo(boolean isTurbo) {
    	m_turbo = isTurbo;
    	//Robot.logDebug("Turbo set " + Boolean.toString(m_turbo),1);
    }

    public boolean getTurbo() {
    	return m_turbo;
   }

    public void setSlow(boolean isSlow) {
    	m_slow = isSlow;
       	//Robot.logDebug("Slow set " + Boolean.toString(m_slow),1);
    }
    
    public boolean getSlow() {
    	return m_slow;
    }
    private static double stickDeadband(double value, double deadband, double center) {
		return (value < (center + deadband) && value > (center - deadband)) ? center : value;
	}

	public double getDriverLeftStickY() {
		// The stick we return is based on the controller we are using...
		double leftValue=0;
		
		leftValue = -stickDeadband(driverLeftJoystick.getY(), JOYSTICK_DEADBAND, 0.0);

		return leftValue;
	}

	public double getDriverRightStickY() {
		// The stick we return is based on the controller we are using...
		double rightValue=0;
		
		rightValue = -stickDeadband(driverRightJoystick.getY(), JOYSTICK_DEADBAND, 0.0);

		return rightValue;
	}

	public double getLiftValue() {
		double liftValue=0;
		// Only have operator gamepad for this
		liftValue = -stickDeadband(opPad.getRawAxis(JoystickMap.gamepad.LEFT_Y), GAMEPAD_DEADBAND, 0.0);  // reverse value cuz joysticks go negative in positive directon
		
		//SmartDashboard.putNumber("LiftValue", liftValue);
		return liftValue;
		
	}
	
	public double getElbowValue() {
		double elbowValue=0;
		// Only have operator gamepad for this
		elbowValue = -stickDeadband(opPad.getRawAxis(JoystickMap.gamepad.RIGHT_Y), GAMEPAD_DEADBAND, 0.0);  // reverse value cuz joysticks go negative in positive directon

		return elbowValue;
	}
	
	
	public double getIntakeVariable() {
		double intakeValue = 0;
		intakeValue = stickDeadband(opPad.getRawAxis(JoystickMap.gamepad.RIGHT_TRIGGER), GAMEPAD_DEADBAND, 0.0); 
		
		return intakeValue;
	}
	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
