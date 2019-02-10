package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_LeftStartLeftScale extends CommandGroup {

    public Auton_LeftStartLeftScale() {
    	addParallel(new AutonElbow_RaiseToMaxAndMaintainGroup());
    	addSequential(new Auton_DriveAnglePositionPower(-1, 4000, .5));
    	addSequential(new Auton_DriveAnglePositionPower(-1, 52000, 1));
    	addSequential(new Auton_DriveAnglePositionPower(-1, 63000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(-1, 66500, .18));
    	addParallel(new AutonLift_LiftMaintainScaleGroup());
    	addSequential(new Auton_Wait(.5));
    	addSequential(new AutonTurnAngle(90, 3));
    	addSequential(new Auton_EncoderReset());
    	addSequential(new AutonLift_LiftToMax(12, 1));	
    	addParallel(new AutonLift_LiftMaintainScaleGroup());
    	addSequential(new Auton_DriveAnglePositionPower(90, 3500, .25));
    	addSequential(new Auton_ElbowLowerToBottom(.7));
    	addSequential(new AutonSuckerPlooper_Ploop(.5, .75));   			//.75 orig
    	addSequential(new Auton_DriveAbsolutePowerTime(-.25, -.25, 2));
    	
    }
}
