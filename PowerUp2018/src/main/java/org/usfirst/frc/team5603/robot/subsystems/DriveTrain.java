package org.usfirst.frc.team5603.robot.subsystems;

import org.usfirst.frc.team5603.robot.Robot;
import org.usfirst.frc.team5603.robot.RobotMap;
import org.usfirst.frc.team5603.robot.commands.TankDriveWithJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
//import com.kauailabs.navx.frc.AHRS;

import Util.UtilityFunctions;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrain extends PIDSubsystem {

	
	//Controllers
    private WPI_TalonSRX leftDriveMainTalon;
    private WPI_VictorSPX leftDriveSlaveVictor;
    private WPI_TalonSRX rightDriveMainTalon;
    private WPI_VictorSPX rightDriveSlaveVictor;

    //private Spark leftDriveSpark;
    //private Spark rightDriveSpark;
    
    private DifferentialDrive m_differentialDrive;  

    //AHRS gyro = new AHRS(I2C.Port.kMXP);
    private ADXRS450_Gyro gyro = new ADXRS450_Gyro();


    //CONSTANTS
    private static final double HIGH_POW = 1.0;
    private static final double LOW_POW = -HIGH_POW;

    private static final double NON_TURBO_MULTIPLIER = .8; // 4/25/18 5:17 pm changed from .7 to .8
    private static final double SLOW_MULTIPLIER = .4; // 4/25/18 5:17 pm changed from .35 to .4
    
    private static final double PID_F = 0; //Feed Forward
    private static final double PID_P = 0.8; //Feed Forward
    private static final double PID_I = 0; //Feed Forward
    private static final double PID_D = 0; //Feed Forward
    
    public static final double WHEEL_DIAMETER = 6; //Inches
    public static final int TICKS_PER_REV = 4096;
    //private static final double TIMEOUT_MS = 10;
    
    // Initialize your subsystem here
    public DriveTrain() {

    	// Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super(0,0,0);  //Not currently using PID, so just initialize it as 0's;

		initializeMotorControllers();

		Robot.logDebug("Calibrating Gyro...", 1);
    	gyro.calibrate();
		Robot.logDebug("Finished Calibrating Gyro...", 1);
        //Gyro
        resetGyro();
    }
    
    
    public void initializeMotorControllers() {

    	//Robot.logDebug("init Motor Controllers...",1);
    	
    	//Robot.logDebug("Controller type :" + Robot.CHASSIS_MOTOR_CONTROLLER_TYPE,1);
		leftDriveMainTalon = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_MAIN_ID);
        leftDriveSlaveVictor = new WPI_VictorSPX(RobotMap.LEFT_DRIVE_SLAVE_ID);
        rightDriveMainTalon = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_MAIN_ID);
        rightDriveSlaveVictor = new WPI_VictorSPX(RobotMap.RIGHT_DRIVE_SLAVE_ID);

        //Slave Control
        leftDriveSlaveVictor.follow(leftDriveMainTalon);
        rightDriveSlaveVictor.follow(rightDriveMainTalon);

        leftDriveMainTalon.configPeakOutputForward(HIGH_POW, 0);
        leftDriveMainTalon.configPeakOutputReverse(LOW_POW, 0);
        leftDriveMainTalon.configNominalOutputForward(0.0, 0);
        leftDriveMainTalon.configNominalOutputReverse(0.0, 0);
        leftDriveSlaveVictor.configPeakOutputForward(HIGH_POW, 0);
        leftDriveSlaveVictor.configPeakOutputReverse(LOW_POW, 0);
        leftDriveSlaveVictor.configNominalOutputForward(0.0, 0);
        leftDriveSlaveVictor.configNominalOutputReverse(0.0, 0);

        rightDriveMainTalon.configPeakOutputForward(HIGH_POW, 0);
        rightDriveMainTalon.configPeakOutputReverse(LOW_POW, 0);
        rightDriveMainTalon.configNominalOutputForward(0.0, 0);
        rightDriveMainTalon.configNominalOutputReverse(0.0, 0);
        rightDriveSlaveVictor.configPeakOutputForward(HIGH_POW, 0);
        rightDriveSlaveVictor.configPeakOutputReverse(LOW_POW, 0);
        rightDriveSlaveVictor.configNominalOutputForward(0.0, 0);
        rightDriveSlaveVictor.configNominalOutputReverse(0.0, 0);

        //Encoder
        leftDriveMainTalon.setSensorPhase(true);
        rightDriveMainTalon.setSensorPhase(true);
        leftDriveMainTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        rightDriveMainTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        
        //PID
        leftDriveMainTalon.config_kF(0, PID_F, 0);
        rightDriveMainTalon.config_kF(0, PID_F, 0);
        leftDriveMainTalon.config_kP(0, PID_P, 0);
        rightDriveMainTalon.config_kP(0, PID_P, 0);
        leftDriveMainTalon.config_kI(0, PID_I, 0);
        rightDriveMainTalon.config_kI(0, PID_I, 0);
        leftDriveMainTalon.config_kD(0, PID_D, 0);
        rightDriveMainTalon.config_kD(0, PID_D, 0);
        
        resetEncoders();
		enableBreakMode();
        
        m_differentialDrive = new DifferentialDrive(leftDriveMainTalon, rightDriveMainTalon);
    	
    }

    public void InitForAuton() {
        //Inverted or Not...
        leftDriveMainTalon.setInverted(false);
        leftDriveSlaveVictor.setInverted(false);
        rightDriveMainTalon.setInverted(true);
        rightDriveSlaveVictor.setInverted(true);
        //Encoder
        leftDriveMainTalon.setSensorPhase(true);
        rightDriveMainTalon.setSensorPhase(true);
        leftDriveMainTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        rightDriveMainTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        resetEncoders();
		enableBreakMode();
   }
    
    public void InitForTeleop() {
        //Inverted or Not...
        leftDriveMainTalon.setInverted(false);
        leftDriveSlaveVictor.setInverted(false);
        rightDriveMainTalon.setInverted(false);
        rightDriveSlaveVictor.setInverted(false);
        //Encoder
        leftDriveMainTalon.setSensorPhase(true);
        rightDriveMainTalon.setSensorPhase(true);
        leftDriveMainTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        rightDriveMainTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        resetEncoders();
		enableBreakMode();
    }
    
    public void enableBreakMode() {
		// For now this only gets called when initializing talons
		leftDriveMainTalon.setNeutralMode(NeutralMode.Brake);
		rightDriveMainTalon.setNeutralMode(NeutralMode.Brake);
    }

    public void disableBreakMode() {
		// For now this only gets called when initializing talons
		this.leftDriveMainTalon.setNeutralMode(NeutralMode.Coast);
		this.rightDriveMainTalon.setNeutralMode(NeutralMode.Coast);
    }

    public void resetEncoders() {
		// For now this only gets called when initializing talons
        this.leftDriveMainTalon.setSelectedSensorPosition(0, 0, 0);
        this.rightDriveMainTalon.setSelectedSensorPosition(0, 0, 0);
    }

    public float getAngle() {
        //return gyro.getYaw();
        return (float)gyro.getAngle();
    }

    public void resetGyro() {
        gyro.reset();
    }

    public void enableSafety() {
    	m_differentialDrive.setSafetyEnabled(true);
    }
    public void disableSafety() {
    	m_differentialDrive.setSafetyEnabled(false);
    }
    public void tankDrive(double leftPower, double rightPower) {
    	if (!(leftPower==0 && rightPower==0))
    		Robot.logDebug("TankDrive: "+ Double.toString(leftPower) + " - " + Double.toString(rightPower) + " - Turbo:" + Boolean.toString(Robot.m_oi.getTurbo()),3);
    	
    	if (Robot.m_oi.getSlow())
    		m_differentialDrive.tankDrive(leftPower*SLOW_MULTIPLIER, rightPower*SLOW_MULTIPLIER, false);
    	else if (Robot.m_oi.getTurbo()) 
    		m_differentialDrive.tankDrive(leftPower, rightPower, false);  // Turbo, run full power!
    	else
    		m_differentialDrive.tankDrive(leftPower*NON_TURBO_MULTIPLIER, rightPower*NON_TURBO_MULTIPLIER, false); // Not turbo and not slow, so non turbo speed
    }
    
    public void autonAbsoluteDrive(double leftPower, double rightPower) {
		leftDriveMainTalon.set(ControlMode.PercentOutput,leftPower);
		rightDriveMainTalon.set(ControlMode.PercentOutput,rightPower);
    }
    
    
    public void DriveToEncoderPositions(double LeftPos, double RightPos) {
    	// can only use this with Talon and encoders
		leftDriveMainTalon.set(ControlMode.Position, LeftPos );
		//Robot.logDebug("L : out:"+leftDriveMainTalon.getMotorOutputPercent() +"\tpos:"+leftDriveMainTalon.getSelectedSensorPosition(0)+"\terr:"+leftDriveMainTalon.getClosedLoopError(0)+"\ttar:"+LeftPos, 1);
		rightDriveMainTalon.set(ControlMode.Position, RightPos );
		//Robot.logDebug("R : out:"+rightDriveMainTalon.getMotorOutputPercent() +"\tpos:"+rightDriveMainTalon.getSelectedSensorPosition(0)+"\terr:"+rightDriveMainTalon.getClosedLoopError(0)+"\ttar:"+RightPos, 1);
    }
    
    public int GetMotorSpeed(char LeftOrRight) {
    	int returnValue=0;
    	if (LeftOrRight=='L')
			returnValue = UtilityFunctions.GetMotorSpeed(leftDriveMainTalon);
		else if (LeftOrRight=='R')
			returnValue = UtilityFunctions.GetMotorSpeed(rightDriveMainTalon);
    	
    	return returnValue;
    }

    public int GetEncoderPosition(char LeftOrRight) {
    	int returnValue=0;
    	if (LeftOrRight=='L')
			returnValue = UtilityFunctions.GetEncoderPosition(leftDriveMainTalon);
		else if (LeftOrRight=='R')
			returnValue = UtilityFunctions.GetEncoderPosition(rightDriveMainTalon);
    	
    	return returnValue;
    }
   
    public double GetMotorCurrent(char LeftOrRight) {
    	double returnValue=0;
    	if (LeftOrRight=='L')
			returnValue = UtilityFunctions.GetMotorCurrent(leftDriveMainTalon);
		else if (LeftOrRight=='R')
			returnValue = UtilityFunctions.GetMotorCurrent(rightDriveMainTalon);
    	
    	return returnValue;
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new TankDriveWithJoystick());
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
