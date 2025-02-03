package ca.warp7.frc2025.subsystems.elevator;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class ElevatorSubsystem extends SubsystemBase {
    public static final double GEAR_RATIO = 12.5 / 1.0; // Temporary value, not accurate
    public static final double DRUM_RADIUS_METERS = Units.inchesToMeters(5); // Temporary value, not accurate
    public static final Rotation2d ELEVATOR_ANGLE = Rotation2d.fromDegrees(65.0);

    ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();
    ElevatorIO io;

    boolean enabled = true;
    boolean debounce = false;

    double targetPosition;

    // Untuned PID constants
    private static final double kP = 0.1;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private final ProfiledPIDController pidController;

    public ElevatorSubsystem(ElevatorIO io) {
        this.io = io;
        this.pidController = new ProfiledPIDController(kP, kI, kD, new Constraints(10.0, 5.0));
    }

    @Override
    public void periodic() {
        // double currentPosition = io.getPosition();

        // double motorOutput = pidController.calculate(currentPosition, targetPosition);

        io.updateInputs(inputs);
    }

    public Command goToPosition(double position) {
        return new RunCommand(
                () -> {
                    targetPosition = position;
                    Logger.recordOutput("Elevator/TargetPosition", targetPosition);
                } // function
                );
    }
}
