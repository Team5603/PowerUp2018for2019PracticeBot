package org.usfirst.frc.team5603.robot.subsystems;

import org.usfirst.frc.team5603.robot.Robot;
import org.usfirst.frc.team5603.robot.RobotMap;
import org.usfirst.frc.team5603.robot.commands.Climber_Maintain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Climber extends PIDSubsystem {

	
	//private Spark m_climberMotor;
	private TalonSRX m_climberMotor1;
	private TalonSRX m_climberMotor2;

	private static final double CLIMBER_POWER_UP = 1; // Per Jacobs 2/20/18 .75 to .90
	private static final double CLIMBER_POWER_DOWN = .4; 
	private static final double HIGH_POW = 1.0;
	private static final double LOW_POW = -HIGH_POW;
    private static final double MAINTAIN_POWER = .15;
    
	private boolean m_maintain=false;
	
	// Initialize your subsystem here
    public Climber() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super(0,0,0);
    	
		//m_climberMotor = new Spark(RobotMap.CLIMBER_PWM);
    	m_climberMotor1 = new TalonSRX(RobotMap.CLIMBER1_ID);
    	m_climberMotor2 = new TalonSRX(RobotMap.CLIMBER2_ID);


    	m_climberMotor1.configPeakOutputForward(HIGH_POW, 0);
    	m_climberMotor1.configPeakOutputReverse(LOW_POW, 0);
    	m_climberMotor1.configNominalOutputForward(0.0, 0);
    	m_climberMotor1.configNominalOutputReverse(0.0, 0);
    	
    	m_climberMotor2.configPeakOutputForward(HIGH_POW, 0);
    	m_climberMotor2.configPeakOutputReverse(LOW_POW, 0);
    	m_climberMotor2.configNominalOutputForward(0.0, 0);
    	m_climberMotor2.configNominalOutputReverse(0.0, 0);

        //Inverted or Not...
    	m_climberMotor1.setInverted(true);
    	m_climberMotor2.setInverted(true);
      
        
        //Break Mode
    	m_climberMotor1.setNeutralMode(NeutralMode.Brake);
    	m_climberMotor2.setNeutralMode(NeutralMode.Brake);

		
    	m_maintain = false;
    }

    public void Climb() {
    	//Robot.logDebug("Climber Climb", 1);
    	//m_climberMotor.set(CLIMBER_POWER_UP);
    	m_climberMotor1.set(ControlMode.PercentOutput,CLIMBER_POWER_UP);
    	m_climberMotor2.set(ControlMode.PercentOutput,CLIMBER_POWER_UP);
    	// Put some code in here to slightly drive the lift as well!!!
    	
    	m_maintain = true;
    }
    
    public void Descend() {
    	//m_climberMotor.set(-CLIMBER_POWER_DOWN);
    	m_climberMotor1.set(ControlMode.PercentOutput,-CLIMBER_POWER_DOWN);
    	m_climberMotor2.set(ControlMode.PercentOutput,-CLIMBER_POWER_DOWN);
    	
    	m_maintain=false;
    	
    }
    
    public void Maintain() {
    	m_climberMotor1.set(ControlMode.PercentOutput,MAINTAIN_POWER);
    	m_climberMotor2.set(ControlMode.PercentOutput,MAINTAIN_POWER);
    }

    public boolean IsMaintained()
    {
    	return m_maintain;
    }
    public void Stop() {
    	//m_climberMotor.set(0);
    	m_climberMotor1.set(ControlMode.PercentOutput,0);
    	m_climberMotor2.set(ControlMode.PercentOutput,0);
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Climber_Maintain());
    }
    
    public void test1(double motorOutput)
    {
    	m_climberMotor1.set(ControlMode.PercentOutput,motorOutput);

    
    }
    
    public void test2(double motorOutput)
    {
    	m_climberMotor2.set(ControlMode.PercentOutput,motorOutput);
    	
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
