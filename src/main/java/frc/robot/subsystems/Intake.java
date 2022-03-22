/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.frcteam2910.common.robot.Utilities;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.DoNothing;
import frc.robot.commands.IntakeHoldRetracted;
import frc.robot.commands.IntakePosition;


public class Intake extends Subsystem {
  /**
   * Creates a new Intake.
   */
  private static final int INTAKE_POWER_CAN_ID  = 12;
  private static final int EXTENDER1_CAN_ID = 10;
  private static final int EXTENDER2_CAN_ID = 9;

  CANSparkMax intakeMotor;
  CANSparkMax extenderMotor1;
  CANSparkMax extenderMotor2;
  
  public Intake() {
    intakeMotor = new CANSparkMax(INTAKE_POWER_CAN_ID, MotorType.kBrushless);
    intakeMotor.setIdleMode(IdleMode.kCoast);
    intakeMotor.setSmartCurrentLimit(20);
    extenderMotor1 = new CANSparkMax(EXTENDER1_CAN_ID, MotorType.kBrushless);
    extenderMotor2 = new CANSparkMax(EXTENDER2_CAN_ID, MotorType.kBrushless);
    
    //extenderMotor1.setSmartCurrentLimit(20);
    //extenderMotor2.setSmartCurrentLimit(20);
  }


  public void setIntakeSpeed(double intakeSpeed) {
    intakeSpeed = Utilities.deadband(intakeSpeed, 0.10);
    intakeMotor.set(intakeSpeed);
  }

  public void setExtenderCurrentLimit(int currentLimit) {
    extenderMotor1.setSmartCurrentLimit(currentLimit);
    extenderMotor2.setSmartCurrentLimit(currentLimit);
  }

  public void extend(double speed) {
    extenderMotor1.set(speed);
    extenderMotor2.set(-speed);
  SmartDashboard.putNumber("Extender", speed);
  }

  public void retract(double speed) {
    extenderMotor1.set(speed);
    extenderMotor2.set(-speed);
  SmartDashboard.putNumber("Extender", speed);
  }

  // @Override

  // public void periodic() {
  //   // This method will be called once per scheduler run

  //   // //TODO move this dumb control code into a proper command
  //   // putNumber = SmartDashboard.putNumber("Extender Pos.", getExtenderPosition());
  //   // double inSpeed = Robot.oi.shooterController.getRawAxis(1);
  //   // setIntakeSpeed(inSpeed);
  
  //   // Public void extend(double speed) {
  //   //   extenderMotor1.set(speed);
  //   //   extenderMotor2.set(-speed);
  //   // SmartDashboard.putNumber("Extender", speed);
  //   // }

  //   // SmartDashboard.putNumber("Extender", speed);
  //   // }
  // }       

  

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub  
    setDefaultCommand(new IntakeHoldRetracted());  
  }

  // public double getExtenderPosition() {
  //   return EXTENDER_INCH_PER_COUNT * extenderMotor1.getEncoder().getPosition();
  // }
  
}
