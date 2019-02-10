package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_MiddleStartRightSwitchGrabCube extends CommandGroup {

    public Auton_MiddleStartRightSwitchGrabCube() {
    	addParallel(new AutonElbow_RaiseToMaxAndMaintainGroup());
    	addParallel(new AutonLift_LiftMaintainSwitchGroup());
    	addSequential(new Auton_Wait(.4));								// this is so we can move the lift up before it hits the switch maybe possibly?
    	addSequential(new Auton_DriveAnglePositionPower(0,14000,.65));	//this is based upon needing to crawl completely over
    	addSequential(new AutonDriveAngleTimePower(0,1,.25));
    	addParallel(new AutonDriveAngleTimePower(0,3,.15));
    	addSequential(new Auton_ElbowLowerToBottom(.7));
    	addSequential(new AutonSuckerPlooper_Ploop(.75, 1));
    	addSequential(new Auton_DriveAbsolutePowerTime(-.25, -.25, 2));
    	addSequential(new AutonDriveAngleTimePower(-35, 2, .35));		//untested new bit  
    	addSequential(new LiftTurnOffMaintain());
    }
}
