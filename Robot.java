// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  SpeedController left1 = new Victor(1);
  SpeedController left2 = new Victor(3);
  SpeedController right1 = new Victor(2);
  SpeedController right2 = new Victor(4);
  int toggle = 0;
SpeedControllerGroup left =new  SpeedControllerGroup(left1, left2);
SpeedControllerGroup right = new SpeedControllerGroup(right1, right2);
Timer s_timer = new Timer();
DifferentialDrive driveTrain = new DifferentialDrive(left, right);
Victor shooter = new Victor(6);
Servo servo = new Servo(0);
Joystick joystick = new Joystick(0);
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);


  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
      System.out.println(servo.get());
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if(joystick.getRawAxis(1) > .02 || joystick.getRawAxis(5) >.02 || joystick.getRawAxis(1)  < -.02 || joystick.getRawAxis(5) < -.02   ){
      driveTrain.tankDrive(joystick.getRawAxis(1),joystick.getRawAxis(5));
    }else{
      driveTrain.tankDrive(0, 0);
    }

    if(joystick.getRawButton(1)){
      shooter.set(1);

    }else{
      shooter.set(0);
    }
    if(joystick.getRawButton(2) && toggle == 0){
       servo.setAngle(-127);
       System.out.println("open");
       s_timer.start();
       while(s_timer.get() < .5){
 
       }
       s_timer.stop();
       s_timer.reset();
toggle = 1;
    }else if(joystick.getRawButton(2) && toggle == 1){
      servo.setAngle(127);
      System.out.println("Closed");
      s_timer.start();
      while(s_timer.get() < .5){

      }
      s_timer.stop();
      s_timer.reset();
      toggle = 0;
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
