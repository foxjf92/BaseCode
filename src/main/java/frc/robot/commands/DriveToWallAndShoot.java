/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveXY;
//import frc.robot.commands.LoadBall;
import frc.robot.commands.SetShooterSpeed;
import frc.robot.commands.ShootBall;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class DriveToWallAndShoot extends SequentialCommandGroup {
  /**
   * Add your docs here.
   */
  public DriveToWallAndShoot(Drivetrain drivetrain, Shooter shooter) {
    addCommands(new SetShooterSpeed(ManualShooterSpeed.FAR_SETPOINT));
    //addSequential(new DriveXY(100, 0, 0, 0.5));
    //addSequential(new DriveXY(120, 0, 0, 0.2));
    //addSequential(new SetShooterSpeed(ManualShooterCommand.FAR_SETPOINT));
    //addSequential(new LoadBall());
    //addSequential(new ShootBall());
    //addSequential(new SetShooterSpeed(ManualShooterCommand.FAR_SETPOINT));
    //addSequential(new LoadBall());
    //addSequential(new ShootBall());
    //addSequential(new SetShooterSpeed(ManualShooterCommand.FAR_SETPOINT));
    //addSequential(new LoadBall());
    addCommands(new ShootBall());
    addCommands(new DriveXY(100, 0, 0, 0.5));
    //addSequential(new DriveXY(100, 50, 0, 0.3));
  }
}
