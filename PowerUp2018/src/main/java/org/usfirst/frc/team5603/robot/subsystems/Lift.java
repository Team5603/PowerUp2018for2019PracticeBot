package org.usfirst.frc.team5603.robot.subsystems;

import org.usfirst.frc.team5603.robot.Robot;
import org.usfirst.frc.team5603.robot.RobotMap;
import org.usfirst.frc.team5603.robot.commands.LiftOperateJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import Util.UtilityFunctions;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lift extends PIDSubsystem {

	//Motor Controller
    private TalonSRX liftMotorTalon;
    //Limit Switches
    private DigitalInput liftEOTUp; //EOT = End of Travel
	private DigitalInput liftEOTDown;
	
	//Other
	private static final double RAISE_MULTIPLIER = .95;			// .75 ORIGINALLY 4/12/2018
	private static final double LOWER_MULTIPLIER = .6;  
	private static final double MAINTAIN_POWER = .15;
	private static final double LOWER_CLIMB_POWER = -.35; // Per Jacobs 2/20/18 from .25 to .35
	
    private static final double HIGH_POW = 1.0;
    private static final double LOW_POW = -HIGH_POW;
	
	private boolean m_maintain=false;

	// Initialize your subsystem here
    public Lift() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super(0,0,0);  //Not currently using PID, so just initialize it as 0's;
    	
    	// Inits
    	// Talon motor controller
		liftMotorTalon = new TalonSRX(RobotMap.LIFT_MOTOR_ID);

		liftMotorTalon.configPeakOutputForward(HIGH_POW, 0);
		liftMotorTalon.configPeakOutputReverse(LOW_POW, 0);
		liftMotorTalon.configNominalOutputForward(0.0, 0);
		liftMotorTalon.configNominalOutputReverse(0.0, 0);

        //Inverted or Not...
		liftMotorTalon.setInverted(false);
		
        //Encoder
        liftMotorTalon.setSensorPhase(true);
		liftMotorTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        liftMotorTalon.setSelectedSensorPosition(0, 0, 0);
        
        //Break Mode
		liftMotorTalon.setNeutralMode(NeutralMode.Brake);

		//Limit Switches
    	liftEOTUp = new DigitalInput(RobotMap.LIFT_END_OF_TRAVEL_UP_LSID);
    	liftEOTDown = new DigitalInput(RobotMap.LIFT_END_OF_TRAVEL_DOWN_LSID);
    	
    	m_maintain = false;
    	
    	/*
    	SmartDashboard.putNumber("Lift Raise output", m_raisePower);
    	SmartDashboard.putNumber("Lift Lower output", m_lowerPower);
    	SmartDashboard.putNumber("Lift Maintain output", m_maintainPower);
    	*/
    
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
    	if (goingUp) {
    		// Moving in upware direction
        	if (!AtEndOfTravelUp()) 
            	liftMotorTalon.set(ControlMode.PercentOutput, raiselowerPower*RAISE_MULTIPLIER);
        	else
        	{
        		if (m_maintain)
        			liftMotorTalon.set(ControlMode.PercentOutput, MAINTAIN_POWER);
        		else
        			liftMotorTalon.set(ControlMode.PercentOutput, 0);
        	}
    	} else {
    		// Moving down or stopping
    		if (GetEncoderPosition()<800) {
    			liftMotorTalon.set(ControlMode.PercentOutput, 0);
    		} else {
	        	if (!AtEndOfTravelDown()) 
	            	liftMotorTalon.set(ControlMode.PercentOutput, raiselowerPower*LOWER_MULTIPLIER);
	        	else
	        		liftMotorTalon.set(ControlMode.PercentOutput, 0);
    		}
    	}
    }
    
    public void Maintain() {
    	if (!AtEndOfTravelDown())  // Removed checking for at end of travel up because now if we are at end of travel up, we will maintain to prevent it from dropping
    		if (m_maintain)
    			liftMotorTalon.set(ControlMode.PercentOutput, MAINTAIN_POWER);
    		else
    			liftMotorTalon.set(ControlMode.PercentOutput, 0);
    	else
    		liftMotorTalon.set(ControlMode.PercentOutput, 0);
    }

    public void LowerClimb() {
       	if (!AtEndOfTravelDown()) 
       		liftMotorTalon.set(ControlMode.PercentOutput, LOWER_CLIMB_POWER);
       	else
       		liftMotorTalon.set(ControlMode.PercentOutput, 0);
    	
    }
    
    public void Stop() {
    	liftMotorTalon.set(ControlMode.PercentOutput, 0);
    }
    
    public boolean AtEndOfTravelUp() {
    	return (liftEOTUp.get()!=RobotMap.LIFT_END_OF_TRAVEL_UP_DS);
    }

    public boolean AtEndOfTravelDown() {
    	return (liftEOTDown.get()!=RobotMap.LIFT_END_OF_TRAVEL_DOWN_DS);
    }

    public int GetMotorSpeed() {
    	return UtilityFunctions.GetMotorSpeed(liftMotorTalon);
    }

    public int GetEncoderPosition() {
    	return UtilityFunctions.GetEncoderPosition(liftMotorTalon);
    }
   
    public double GetMotorCurrent() {
    	return UtilityFunctions.GetMotorCurrent(liftMotorTalon);
    }
    public void resetEncoders() {
		// For now this only gets called when initializing talons
        this.liftMotorTalon.setSelectedSensorPosition(0, 0, 0);
    }

    public void InitForAuton() {
        //Inverted or Not...
        resetEncoders();
   }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new LiftOperateJoystick());
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
