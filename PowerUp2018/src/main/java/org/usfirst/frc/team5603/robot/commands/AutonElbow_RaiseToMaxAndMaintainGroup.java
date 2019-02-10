package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonElbow_RaiseToMaxAndMaintainGroup extends CommandGroup {

    public AutonElbow_RaiseToMaxAndMaintainGroup() {
    	addSequential(new Auton_ElbowRaiseToMax(3.0,1.0));	
    	addSequential(new Auton_ElbowMaintain());
    }
}
