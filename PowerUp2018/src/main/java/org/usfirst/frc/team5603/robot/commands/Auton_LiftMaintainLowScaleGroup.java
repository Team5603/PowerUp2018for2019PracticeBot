package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_LiftMaintainLowScaleGroup extends CommandGroup {

    public Auton_LiftMaintainLowScaleGroup() {
    	addSequential(new AutonLift_LiftToPosition(1, 13500, 6));
    	addSequential(new AutonLift_Maintain());
    }
}
