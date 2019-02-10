/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5603.robot;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
	/*
     * Can Bus Devices
     */
	// TODO : I noticed the other team groups their Device ID's together.  i.e. drive is 1-6, accessories are in the 50's.  should we
	//  rename the ID's to be 'not so random'?
    // Chassis Motors
	public static final int LEFT_DRIVE_MAIN_ID = 3;
    public static final int LEFT_DRIVE_SLAVE_ID = 6;
    public static final int RIGHT_DRIVE_MAIN_ID = 2;
    public static final int RIGHT_DRIVE_SLAVE_ID = 5;

    // Elevator Motor
    public static final int LIFT_MOTOR_ID = 4;

    // Elbow Motor
    public static final int ELBOW_MOTOR_ID = 1; //CHANGE BACK TO 1 on PRODUCTION, 11 is temp elbow from Darryl
    
    /*
     * PWM Devices
     */
    // Intake Motors
    public static final int SUCKERPLOOPER_LEFT_PWM = 0;
    public static final int SUCKERPLOOPER_RIGHT_PWM = 1;
    
    // Climber Motor
    //public static final int CLIMBER_PWM = 3;
    public static final int CLIMBER1_ID = 15;
    public static final int CLIMBER2_ID = 16;

    //Digital Inputs / Limit Switches
    // Sucker Plooper
    public static final int SUCKERPLOOPER_CUBE_IN_PLACE_LSID = 3;  //LSID = Limit Switch ID (Digital Input ID) 
    public static final boolean SUCKERPLOOPER_CUBE_IN_PLACE_DS = true;  //Default State 
    
    // Elbow
    public static final int ELBOW_END_OF_TRAVEL_UP_LSID = 1;
    public static final boolean ELBOW_END_OF_TRAVEL_UP_DS = true;
    public static final int ELBOW_END_OF_TRAVEL_DOWN_LSID = 2;
    public static final boolean ELBOW_END_OF_TRAVEL_DOWN_DS = true;
    
    //Lift
    public static final int LIFT_END_OF_TRAVEL_UP_LSID = 4;
    public static final boolean LIFT_END_OF_TRAVEL_UP_DS = true; //Default State
    public static final int LIFT_END_OF_TRAVEL_DOWN_LSID = 0;
    public static final boolean LIFT_END_OF_TRAVEL_DOWN_DS = true; //Default state
    
    //Climber
    // Potential for Encoder here, or limit switches, etc. 
   
    //ALT Chassis - on Test Board (Sparks)
    public static final int LEFT_DRIVE_PWM = 8;
    public static final int RIGHT_DRIVE_PWM = 9;
    
    public static final int BUDDY_BAR_MOTOR_PWM = 2;
    
}
