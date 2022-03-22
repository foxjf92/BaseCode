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

public class ManualShootCommand extends Command {
  
  /**
   * Creates a new Collect command.
   */
  public ManualShootCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    requires(Robot.shooter);
    requires(Robot.feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double shootTriggerValue = Robot.oi.shooterController.getRawAxis(3); //should be right trigger
    
    if(shootTriggerValue > 0.1){
      Robot.feeder.feed(1.0);
    }
    else{
      Robot.feeder.feed(0);
    }
    
    //FIXME Work out logic to run feed while intaking, maybe here?

    // if(Robot.oi.shooterController.getRawButton(4)) {
    //   SmartDashboard.putString("ExtendState", "EXTEND");
    //   Robot.climber.extend(EXTEND_SPEED);
    // } else if(Robot.oi.shooterController.getRawButton(3)){
    //   SmartDashboard.putString("ExtendState", "RETRACT");
    //   Robot.climber.extend(-EXTEND_SPEED);
    // } else {
    //   SmartDashboard.putString("ExtendState", "STOP");
    //   Robot.climber.extend(0);
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void interrupted() {
    Robot.shooter.setMotorRPM(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
