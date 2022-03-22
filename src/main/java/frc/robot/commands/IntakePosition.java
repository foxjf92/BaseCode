/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class IntakePosition extends Command {
  
  private static final double SPEED = 0.25;

  /**
   * Creates a new Collect command.
   */
  
   public IntakePosition() {
    // Use addRequirements() here to declare subsystem dependencies.
    requires(Robot.intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.intake.setExtenderCurrentLimit(20);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(Robot.oi.shooterController.getRawButton(4)) { // Y Button
      SmartDashboard.putString("ExtendState", "EXTEND");
      Robot.intake.extend(SPEED);
    } else if(Robot.oi.shooterController.getRawButton(3)){ // X Button
      SmartDashboard.putString("ExtendState", "RETRACT");
      Robot.intake.retract(-SPEED);
    } else {
      SmartDashboard.putString("ExtendState", "STOP");
      Robot.intake.extend(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void interrupted() {
    Robot.intake.extend(0);
    Robot.intake.retract(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Robot.oi.shooterController.getRawButtonReleased(4) || Robot.oi.shooterController.getRawButtonReleased(3)){
        return true;
    }
    else{
        return false;
    }
  }
}
