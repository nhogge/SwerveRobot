package org.usfirst.frc3707.SwerveRobot.swerve;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveDrive {

    private SwerveWheel rightFrontWheel;
    private SwerveWheel leftFrontWheel;
    private SwerveWheel leftBackWheel;
    private SwerveWheel rightBackWheel;
    
    private GyroBase gyro;

    private double wheelbase = 22.5;
    private double trackwidth = 24.5;

    public SwerveDrive(SwerveWheel rightFront, SwerveWheel leftFront, SwerveWheel leftBack, SwerveWheel rightBack, GyroBase gyro){
    	this.rightFrontWheel = rightFront;
    	this.leftFrontWheel = leftFront;
    	this.leftBackWheel = leftBack;
    	this.rightBackWheel = rightBack;
	    
        this.gyro = gyro;
    }
    
    public void drive(double directionX, double directionY, double rotation) {
    	
    	//SmartDashboard.putNumber("directionX", directionX);
    	//SmartDashboard.putNumber("directionY", directionY);
    	
    	//if the joystick in the center
    	if((directionX < 0.2 && directionX > -0.2) && (directionY < 0.2 && directionY > -0.2) && (rotation < 0.2 && rotation > -0.2)) {
    		this.rightFrontWheel.updateSpeed(0);
        	this.leftFrontWheel.updateSpeed(0);
        	this.leftBackWheel.updateSpeed(0);
        	this.rightBackWheel.updateSpeed(0);
        	return;
    	}
    	
    	double L = this.wheelbase; 					//distance between front and back wheels
    	double W = this.trackwidth; 				//distance between front wheels
        double r = Math.sqrt ((L * L) + (W * W)); 	//radius of circle (actually it may be the diameter?)
        
        directionY *= -1; 							//invert Y
        directionX *= -1; 							//invert X

        double a = directionX - rotation * (L / r); //rear axle
        double b = directionX + rotation * (L / r); //front axle
        double c = directionY - rotation * (W / r); //left track
        double d = directionY + rotation * (W / r); //right track
        
        /*
         *                FRONT
         * 
         *            c          d
         *            | 		 |
         *       b ------------------ b
         *            |          |
         *            |          |
         * LEFT       |          |      RIGHT
         *            |          |
         *            |          |
         *       a ------------------ a
         *            |          |
         *            c          d
         * 
         *                BACK
         */

        //set motor speeds for each wheel
        double backRightSpeed = Math.sqrt ((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));

        //set wheel angle for each wheel
        double backRightAngle = (Math.atan2 (a, d) / Math.PI) * 180;
        double backLeftAngle = (Math.atan2 (a, c) / Math.PI) * 180;
        double frontRightAngle = (Math.atan2 (b, d) / Math.PI) * 180;
        double frontLeftAngle = (Math.atan2 (b, c) / Math.PI) * 180;
        
        //update the actual motors
    	this.rightFrontWheel.drive(frontRightSpeed, frontRightAngle);
    	this.leftFrontWheel.drive(frontLeftSpeed, frontLeftAngle);
    	this.leftBackWheel.drive(backLeftSpeed, backLeftAngle);
    	this.rightBackWheel.drive(backRightSpeed, backRightAngle);
        
    }
   
    // this code was to convert x/y into direction in radians
//    public double getDirectionRadians(double x, double y) {
//    return Math.atan2(x, y);
//}
    
// this code is for field oriented drive from previous code
//  double angle = getDirectionRadians(directionX, directionY);
//  if (fieldOrientedDrive) {
//      angle += FastMath.toRadians(normalizeGyroAngle(gyro.getAngle()));
//  }
//    public double normalizeGyroAngle(double angle){
//        return (angle - (FastMath.floor( angle / 360) * 360) );
//    }
}
