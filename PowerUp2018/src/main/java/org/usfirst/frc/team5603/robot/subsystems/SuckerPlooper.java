package org.usfirst.frc.team5603.robot.subsystems;

import org.usfirst.frc.team5603.robot.Robot;
import org.usfirst.frc.team5603.robot.RobotMap;
import org.usfirst.frc.team5603.robot.commands.LiftOperateJoystick;
import org.usfirst.frc.team5603.robot.commands.SuckerPlooperOperateTrigger;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class SuckerPlooper extends PIDSubsystem {

	private Spark m_suckerplooperLeftMotor; 
	private Spark m_suckerplooperRightMotor; 
	
	private DigitalInput cubeInPlaceDI;
	
	private Timer m_timer = new Timer();
	private boolean m_limitLastState;
	
	private static final double SUCKER_POWER=.8;
	private static final double PLOOPER_POWER_LOW=.3;
	private static final double PLOOPER_POWER_HIGH=1.0;
	private static final double CUBE_IN_PLACE_DELAY_TIME=1;
	
    // Initialize your subsystem here
    public SuckerPlooper() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super(0,0,0);  //Not currently using PID, so just initialize it as 0's;
    	
    	//Only initialize this if we are not in chassis only mode
		m_suckerplooperLeftMotor = new Spark(RobotMap.SUCKERPLOOPER_LEFT_PWM);
		m_suckerplooperRightMotor = new Spark(RobotMap.SUCKERPLOOPER_RIGHT_PWM);
		cubeInPlaceDI = new DigitalInput(RobotMap.SUCKERPLOOPER_CUBE_IN_PLACE_LSID);
    	m_timer.reset();
    	m_limitLastState = CubeInPlace();
    }

    public boolean CubeInPlace()
    {
    	return cubeInPlaceDI.get()!=RobotMap.SUCKERPLOOPER_CUBE_IN_PLACE_DS;
    }
    
    public void Suck() {
    	// check if limit switch changed states.  If it went to "IN PLACE" then keep track of elapsed time
    	// only stop after cube in place timeout
    	boolean lCubeInPlace = CubeInPlace();
    	if (lCubeInPlace!=m_limitLastState) {
    		//Change in state
    		m_timer.stop(); // WE CHANGED STATE SO REGARDLESS RESET TIMER
    		m_timer.reset();
    		m_limitLastState = CubeInPlace();
    		if (lCubeInPlace)
    			//so it is 'IN PLACE', start timer
    			m_timer.start();
    	}
    	//Robot.logDebug(Boolean.toString(cubeInPlace.get()) + " - " + Double.toString(m_timer.get()), 1);
    	if ((!CubeInPlace()) || (m_timer.get()<CUBE_IN_PLACE_DELAY_TIME)) {
        	m_suckerplooperLeftMotor.set(-SUCKER_POWER);
        	m_suckerplooperRightMotor.set(SUCKER_POWER); // Need to spin in Opposite directions!!
    	} else
    		Stop();
    }
    public void SuckVariable(double suckPower) {
    	// check if limit switch changed states.  If it went to "IN PLACE" then keep track of elapsed time
    	// only stop after cube in place timeout
    	boolean lCubeInPlace = CubeInPlace();
    	if (lCubeInPlace!=m_limitLastState) {
    		//Change in state
    		m_timer.stop(); // WE CHANGED STATE SO REGARDLESS RESET TIMER
    		m_timer.reset();
    		m_limitLastState = CubeInPlace();
    		if (lCubeInPlace)
    			//so it is 'IN PLACE', start timer
    			m_timer.start();
    	}
    	//Robot.logDebug(Boolean.toString(cubeInPlace.get()) + " - " + Double.toString(m_timer.get()), 1);
    	if ((!CubeInPlace()) || (m_timer.get()<CUBE_IN_PLACE_DELAY_TIME)) {
        	//Robot.logDebug("Suck Variable :  " + suckPower, 1);
        	m_suckerplooperLeftMotor.set(-suckPower);
        	m_suckerplooperRightMotor.set(suckPower); // Need to spin in Opposite directions!!
    	} else
    		Stop();
    }
    public void PloopLow() {
    	//Reset the time if we exhaust
    	m_limitLastState = false;
    	m_timer.stop();
    	m_timer.reset();
    	m_suckerplooperLeftMotor.set(PLOOPER_POWER_LOW);
    	m_suckerplooperRightMotor.set(-PLOOPER_POWER_LOW);
    }
    
    public void PloopHigh() {
    	//Reset the time if we exhaust
    	m_limitLastState = false;
    	m_timer.stop();
    	m_timer.reset();
    	m_suckerplooperLeftMotor.set(PLOOPER_POWER_HIGH);
    	m_suckerplooperRightMotor.set(-PLOOPER_POWER_HIGH);
    }
    public void PloopPower(double ploopPower) {
    	//Reset the time if we exhaust
    	m_limitLastState = false;
    	m_timer.stop();
    	m_timer.reset();
    	m_suckerplooperLeftMotor.set(ploopPower);
    	m_suckerplooperRightMotor.set(-ploopPower);
    }
    public void Stop() {
    	m_suckerplooperLeftMotor.set(0);
    	m_suckerplooperRightMotor.set(0);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new SuckerPlooperOperateTrigger());
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
