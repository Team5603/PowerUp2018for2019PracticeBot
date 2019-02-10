package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonLift_LiftMaintainSwitchGroup extends CommandGroup {

    public AutonLift_LiftMaintainSwitchGroup() {
    	// This is the process that will lift and maintain the lift, we group this together so we can run it in parallel with other processes
    	// because maintain has to keep running to keep lift from falling
    	//addSequential(new AutonLift_LiftTimePower(2.75, .75));	//3/16/18 - 2:47 pm changed from 2.5/.65 to 2.6 and .75, faster and slightly longer
    	addSequential(new AutonLift_LiftToPosition(1, 13500, 6));
    	addSequential(new AutonLift_Maintain());
    }
}
