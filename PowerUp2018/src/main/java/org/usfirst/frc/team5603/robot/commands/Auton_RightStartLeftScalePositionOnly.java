package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_RightStartLeftScalePositionOnly extends CommandGroup {

    public Auton_RightStartLeftScalePositionOnly() {
    	addParallel(new AutonElbow_RaiseToMaxAndMaintainGroup());
    	addSequential(new Auton_DriveAnglePositionPower(0, 4000, .5));				// 3/20/2018 1:16PM
    	addSequential(new Auton_DriveAnglePositionPower(0, 35000, 1));				// yet to be properly tested
    	addSequential(new Auton_DriveAnglePositionPower(0, 45000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(0, 48000, .18));
    	addSequential(new AutonTurnAngle(-90, 3));
    	addSequential(new Auton_EncoderReset());
    	addSequential(new Auton_Wait(.1));
    	addSequential(new Auton_DriveAnglePositionPower(-89, 4000, .5));
    	addSequential(new Auton_DriveAnglePositionPower(-89, 27000, 1));
    	//addSequential(new ElbowTurnOffMaintain());
    	addParallel(new Auton_LiftMaintainLowScaleGroup());
    	addSequential(new Auton_DriveAnglePositionPower(-89, 37000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(-89, 40500, .18));
    	
    	
    	
    	/*
    	addSequential(new AutonTurnAngle(0, 3));
    	addSequential(new AutonLift_LiftToMax(12, 1));	
    	addParallel(new AutonLift_LiftMaintainScaleGroup());
    	addSequential(new Auton_EncoderReset());
    	addSequential(new Auton_Wait(.1));
    	addSequential(new Auton_DriveAnglePositionPower(0, 9000, .25));
    	addSequential(new Auton_ElbowLowerToBottom(1.5));
    	addSequential(new AutonSuckerPlooper_Ploop(.75, .75));
		*/
    	
    	
    	
    	
    	
    	
    	addSequential(new Auton_DriveAnglePositionPower(-2.5, 28000, 1));
    	addSequential(new Auton_DriveAnglePositionPower(-2.5, 41000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(-2.5, 44000, .18));
    	addSequential(new AutonTurnAngle(-90, 3));
    	addSequential(new Auton_DriveAnglePositionPower(-81, 66000, 1));
    	addSequential(new Auton_DriveAnglePositionPower(-81, 79000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(-81, 82500, .18));
    	//addParallel(new AutonLift_LiftMaintainScaleGroup());
    	addSequential(new AutonTurnAngle(0, 4));
    	/*
    	addSequential(new Auton_Wait(2.5));
    	addSequential(new Auton_DriveAnglePositionPower(0, 94340, .35));
    	addSequential(new Auton_DriveAnglePositionPower(0, 97340, .18));
    	addSequential(new Auton_Wait(.5));
    	addSequential(new AutonSuckerPlooper_Ploop(1));
    	addSequential(new Auton_Wait(.5));
    	addSequential(new AutonDriveAngleTimePower(0, 2, -.65));
    	addSequential(new LiftTurnOffMaintain());
		*/
    
    }
}
