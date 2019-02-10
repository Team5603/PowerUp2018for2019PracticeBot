/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5603.robot;

// TEST

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5603.robot.commands.AA_TEST_COMMAND;
import org.usfirst.frc.team5603.robot.commands.AA_TEST_COMMAND2;
import org.usfirst.frc.team5603.robot.commands.AutonDriveAngleTimePower;
import org.usfirst.frc.team5603.robot.commands.AutonLift_LiftTimePower;
import org.usfirst.frc.team5603.robot.commands.AutonTestDrive;
import org.usfirst.frc.team5603.robot.commands.Auton_CrossLine;
import org.usfirst.frc.team5603.robot.commands.Auton_DriveAnglePositionPower;
import org.usfirst.frc.team5603.robot.commands.Auton_DriveDistanceInchesTalonPID;
import org.usfirst.frc.team5603.robot.commands.Auton_LeftStartLeftScale;
import org.usfirst.frc.team5603.robot.commands.Auton_LeftStartLeftScalePositionOnly;
import org.usfirst.frc.team5603.robot.commands.Auton_LeftStartLeftSwitch;
import org.usfirst.frc.team5603.robot.commands.Auton_LeftStartRightScale;
import org.usfirst.frc.team5603.robot.commands.Auton_LeftStartRightScalePositionOnly;
import org.usfirst.frc.team5603.robot.commands.Auton_MiddleStartDeliverSwitchLeft;
import org.usfirst.frc.team5603.robot.commands.Auton_MiddleStartDeliverSwitchStraight;
import org.usfirst.frc.team5603.robot.commands.Auton_RightScale;
import org.usfirst.frc.team5603.robot.commands.Auton_RightStartLeftScale;
import org.usfirst.frc.team5603.robot.commands.Auton_RightStartLeftScalePositionOnly;
import org.usfirst.frc.team5603.robot.commands.Auton_RightStartRightScalePositionOnly;
import org.usfirst.frc.team5603.robot.commands.Auton_RightStartRightSwitch;
import org.usfirst.frc.team5603.robot.subsystems.BuddyBar;
import org.usfirst.frc.team5603.robot.subsystems.Climber;
import org.usfirst.frc.team5603.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5603.robot.subsystems.Elbow;
import org.usfirst.frc.team5603.robot.subsystems.Lift;
import org.usfirst.frc.team5603.robot.subsystems.SuckerPlooper;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	// PROGRAM SETUP CONSTANTS - SET THESE AND REDEPLOY BASED ON WHAT YOU ARE DOING...
	
	// System out various stuff, 
	// 1 - called once items, such as initialize constructors, etc.
	// 2 - loops that are triggered from a button, etc.
	// 3 - default loops, such as drive w/joystick execute
	public static final int DEBUG_LEVEL=10; 
	
	// Are we running/testing Chassis only? won't initialize other motor controllers, operator joystick, buttons, etc
	//public static final boolean CHASSIS_ONLY=false;
	
	/*** CHANGE TO PROPER CONTROLLER TYPE and redeploy ***/
	//public static final String DRIVER_TYPE = "Joysticks"; // Gamepad or Joysticks

	/********* CHANGE TO PROPER CONTROLLER TYPES and redeploy ********/
	//public static final String CHASSIS_MOTOR_CONTROLLER_TYPE="Talon"; // Talon or Spark

	//public static final boolean UdebugSING_CAMERAS=true;
	
	// END PROGRAM SETUP CONSTANTS
	
	
	//SUBSYSTEMS
	public static final Climber kClimber = new Climber();
	public static final DriveTrain kDriveTrain = new DriveTrain();
	public static final Elbow kElbow = new Elbow();
	public static final Lift kLift = new Lift();
	public static final SuckerPlooper kSuckerPlooper = new SuckerPlooper();
	public static final BuddyBar kBuddyBar = new BuddyBar();
	
	//Operator Interface
	public static final OI m_oi = new OI();
	public static final FMS m_FMS = new FMS();
	
	//Autonomous
	Command m_autonomousCommand;
	SendableChooser<String> m_chooser = new SendableChooser<>();

	
	// Our functions
	public static void logDebug(String msg, int debugLevel) {
		if (debugLevel<=Robot.DEBUG_LEVEL) System.out.println(msg);
	}
	
	private void UpdateDashboard() {
		SmartDashboard.putBoolean("Elbow Maintain : " , Robot.kElbow.GetMaintain());
    	SmartDashboard.putBoolean("Lift Maintain : " , Robot.kLift.GetMaintain());
		SmartDashboard.putNumber("Lift Motor Current", kLift.GetMotorCurrent());
		SmartDashboard.putNumber("Lift Motor Speed", kLift.GetMotorSpeed() );
		SmartDashboard.putNumber("Lift Motor Encoder Position", kLift.GetEncoderPosition());
		SmartDashboard.putNumber("Elbow Motor Current", kElbow.GetMotorCurrent());
		SmartDashboard.putNumber("Elbow Motor Speed", kElbow.GetMotorSpeed() );
		SmartDashboard.putNumber("Elbow Motor Encoder Position", kElbow.GetEncoderPosition());
		SmartDashboard.putNumber("Left Motor Current", kDriveTrain.GetMotorCurrent('L'));
		SmartDashboard.putNumber("Left Motor Speed", kDriveTrain.GetMotorSpeed('L') );
		SmartDashboard.putNumber("Left Motor Encoder Position", kDriveTrain.GetEncoderPosition('L'));
		SmartDashboard.putNumber("Right Motor Current", kDriveTrain.GetMotorCurrent('R'));
		SmartDashboard.putNumber("Right Motor Speed", kDriveTrain.GetMotorSpeed('R'));
		SmartDashboard.putNumber("Right Motor Encoder Position",kDriveTrain.GetEncoderPosition('R'));
		
		SmartDashboard.putBoolean("Elbow EOT Up", Robot.kElbow.AtEndOfTravelUp());
		SmartDashboard.putBoolean("Elbow EOT Down", Robot.kElbow.AtEndOfTravelDown());
		SmartDashboard.putBoolean("Lift EOT Up", Robot.kLift.AtEndOfTravelUp());
		SmartDashboard.putBoolean("Lift EOT Down", Robot.kLift.AtEndOfTravelDown());
		SmartDashboard.putBoolean("Cube In Place", Robot.kSuckerPlooper.CubeInPlace());
		SmartDashboard.putNumber("Gyro Angle", kDriveTrain.getAngle());
		
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		//if (Robot.DEBUG_LEVEL>=1) System.out.println("robotInit Enter");
		
		Robot.kDriveTrain.resetGyro();
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);

		// For Testing
		//SmartDashboard.putData("Test Right Scale", new Auton_RightScale());
		//SmartDashboard.putData("Test Command 1", new AA_TEST_COMMAND(.3));
		//SmartDashboard.putData("Test Command 2", new AA_TEST_COMMAND2(.3));
		 
		m_chooser.addObject("Middle Deliver Switch", "Middle Switch");
		m_chooser.addObject("Right Deliver Scale", "Right Scale");
		m_chooser.addObject("Right Deliver Switch", "Right Switch");
		m_chooser.addObject("Right CONDITIONAL Delivery", "Right Delivery");
		m_chooser.addObject("Right POSITION Scale", "Right Position Scale");
		m_chooser.addObject("Left Deliver Scale", "Left Scale");
		m_chooser.addObject("Left Deliver Switch", "Left Switch");
		m_chooser.addObject("Left CONDITIONAL Delivery", "Left Delivery");
		m_chooser.addObject("Left POSITION Scale", "Left Position Scale");
		m_chooser.addDefault("Cross Line", "Cross Line"); //changed default 4/27/18 to cross line in case boot up changes it
															// so at least we cross line.
		SmartDashboard.putData("Auto Mode", m_chooser);
		//if (Robot.DEBUG_LEVEL>=1) System.out.println("robotInit Exit");
	}

	
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}

		Robot.kDriveTrain.resetGyro();
		Robot.kDriveTrain.InitForTeleop();
		Robot.kDriveTrain.enableSafety();
		
		
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		UpdateDashboard();
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {

		Robot.m_FMS.UpdateGameData(); //Try one last time just in case disabled didn't update the data
		
		Robot.kDriveTrain.resetGyro();
		Robot.kDriveTrain.disableSafety();
		Robot.kDriveTrain.InitForAuton();
		Robot.kLift.InitForAuton();
		
		String autoSelected = m_chooser.getSelected();
		SmartDashboard.putString("Auton Mode Read : ", autoSelected);
		Robot.logDebug(autoSelected, 1);
	    switch (autoSelected) {
	    case "Middle Switch":
	    //Robot.logDebug("Our Switch Position : " + Character.toString(Robot.m_FMS.OurSwitchPos()), 1);
	    	if (Robot.m_FMS.OurSwitchPos()=='R') {
	    		m_autonomousCommand = new Auton_MiddleStartDeliverSwitchStraight();
	    		SmartDashboard.putString("Auton Command", "Middle Start Deliver R");
	    	} else if (Robot.m_FMS.OurSwitchPos()=='L') {
	    		m_autonomousCommand = new Auton_MiddleStartDeliverSwitchLeft();
	    		SmartDashboard.putString("Auton Command", "Middle Start Deliver L");
	    	} else {
	    		m_autonomousCommand = new Auton_CrossLine();
	    		SmartDashboard.putString("Auton Command", "Middle Start Cross Line");
	    	}
	    	break;
	    //case "Middle Exchange":
	    	//break;
	    case "Right Scale":
	    	if (Robot.m_FMS.ScalePos()=='R') {
	    		m_autonomousCommand = new Auton_RightScale();
	    		SmartDashboard.putString("Auton Command", "Right Start Deliver R Scale");
	    	} else if (Robot.m_FMS.ScalePos()=='L') {
	    		m_autonomousCommand = new Auton_RightStartLeftScale();
	    		SmartDashboard.putString("Auton Command", "Right Start Deliver L Scale");
	    	} else {
	    		SmartDashboard.putString("Auton Command", "Right Start Cross Line");
	    		m_autonomousCommand = new Auton_CrossLine();
	    	}
	    	break;
	    case "Right Switch":
	    	if (Robot.m_FMS.OurSwitchPos()=='R') {
	    		m_autonomousCommand = new Auton_RightStartRightSwitch();
	    	} else {
	    		m_autonomousCommand = new Auton_CrossLine();
	    	}
	    	break;
	    case "Right Delivery":
	    	if (Robot.m_FMS.ScalePos()=='R') {
	    		m_autonomousCommand = new Auton_RightScale();
	    	} else if (Robot.m_FMS.OurSwitchPos()=='R') {
	    		m_autonomousCommand = new Auton_RightStartRightSwitch();
	    	} else {
	    		m_autonomousCommand = new Auton_CrossLine();
	    	}
	    	break;
	    case "Right Position Scale":
	    	if (Robot.m_FMS.ScalePos()=='R') {
	    		m_autonomousCommand = new Auton_RightStartRightScalePositionOnly();
	    		SmartDashboard.putString("Auton Command", "Right Start Right Scale Position");
	    	} else if (Robot.m_FMS.ScalePos()=='L') {
	    		m_autonomousCommand = new Auton_RightStartLeftScalePositionOnly();
	    		SmartDashboard.putString("Auton Command", "Right Start Left Scale Position");
	    	}else {
	    		m_autonomousCommand = new Auton_CrossLine();
	    		SmartDashboard.putString("Auton Command", "Right Start Cross Line");
	    	}
	    	break;
	    case "Left Scale":
	    	if (Robot.m_FMS.ScalePos()=='R') {
	    		m_autonomousCommand = new Auton_LeftStartRightScale();
	    		//SmartDashboard.putString("Auton Command", "Left Start Deliver R Scale");
	    	} else if (Robot.m_FMS.ScalePos()=='L') {
	    		m_autonomousCommand = new Auton_LeftStartLeftScale();
	    		//SmartDashboard.putString("Auton Command", "Left Start Deliver L Scale");
	        } else {
	    		m_autonomousCommand = new Auton_CrossLine();
	    		//SmartDashboard.putString("Auton Command", "Left Start Cross Line");
	        }
	    	break;
	    case "Left Switch":
	    	if (Robot.m_FMS.OurSwitchPos()=='L') {
	    		m_autonomousCommand = new Auton_LeftStartLeftSwitch();
	    	} else {
	    		m_autonomousCommand = new Auton_CrossLine();
	    	}
	    	break;
	    case "Left Delivery":
	    	if (Robot.m_FMS.ScalePos()=='L') {
	    		m_autonomousCommand = new Auton_LeftStartLeftScale();
	    	} else if (Robot.m_FMS.OurSwitchPos()=='L') {
	    		m_autonomousCommand = new Auton_LeftStartLeftSwitch();
	    	} else {
	    		m_autonomousCommand = new Auton_CrossLine();
	    	}
	    	break;
	    case "Left Position Scale":
	    	if (Robot.m_FMS.ScalePos()=='R') {
	    		m_autonomousCommand = new Auton_LeftStartRightScalePositionOnly();
	    		SmartDashboard.putString("Auton Command", "Left Start Right Scale Position");
	    	} else if (Robot.m_FMS.ScalePos()=='L') {
	    		m_autonomousCommand = new Auton_LeftStartLeftScalePositionOnly();
	    		SmartDashboard.putString("Auton Command", "Left Start Left Scale Position");
	    	}else {
	    		m_autonomousCommand = new Auton_CrossLine();
	    		SmartDashboard.putString("Auton Command", "Left Start Cross Line");
	    	}
	    	break;
	    default:
	    	m_autonomousCommand = new Auton_CrossLine();
    		SmartDashboard.putString("Auton Command", "Default - Cross Line");
    		break;
	    }
		m_autonomousCommand.start();
		
		//m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.start();
		//}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		UpdateDashboard();
		Scheduler.getInstance().run();
	}


	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Robot.m_FMS.UpdateGameData();
		Scheduler.getInstance().run();
		UpdateDashboard();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
	
}
