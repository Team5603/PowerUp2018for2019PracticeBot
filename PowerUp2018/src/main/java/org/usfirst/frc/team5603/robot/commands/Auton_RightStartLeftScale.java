package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_RightStartLeftScale extends CommandGroup {

    public Auton_RightStartLeftScale() {
    	addParallel(new AutonElbow_RaiseToMaxAndMaintainGroup());
    	addSequential(new Auton_DriveAnglePositionPower(0, 4000, .5));				// 3/20/2018 1:16PM
    	addSequential(new Auton_DriveAnglePositionPower(0, 35000, 1));				// yet to be properly tested
    	addSequential(new Auton_DriveAnglePositionPower(0, 45000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(0, 48000, .18));
    	addSequential(new AutonTurnAngle(-90, 3));
    	addSequential(new Auton_EncoderReset());
    	addSequential(new Auton_Wait(.1));
    	addSequential(new Auton_DriveAnglePositionPower(-89, 4000, .5));
    	addSequential(new Auton_DriveAnglePositionPower(-89, 29000, 1));
    	//addSequential(new ElbowTurnOffMaintain());
    	addSequential(new Auton_DriveAnglePositionPower(-89, 39000, .2));
    	addSequential(new Auton_DriveAnglePositionPower(-89, 41800, .18));
    	addParallel(new AutonLift_LiftMaintainScaleGroup());
    	addSequential(new AutonTurnAngle(0, 3));
    	addSequential(new AutonLift_LiftToMax(12, 1));	
    	addParallel(new AutonLift_LiftMaintainScaleGroup());
    	addSequential(new Auton_EncoderReset());
    	addSequential(new Auton_Wait(.1));
    	addSequential(new Auton_DriveAnglePositionPower(0, 9000, .25));
    	addSequential(new Auton_ElbowLowerToBottom(.7));
    	addSequential(new AutonSuckerPlooper_Ploop(.5, .75));
    	addSequential(new Auton_DriveAbsolutePowerTime(-.25, -.25, 2));

    }
}
