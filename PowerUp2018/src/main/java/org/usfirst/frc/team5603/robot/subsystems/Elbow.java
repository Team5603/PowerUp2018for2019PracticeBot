package org.usfirst.frc.team5603.robot.subsystems;

import org.usfirst.frc.team5603.robot.Robot;
import org.usfirst.frc.team5603.robot.RobotMap;
import org.usfirst.frc.team5603.robot.commands.ElbowOperateJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import Util.UtilityFunctions;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Elbow extends PIDSubsystem {

	//Motor Controller
    private TalonSRX elbowMotorTalon;
    //Limit Switches
    private DigitalInput elbowEOTUp; //EOT = End of Travel
	private DigitalInput elbowEOTDown;
	
	//Other
	private static final double RAISE_MULTIPLIER = .35;
	private static final double LOWER_MULTIPLIER = .13;
	private static final double MAINTAIN_POWER = .08;
	
    private static final double HIGH_POW = 0.7;
    private static final double LOW_POW = -0.25;
	
	private boolean m_maintain=true;

    // Initialize your subsystem here
    public Elbow() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super(0,0,0);  //Not currently using PID, so just initialize it as 0's;
    	// Inits
    	// Talon motor controller
		elbowMotorTalon = new TalonSRX(RobotMap.ELBOW_MOTOR_ID);

		elbowMotorTalon.configPeakOutputForward(HIGH_POW, 0);
		elbowMotorTalon.configPeakOutputReverse(LOW_POW, 0);
		elbowMotorTalon.configNominalOutputForward(0.0, 0);
		elbowMotorTalon.configNominalOutputReverse(0.0, 0);

        //Inverted or Not...
		elbowMotorTalon.setInverted(true);
		
        //Encoder
        elbowMotorTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        elbowMotorTalon.setSelectedSensorPosition(0, 0, 0);
        
        //Break Mode
		elbowMotorTalon.setNeutralMode(NeutralMode.Brake);

		//Limit Switches
    	elbowEOTUp = new DigitalInput(RobotMap.ELBOW_END_OF_TRAVEL_UP_LSID);
    	elbowEOTDown = new DigitalInput(RobotMap.ELBOW_END_OF_TRAVEL_DOWN_LSID);
    	
    	m_maintain = true;
    }

    public void TurnOffMaintain() {
    	m_maintain = false;
    }
    
    public void TurnOnMaintain() {
    	m_maintain = true;
    }

    public boolean GetMaintain( ) {
    	return m_maintain;
    }

    public void Raise_Lower(double raiselowerPower) {
    	// Positive value is UP, negative Value is down
    	boolean goingUp = (raiselowerPower>0);
		//Robot.logDebug("Elbow Raise/Lower - AtEndOfTravel : " + Boolean.toString(AtEndOfTravelUp()) + " - Raise/Lower : " + raiselowerPower , 1);
    	if (goingUp) {
    		// Moving in upward direction
        	//Robot.logDebug("EOT UP : " + Boolean.toString(AtEndOfTravelUp()) + " - " + Double.toString(raiselowerPower*RAISE_MULTIPLIER) , 1);
        	if (!AtEndOfTravelUp()) 
            	elbowMotorTalon.set(ControlMode.PercentOutput, raiselowerPower*RAISE_MULTIPLIER);
        	else
        	{
        		if(m_maintain)
            		elbowMotorTalon.set(ControlMode.PercentOutput, MAINTAIN_POWER);
        		else
            		elbowMotorTalon.set(ControlMode.PercentOutput, 0);
        	}
    	} else {
    		// Moving down or stopping
        	//Robot.logDebug("EOT DOWN : " + Boolean.toString(AtEndOfTravelUp()) + " - " + Double.toString(raiselowerPower*RAISE_MULTIPLIER) , 1);
        	if (!AtEndOfTravelDown()) 
            	elbowMotorTalon.set(ControlMode.PercentOutput, raiselowerPower*LOWER_MULTIPLIER);
        	else
        		elbowMotorTalon.set(ControlMode.PercentOutput, 0);
    	}
    }

    public void Maintain() {
    	
		//Robot.logDebug("Elbow Maintain - AtEndOfTravel : " + Boolean.toString(AtEndOfTravelUp()) , 1);
    	// only going to turn off when at end of travel down.  When at end of travel up, we WILL maintain.
		if (!AtEndOfTravelDown())
    		if (m_maintain)
    			elbowMotorTalon.set(ControlMode.PercentOutput, MAINTAIN_POWER);
    		else
    			elbowMotorTalon.set(ControlMode.PercentOutput, 0);
    	else 
    		elbowMotorTalon.set(ControlMode.PercentOutput, 0);
    }

    public void Stop() {
    	elbowMotorTalon.set(ControlMode.PercentOutput, 0);
    }

    public boolean AtEndOfTravelUp() {
    	return (elbowEOTUp.get()!=RobotMap.ELBOW_END_OF_TRAVEL_UP_DS);
    }

    public boolean AtEndOfTravelDown() {
    	return (elbowEOTDown.get()!=RobotMap.ELBOW_END_OF_TRAVEL_DOWN_DS);
    }

    public int GetMotorSpeed() {
    	return UtilityFunctions.GetMotorSpeed(elbowMotorTalon);
    }

    public int GetEncoderPosition() {
    	return UtilityFunctions.GetEncoderPosition(elbowMotorTalon);
    }
   
    public double GetMotorCurrent() {
    	return UtilityFunctions.GetMotorCurrent(elbowMotorTalon);
    }

    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new ElbowOperateJoystick()); // Only operate elbow if full robot, not chassis only mode
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return 0.0;
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    }
}
