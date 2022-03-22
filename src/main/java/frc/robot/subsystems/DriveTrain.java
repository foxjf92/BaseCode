// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import org.frcteam2910.common.drivers.SwerveModule;
import org.frcteam2910.common.robot.drivers.Mk2SwerveModuleBuilder;
import org.frcteam2910.common.math.Vector2;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveCommand;

import edu.wpi.first.wpilibj.I2C;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class DriveTrain extends Subsystem {

    public static final double METERS_PER_ENCODER_COUNT = 1; // TODO Calibrate this value!!!

    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 4; // CAN
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER = 0; // Analog
    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 8; // CAN

    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 5; // CAN
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER = 1; // Analog
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 1; // CAN

    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 6; // CAN
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER = 2; // Analog
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 2; // CAN

    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 7; // CAN
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER = 3; // Analog
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 3; // CAN

    private static final double TRACKWIDTH = 18.625; // inches
    private static final double WHEELBASE = 27.5;

    // 2022 Values
    private static final double FRONT_LEFT_ANGLE_OFFSET = -Math.toRadians(102.3);
    private static final double FRONT_RIGHT_ANGLE_OFFSET = -Math.toRadians(285.2);
    private static final double BACK_LEFT_ANGLE_OFFSET = -Math.toRadians(135.2);
    private static final double BACK_RIGHT_ANGLE_OFFSET = -Math.toRadians(138.5);

    // Calibration
    // private static final double FRONT_LEFT_ANGLE_OFFSET = -Math.toRadians(0.0);
    // private static final double FRONT_RIGHT_ANGLE_OFFSET = -Math.toRadians(0.0);
    // private static final double BACK_LEFT_ANGLE_OFFSET = -Math.toRadians(0.0);
    // private static final double BACK_RIGHT_ANGLE_OFFSET = -Math.toRadians(0.0);

    // 2020 Values
    // private static final double FRONT_LEFT_ANGLE_OFFSET = -Math.toRadians(102.2);
    // private static final double FRONT_RIGHT_ANGLE_OFFSET =
    // -Math.toRadians(282.2);
    // private static final double BACK_LEFT_ANGLE_OFFSET = -Math.toRadians(134.2);
    // private static final double BACK_RIGHT_ANGLE_OFFSET = -Math.toRadians(138.2);

    private static final double kPgain = 0.040;
    private static final double kDgain = 0.0;

    public CANSparkMax rightFrontDriveMotor;
    public CANSparkMax rightFrontRotateMotor;
    public CANSparkMax leftFrontDriveMotor;
    public CANSparkMax leftFrontRotateMotor;
    public CANSparkMax rightBackDriveMotor;
    public CANSparkMax rightBackRotateMotor;
    public CANSparkMax leftBackDriveMotor;
    public CANSparkMax leftBackRotateMotor;
    private final SwerveModule frontLeftModule;
    private final SwerveModule frontRightModule;
    private final SwerveModule backLeftModule;
    private final SwerveModule backRightModule;
    private final SwerveDriveKinematics kinematics;

    private AHRS navx;

    private SwerveDriveOdometry m_odometry;

    private NetworkTableEntry frontLeftEntry = Shuffleboard.getTab("DriveTrain").add("Front Left Angle", 0).getEntry();
    private NetworkTableEntry frontRightEntry = Shuffleboard.getTab("DriveTrain").add("Front Right Angle", 0)
            .getEntry();
    private NetworkTableEntry backLeftEntry = Shuffleboard.getTab("DriveTrain").add("Back Left Angle", 0).getEntry();
    private NetworkTableEntry backRightEntry = Shuffleboard.getTab("DriveTrain").add("Back Right Angle", 0).getEntry();

    public DriveTrain() {
        rightFrontDriveMotor = new CANSparkMax(DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR, CANSparkMax.MotorType.kBrushless);
        rightFrontDriveMotor.setInverted(true);
        rightFrontDriveMotor.setSmartCurrentLimit(40);
        rightFrontDriveMotor.setIdleMode(IdleMode.kBrake);
        rightFrontRotateMotor = new CANSparkMax(DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR, CANSparkMax.MotorType.kBrushless);
        rightFrontRotateMotor.setSmartCurrentLimit(40);
        rightFrontRotateMotor.setIdleMode(IdleMode.kBrake);

        leftFrontDriveMotor = new CANSparkMax(DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR, CANSparkMax.MotorType.kBrushless);
        leftFrontDriveMotor.setInverted(true);
        leftFrontDriveMotor.setSmartCurrentLimit(40);
        leftFrontDriveMotor.setIdleMode(IdleMode.kBrake);
        leftFrontRotateMotor = new CANSparkMax(DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR, CANSparkMax.MotorType.kBrushless);
        leftFrontRotateMotor.setSmartCurrentLimit(40);
        leftFrontRotateMotor.setIdleMode(IdleMode.kBrake);

        rightBackDriveMotor = new CANSparkMax(DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR, CANSparkMax.MotorType.kBrushless);
        rightBackDriveMotor.setInverted(true);
        rightBackDriveMotor.setSmartCurrentLimit(40);
        rightBackDriveMotor.setIdleMode(IdleMode.kBrake);
        rightBackRotateMotor = new CANSparkMax(DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR, CANSparkMax.MotorType.kBrushless);
        rightBackRotateMotor.setSmartCurrentLimit(40);
        rightBackRotateMotor.setIdleMode(IdleMode.kBrake);

        leftBackDriveMotor = new CANSparkMax(DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR, CANSparkMax.MotorType.kBrushless);
        leftBackDriveMotor.setInverted(true);
        leftBackDriveMotor.setSmartCurrentLimit(40);
        leftBackRotateMotor = new CANSparkMax(DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR, CANSparkMax.MotorType.kBrushless);
        leftBackDriveMotor.setIdleMode(IdleMode.kBrake);
        leftBackRotateMotor.setSmartCurrentLimit(40);
        leftBackRotateMotor.setIdleMode(IdleMode.kBrake);

        frontLeftModule = new Mk2SwerveModuleBuilder(
                new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0))
                        .angleEncoder(new AnalogInput(DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER), FRONT_LEFT_ANGLE_OFFSET)
                        .angleMotor(leftFrontRotateMotor)
                        .driveMotor(leftFrontDriveMotor, Mk2SwerveModuleBuilder.MotorType.NEO)
                        .build();

        frontRightModule = new Mk2SwerveModuleBuilder(
                new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
                        .angleEncoder(new AnalogInput(DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER), FRONT_RIGHT_ANGLE_OFFSET)
                        .angleMotor(rightFrontRotateMotor, Mk2SwerveModuleBuilder.MotorType.NEO)
                        .driveMotor(rightFrontDriveMotor, Mk2SwerveModuleBuilder.MotorType.NEO)
                        .build();

        backLeftModule = new Mk2SwerveModuleBuilder(
                new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0))
                        .angleEncoder(new AnalogInput(DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER), BACK_LEFT_ANGLE_OFFSET)
                        .angleMotor(leftBackRotateMotor, Mk2SwerveModuleBuilder.MotorType.NEO)
                        .driveMotor(leftBackDriveMotor, Mk2SwerveModuleBuilder.MotorType.NEO)
                        .build();

        backRightModule = new Mk2SwerveModuleBuilder(
                new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
                        .angleEncoder(new AnalogInput(DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER), BACK_RIGHT_ANGLE_OFFSET)
                        .angleMotor(rightBackRotateMotor, Mk2SwerveModuleBuilder.MotorType.NEO)
                        .driveMotor(rightBackDriveMotor, Mk2SwerveModuleBuilder.MotorType.NEO)
                        .build();

        frontLeftModule.setName("Front Left");
        frontRightModule.setName("Front Right");
        backLeftModule.setName("Back Left");
        backRightModule.setName("Back Right");

        kinematics = new SwerveDriveKinematics(
                new Translation2d(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                new Translation2d(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
                new Translation2d(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
                new Translation2d(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0));

        navx = new AHRS(I2C.Port.kOnboard);
        System.out.println("Navx firmware: " + navx.getFirmwareVersion());

        m_odometry = new SwerveDriveOdometry(kinematics, Rotation2d.fromDegrees(getAngle()));

    }

    public void setFieldPosition(double xPos, double yPos, double angle) {
        m_odometry.resetPosition(new Pose2d(xPos, yPos, Rotation2d.fromDegrees(angle)), Rotation2d.fromDegrees(angle));
    }

    public void resetYaw() {
        navx.reset();
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveCommand());
    }

    @Override
    public void periodic() {
        frontLeftModule.updateSensors();
        frontRightModule.updateSensors();
        backLeftModule.updateSensors();
        backRightModule.updateSensors();

        frontLeftEntry.setDouble(Math.toDegrees(frontLeftModule.getCurrentAngle()));
        frontRightEntry.setDouble(Math.toDegrees(frontRightModule.getCurrentAngle()));
        backLeftEntry.setDouble(Math.toDegrees(backLeftModule.getCurrentAngle()));
        backRightEntry.setDouble(Math.toDegrees(backRightModule.getCurrentAngle()));

        SmartDashboard.putNumber("Gyroscope Angle", getAngle());

        frontLeftModule.updateState(TimedRobot.kDefaultPeriod);
        frontRightModule.updateState(TimedRobot.kDefaultPeriod);
        backLeftModule.updateState(TimedRobot.kDefaultPeriod);
        backRightModule.updateState(TimedRobot.kDefaultPeriod);

        Pose2d pose = m_odometry.update(Rotation2d.fromDegrees(getAngle()),
                new SwerveModuleState(frontLeftModule.getCurrentVelocity(),
                        new Rotation2d(frontLeftModule.getCurrentAngle())),
                new SwerveModuleState(frontRightModule.getCurrentVelocity(),
                        new Rotation2d(frontRightModule.getCurrentAngle())),
                new SwerveModuleState(backLeftModule.getCurrentVelocity(),
                        new Rotation2d(backLeftModule.getCurrentAngle())),
                new SwerveModuleState(backRightModule.getCurrentVelocity(),
                        new Rotation2d(backRightModule.getCurrentAngle())));

        SmartDashboard.putString("Position", pose.toString());
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public void driveHeading(Translation2d translation, double heading) {

        double angle = getAngle();
        double currentAngularRate = getAngularRate();
        double angle_error = angleDelta(heading, angle);
        double yawCommand = -angle_error * kPgain - (currentAngularRate) * kDgain;

        drive(translation, yawCommand, true);
    }

    public void drive(Translation2d translation, double rotation, boolean fieldOriented) {
        rotation *= 2.0 / Math.hypot(WHEELBASE, TRACKWIDTH);
        ChassisSpeeds speeds;
        SmartDashboard.putBoolean("Field-Oriented", fieldOriented);
        if (fieldOriented) {
            speeds = ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotation,
                    Rotation2d.fromDegrees(getAngle()));
        } else {
            speeds = new ChassisSpeeds(translation.getX(), translation.getY(), rotation);
        }

        SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);
        frontLeftModule.setTargetVelocity(states[0].speedMetersPerSecond, states[0].angle.getRadians());
        frontRightModule.setTargetVelocity(states[1].speedMetersPerSecond, states[1].angle.getRadians());
        backLeftModule.setTargetVelocity(states[2].speedMetersPerSecond, states[2].angle.getRadians());
        backRightModule.setTargetVelocity(states[3].speedMetersPerSecond, states[3].angle.getRadians());
    }

    public void stop() {
        frontLeftModule.setTargetVelocity(0.0, frontLeftModule.getCurrentAngle());
        frontRightModule.setTargetVelocity(0.0, frontRightModule.getCurrentAngle());
        backLeftModule.setTargetVelocity(0.0, backLeftModule.getCurrentAngle());
        backRightModule.setTargetVelocity(0.0, backRightModule.getCurrentAngle());
    }

    public double getAngle() {
        return -navx.getAngle();
    }

    public double getAngularRate() {
        return -navx.getRate();
    }

    static public double angleDelta(double src, double dest) {
        double delta = (dest - src) % 360.0;
        if (Math.abs(delta) > 180) {
            delta = delta - (Math.signum(delta) * 360);
        }
        return delta;
    }
}
