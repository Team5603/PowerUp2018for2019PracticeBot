package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_LeftStartRightScalePositionOnly extends CommandGroup {

    public Auton_LeftStartRightScalePositionOnly() {
    	addParallel(new AutonElbow_RaiseToMaxAndMaintainGroup());
    	addSequential(new Auton_DriveAnglePositionPower(0, 4000, .5));
    	addSequential(new Auton_DriveAnglePositionPower(0, 35000, 1));
    	addSequential(new Auton_DriveAnglePositionPower(0, 45000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(0, 48000, .18));
    	addSequential(new AutonTurnAngle(90, 3));
    	addSequential(new Auton_EncoderReset());
    	addSequential(new Auton_Wait(.1));
    	addSequential(new Auton_DriveAnglePositionPower(90, 4000, .5));
    	addSequential(new Auton_DriveAnglePositionPower(90, 29000, 1));
    	//addSequential(new ElbowTurnOffMaintain());
    	addParallel(new Auton_LiftMaintainLowScaleGroup());
    	addSequential(new Auton_DriveAnglePositionPower(90, 39000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(90, 42500, .18));
    	
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
    	
    	
    	
    }
}
