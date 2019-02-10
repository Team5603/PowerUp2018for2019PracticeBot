package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLift_LiftMaintainScaleGroup extends CommandGroup {

    public AutonLift_LiftMaintainScaleGroup() {
    	addSequential(new AutonLift_LiftToMax(12, 1));	
    	addSequential(new AutonLift_Maintain());
    }
}
