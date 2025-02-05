package ca.warp7.frc2025.subsystems.elevator;

import static ca.warp7.frc2025.subsystems.elevator.ElevatorConstants.*;

import ca.warp7.frc2025.subsystems.elevator.ElevatorConstants.LEVEL;
import ca.warp7.frc2025.util.LoggedTunableNumber;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class ElevatorSubsystem extends SubsystemBase {
    ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();
    ElevatorIO io;

    boolean enabled = true;
    boolean debounce = false;

    private double targetPosition;

    // Untuned PID constants
    private static final LoggedTunableNumber kP = new LoggedTunableNumber("Elevator/kP", GAINS.kP());
    private static final LoggedTunableNumber kI = new LoggedTunableNumber("Elevator/kI", GAINS.kI());
    private static final LoggedTunableNumber kD = new LoggedTunableNumber("Elevator/kD", GAINS.kD());
    private final ProfiledPIDController pidController;

    public ElevatorSubsystem(ElevatorIO io) {
        this.io = io;
        this.pidController = new ProfiledPIDController(kP.get(), kI.get(), kD.get(), new Constraints(10.0, 5.0));
        this.targetPosition = 0;
    }

    @Override
    public void periodic() {
        double currentPosition = inputs.elevatorPositionMeters;

        double motorOutput = pidController.calculate(currentPosition, targetPosition);

        LoggedTunableNumber.ifChanged(
                hashCode(),
                () -> {
                    pidController.setP(kP.get());
                    pidController.setI(kI.get());
                    pidController.setD(kD.get());
                },
                kP,
                kI,
                kD);
        motorOutput = Math.max(Math.min(motorOutput, 12.0), -12.0);
        io.setVoltage(motorOutput);
        io.updateInputs(inputs);
        Logger.processInputs("Elevator Inputs", inputs);
    }

    public Command goToPosition(LEVEL position) {
        return this.runOnce(
                () -> {
                    targetPosition = position.getPosition();
                    Logger.recordOutput("Elevator/TargetPosition", targetPosition);
                } // function
                );
    }
}
