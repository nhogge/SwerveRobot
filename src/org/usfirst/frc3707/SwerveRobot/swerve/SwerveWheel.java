package org.usfirst.frc3707.SwerveRobot.swerve;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;


public class SwerveWheel {
    private PIDController rotation;
    private SpeedController speed;
    private double offset;

    public SwerveWheel(PIDController rotation, SpeedController speed, double offset){
        this.rotation  = rotation;
        this.speed = speed;
        this.offset = offset;
    }
    
    public void drive(double newSpeed, double newAngle) {
    	updateSpeed(newSpeed);
    	updateRotation(newAngle);
    }

    public void updateSpeed(double newSpeed){
        speed.set(newSpeed);
    }

    public void updateRotation(double newAngle){
    	if(newAngle < 0) {
            rotation.setSetpoint(360 - (newAngle * -1) + offset);
    	}
    	else {
            rotation.setSetpoint(newAngle + offset);
    	}
    }

    public void disable() {
        rotation.disable();
    }

    public void enable() {
        rotation.enable();
    }
}
