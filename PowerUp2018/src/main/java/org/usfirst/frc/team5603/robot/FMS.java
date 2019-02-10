package org.usfirst.frc.team5603.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FMS {

	String gameData;

	public void FMS() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
	}
	
	public void UpdateGameData() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		SmartDashboard.putString("Game Data:", gameData);
	}
	
	public char OurSwitchPos() {
		if (gameData.length()>0)
			return gameData.charAt(0);
		else
			return ' ';
	}
	
	public char ScalePos() {
		if (gameData.length()>1)
			return gameData.charAt(1);
		else
			return ' ';
	}
	
	public char TheirSwitchPos() {
		if (gameData.length()>2)
			return gameData.charAt(2);
		else
			return ' ';
	}
}
