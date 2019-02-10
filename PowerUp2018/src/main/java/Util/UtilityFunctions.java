package Util;

import org.usfirst.frc.team5603.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

public class UtilityFunctions {
	public static double AngleDiff(double TargetAngle) {
		//Return the angle difference between our current angle and our target angle
		// positive means turn clockwise to get to it, negative is counter clockwise
		// so if we are at 350 degrees and out target is 45 degrees, then it would return 55 degrees (turn clockwise 55 degrees)
		// if we are at 180 degrees and our target is 45, then it would return -135.  This will never return a value higher than 180
		// or lower than -180
		double SensorAngle; 
		SensorAngle = Get0_360Angle();
		if (TargetAngle<SensorAngle) {
			if ((SensorAngle-TargetAngle)<=180) 
				//Go CCW angle is Sensor - target
				return -(SensorAngle-TargetAngle);
			else 
				//Go CW angle is Target + 360 - SensorAngle
				return TargetAngle+360-SensorAngle;
		} else if (TargetAngle>SensorAngle) {
			if ((TargetAngle-SensorAngle)<=180) 
				//Go CW angle is Sensor - target
				return (TargetAngle-SensorAngle);
			else 
				//Go CCW angle is SensorAngle+360-TargetAngle
				return (SensorAngle+360-TargetAngle);
			
		} else return 0;
	}

    private static double Get0_360Angle() {
		/*
		float SensorAngle;
		SensorAngle = Robot.kDriveTrain.getAngle();
		if (SensorAngle<0) SensorAngle = 360+SensorAngle;
		//Robot.logDebug("GyroAngle:" + Float.toString(SensorAngle),1);
		return (double)SensorAngle;
		 */
		float SensorAngle;
		SensorAngle = Robot.kDriveTrain.getAngle();
		SensorAngle = SensorAngle % 360; // Modulus 360, so now it returns 0 - 359.999 or 0 - -359.99
		if (SensorAngle<0) SensorAngle = 360+SensorAngle;
		//Robot.logDebug("GyroAngle:" + Float.toString(SensorAngle),1);
		return (double)SensorAngle;
	}
    
    public static int GetMotorSpeed(BaseMotorController thisTalon) {
		int returnValue=0;
		
		returnValue = thisTalon.getSelectedSensorVelocity(0);

		return returnValue;
    }

    public static int GetEncoderPosition(BaseMotorController thisTalon) {
		int returnValue=0;
		
		returnValue = thisTalon.getSelectedSensorPosition(0);

		return returnValue;
    }
   
    public static double GetMotorCurrent( BaseMotorController thisTalon) {
		double returnValue=0;

		//This seems to have been changed in 2019 to not visible?  Just returning 0 for current for now it's not needed for functionality of the robot
		//returnValue = thisTalon.getOutputCurrent();
		returnValue = 0;

    	return returnValue;
    	
    }


}

