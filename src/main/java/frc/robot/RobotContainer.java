// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.[]

package frc.robot;

import java.io.File;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AbsoluteDrive;
import frc.robot.commands.AbsoluteDriveAdv;
import frc.robot.subsystems.DrivetrainSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{

  public static final double LEFT_X_DEADBAND  = 0.1;
  public static final double LEFT_Y_DEADBAND  = 0.1;
  public static final double RIGHT_X_DEADBAND = 0.1;
  public static final double TURN_CONSTANT    = 6;

  // The robot's subsystems and commands are defined here...
  private final DrivetrainSubsystem drivebase = new DrivetrainSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve"));

  // Replace with CommandPS4Controller or CommandJoystick if needed
  final Joystick controller = new Joystick(0);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    // Configure the trigger bindings
    configureBindings();

    // AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(drivebase,
    //                                                                () -> -MathUtil.applyDeadband(controller.getLeftY(),
    //                                                                                             LEFT_Y_DEADBAND),
    //                                                                () -> -MathUtil.applyDeadband(controller.getLeftX(),
    //                                                                                             LEFT_X_DEADBAND),
    //                                                                () -> -MathUtil.applyDeadband(controller.getRightX(),
    //                                                                                             RIGHT_X_DEADBAND),
    //                                                                controller.getHID()::getTriangleButtonPressed,
    //                                                                controller.getHID()::getCrossButtonPressed,
    //                                                                controller.getHID()::getSquareButtonPressed,
    //                                                                controller.getHID()::getCircleButtonPressed);

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the desired angle NOT angular rotation




    // Command driveFieldOrientedDirectAngle = drivebase.driveCommand(
    //     () -> MathUtil.applyDeadband(controller.getLeftX(), LEFT_X_DEADBAND),
    //     () -> MathUtil.applyDeadband(controller.getLeftY(), LEFT_Y_DEADBAND),
    //     () -> controller.getRightX(),
    //     () -> controller.getRightY());



    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the angular velocity of the robot
    Command driveFieldOrientedAnglularVelocity = drivebase.driveCommand(
        () -> MathUtil.applyDeadband(controller.getRawAxis(0), LEFT_X_DEADBAND),
        () -> MathUtil.applyDeadband(controller.getRawAxis(1), LEFT_Y_DEADBAND),
        () -> controller.getRawAxis(5) * 0.5);

    // Command driveFieldOrientedDirectAngleSim = drivebase.simDriveCommand(
    //     () -> MathUtil.applyDeadband(controller.getLeftY(), LEFT_Y_DEADBAND),
    //     () -> MathUtil.applyDeadband(controller.getLeftX(), LEFT_X_DEADBAND),
    //     () -> controller.getRawAxis(2));

    drivebase.setDefaultCommand(driveFieldOrientedAnglularVelocity);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary predicate, or via the
   * named factories in {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight joysticks}.
   */
  private void configureBindings()
  {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

//    controller.getRawButtonPressed(3).onTrue((Commands.runOnce(drivebase::zeroGyro)));

    // controller.x().whileTrue(Commands.runOnce(drivebase::lock, drivebase).repeatedly());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand()
  {
    return null;
    // An example command will be run in autonomous
  }

  
  public void setDriveMode()
  {
    //drivebase.setDefaultCommand();
  }

  public void setMotorBrake(boolean brake)
  {
    drivebase.setMotorBrake(brake);
  }
}
