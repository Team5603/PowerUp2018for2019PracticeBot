package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_RightStartRightScalePositionOnly extends CommandGroup {

    public Auton_RightStartRightScalePositionOnly() {
    	addParallel(new AutonElbow_RaiseToMaxAndMaintainGroup());
    	addSequential(new Auton_DriveAnglePositionPower(3.5, 4000, .5));	
    	addSequential(new Auton_DriveAnglePositionPower(3.5, 55000, 1));			
    	addSequential(new Auton_DriveAnglePositionPower(3.5, 65000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(3.5, 68500, .18));
    	addParallel(new Auton_LiftMaintainLowScaleGroup());
    	addSequential(new Auton_Wait(.5));
    	addSequential(new AutonTurnAngle(-90, 3));
    	
    	/*
    	addSequential(new Auton_EncoderReset());
    	addSequential(new AutonLift_LiftToMax(12, 1));	
    	addParallel(new AutonLift_LiftMaintainScaleGroup());
    	addSequential(new Auton_DriveAnglePositionPower(-90, 5000, .25));
    	addSequential(new Auton_ElbowLowerToBottom(1.5));
    	addSequential(new AutonSuckerPlooper_Ploop(.5, 1));
		
    	
    	
    	addSequential(new Auton_DriveAnglePositionPower(2.5, 50000, 1));
    	addSequential(new Auton_DriveAnglePositionPower(2.5, 60000, .35));
    	addSequential(new Auton_DriveAnglePositionPower(2.5, 65000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(2.5, 68500, .18));
    	//addParallel(new AutonLift_LiftMaintainScaleGroup());
    	addSequential(new AutonTurnAngle(-90, 4));
    	*/
    	/*
    	addSequential(new Auton_Wait(2.5));
    	addSequential(new Auton_DriveAnglePositionPower(-90, 68400, .18));
    	addSequential(new Auton_Wait(1));
    	addSequential(new AutonSuckerPlooper_Ploop(1));
    	*/
    }
}
