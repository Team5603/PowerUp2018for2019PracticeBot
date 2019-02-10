package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_LeftStartLeftSwitch extends CommandGroup {

    public Auton_LeftStartLeftSwitch() {
    	addParallel(new AutonElbow_RaiseToMaxAndMaintainGroup());
    	addParallel(new AutonLift_LiftMaintainSwitchGroup());
    	addSequential(new Auton_Wait(.5));
    	addSequential(new Auton_DriveAnglePositionPower(0, 22000, .45));
    	addSequential(new AutonDriveAngleTimePower(0, 2, .15));
    	addSequential(new AutonTurnAngle(90, 3));
    	addParallel(new AutonDriveAngleTimePower(90, 3, .25));
    	addSequential(new Auton_Wait(1));
    	addSequential(new Auton_ElbowLowerToBottom(.7));
    	addSequential(new AutonSuckerPlooper_Ploop(.75, .7));
    	addSequential(new Auton_DriveAbsolutePowerTime(-.25, -.25, 2));
    	addSequential(new LiftTurnOffMaintain());
    }
}
