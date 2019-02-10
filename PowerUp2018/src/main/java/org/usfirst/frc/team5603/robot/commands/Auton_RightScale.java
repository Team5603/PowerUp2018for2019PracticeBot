package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_RightScale extends CommandGroup {

    public Auton_RightScale() {
    	addParallel(new AutonElbow_RaiseToMaxAndMaintainGroup());
    	addSequential(new Auton_DriveAnglePositionPower(3.5, 4000, .5));	
    	addSequential(new Auton_DriveAnglePositionPower(3.5, 53000, 1));			
    	addSequential(new Auton_DriveAnglePositionPower(3.5, 63000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(3.5, 66500, .18));
    	addParallel(new AutonLift_LiftMaintainScaleGroup());
    	addSequential(new Auton_Wait(.5));
    	addSequential(new AutonTurnAngle(-90, 3));
    	addSequential(new Auton_EncoderReset());
    	addSequential(new AutonLift_LiftToMax(12, 1));	
    	addParallel(new AutonLift_LiftMaintainScaleGroup());
    	addSequential(new Auton_DriveAnglePositionPower(-90, 5000, .25));
    	addSequential(new Auton_ElbowLowerToBottom(1.5));
    	addSequential(new AutonSuckerPlooper_Ploop(.75, .75));
    	addSequential(new Auton_DriveAbsolutePowerTime(-.25, -.25, 2));

    }
}
