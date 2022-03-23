/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class TurnToAngle extends CommandBase {

  public static final double ANGLE_TOLERANCE = 5.0;

  double target;

  public TurnToAngle(double targetAngle) {
    addRequirements(Robot.drivetrain);
    target = targetAngle;
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    Robot.drivetrain.driveHeading(new Translation2d(), target);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return Math.abs(delta()) < ANGLE_TOLERANCE;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    Robot.drivetrain.stop();
  }

  private double delta() {
    return Drivetrain.angleDelta(Robot.drivetrain.getAngle(), target);
  }
}
