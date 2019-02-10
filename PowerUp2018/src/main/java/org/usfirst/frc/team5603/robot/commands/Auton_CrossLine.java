package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_CrossLine extends CommandGroup {

    public Auton_CrossLine() {
    	addSequential(new Auton_DriveAnglePositionPower(0, 19000, .45));
    	addSequential(new AutonDriveAngleTimePower(0, 2.5, .15));
    }
}
