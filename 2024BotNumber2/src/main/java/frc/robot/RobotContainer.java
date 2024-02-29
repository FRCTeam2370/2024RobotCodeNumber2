// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.RunIntake;
import frc.robot.commands.Shooter.RunIndexer;
import frc.robot.commands.Shooter.RunShooter;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  //The controller is instantiated here and is in port 0
  public static final GenericHID driver = new GenericHID(0);
  
  public static JoystickButton A = new JoystickButton(driver, 1);
  public static JoystickButton B = new JoystickButton(driver, 2);
  public static JoystickButton X = new JoystickButton(driver, 3);
  public static JoystickButton Y = new JoystickButton(driver, 4);
  public static JoystickButton righButton = new JoystickButton(driver, 6);
  public static JoystickButton leftButton = new JoystickButton(driver, 5);
  public static JoystickButton Start = new JoystickButton(driver, 8);
  public static JoystickButton Menu = new JoystickButton(driver, 7);
  public static POVButton UpDpad = new POVButton(driver, 0);
  public static POVButton downDpad = new POVButton(driver, 180);
  public static JoystickButton M1 = new JoystickButton(driver, 9);
  public static JoystickButton M2 = new JoystickButton(driver, 10);
  public static POVButton rightDpad = new POVButton(driver, 90);

  
  public static Trigger trigger(GenericHID controller, int axis){
    return new Trigger(()-> controller.getRawAxis(axis) >= 0.9);
  }

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final IntakeSubsystem mIntakeSubsystem = new IntakeSubsystem();
  private final ShooterSubsystem mShooterSubsystem = new ShooterSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    mShooterSubsystem.setDefaultCommand(new RunShooter(mShooterSubsystem));


    leftButton.whileTrue(new RunIntake());

    righButton.whileTrue(new RunIndexer());

    

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
