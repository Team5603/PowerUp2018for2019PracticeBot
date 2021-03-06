package org.usfirst.frc.team5603.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonTestDrive extends CommandGroup {

    public AutonTestDrive() {
    	
    	addParallel(new AutonLift_LiftMaintainSwitchGroup());

    	addSequential(new AutonDriveAngleTimePower(0, .5, .25));
    	addSequential(new AutonTurnAngle(-60, 1.5));
    	addSequential(new Auton_DriveAnglePositionPower(-60, 26000, .5));			//1.75
    	
    	
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
