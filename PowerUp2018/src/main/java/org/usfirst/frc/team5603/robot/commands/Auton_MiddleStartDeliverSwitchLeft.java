package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_MiddleStartDeliverSwitchLeft extends CommandGroup {

    public Auton_MiddleStartDeliverSwitchLeft() {
    	addParallel(new AutonElbow_RaiseToMaxAndMaintainGroup());
    	addParallel(new AutonLift_LiftMaintainSwitchGroup());
    	//addSequential(new AutonDriveAngleTimePower(0, .7, .35));		//.5
    	addSequential(new Auton_DriveAnglePositionPower(0, 1000, .5));
    	addSequential(new Auton_Wait(.5));
    	addSequential(new AutonTurnAngle(-60, 1.5));
    	addSequential(new Auton_EncoderReset());				// function currently in test
//    	addSequential(new AutonDriveAngleTimePower(-60, 1.85, .5));			//1.75
    	addSequential(new Auton_DriveAnglePositionPower(-60, 24000, .5));			//26000 originally
    	addSequential(new Auton_Wait(.5));
    	addSequential(new AutonTurnAngle(0, 1.5));
    	//addParallel(new Auton_DriveAnglePositionPower(0, 36200, .3)); //3/17/18 - 10:38 - made this parallel and commented out the 
    																	// the next command to make sure it ploops
    	addSequential(new AutonDriveAngleTimePower(0,1.5,.25));
    	//addParallel(new AutonDriveAngleTimePower(0, 5, .23));				//time was 2.15
    	//addSequential(new Auton_Wait(2.5));  // 3/17/18 10:38 increased wait time from 1 to 2.5 to make sure it approaches switch with
    													// parallel forward now
    	addSequential(new Auton_ElbowLowerToBottom(.7));
    	addSequential(new AutonSuckerPlooper_Ploop(.75, 1));
    	addSequential(new Auton_DriveAbsolutePowerTime(-.25, -.25, 2));
    	addSequential(new LiftTurnOffMaintain());


    }
}
