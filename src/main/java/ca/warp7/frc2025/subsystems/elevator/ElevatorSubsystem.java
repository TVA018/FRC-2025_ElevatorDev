package ca.warp7.frc2025.subsystems.elevator;

import static ca.warp7.frc2025.subsystems.elevator.ElevatorConstants.*;

import ca.warp7.frc2025.subsystems.elevator.ElevatorConstants.LEVEL;
import ca.warp7.frc2025.util.LoggedTunableNumber;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
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

    // PID values
    private final ProfiledPIDController pidController;
    private static final LoggedTunableNumber kP = new LoggedTunableNumber("Elevator/PID/kP", GAINS.kP());
    private static final LoggedTunableNumber kI = new LoggedTunableNumber("Elevator/PID/kI", GAINS.kI());
    private static final LoggedTunableNumber kD = new LoggedTunableNumber("Elevator/PID/kD", GAINS.kD());

    private static final LoggedTunableNumber maxVelocity =
            new LoggedTunableNumber("Elevator/PID/maxVelocity", MAX_VELOCITY);
    private static final LoggedTunableNumber maxAcceleration =
            new LoggedTunableNumber("Elevator/PID/maxAcceleration", MAX_ACCELERATION);

    // FeedForward  values
    private static final LoggedTunableNumber kS = new LoggedTunableNumber("Elevator/FeedForward/kS", GAINS.kS());
    private static final LoggedTunableNumber kG = new LoggedTunableNumber("Elevator/FeedForward/kG", GAINS.kG());
    private static final LoggedTunableNumber kV = new LoggedTunableNumber("Elevator/FeedForward/kV", GAINS.kV());
    private static final LoggedTunableNumber kA = new LoggedTunableNumber("Elevator/FeedForward/kA", GAINS.kA());

    public ElevatorSubsystem(ElevatorIO io) {
        this.io = io;
        this.pidController = new ProfiledPIDController(
                kP.get(), kI.get(), kD.get(), new Constraints(maxVelocity.get(), maxAcceleration.get()));

        this.targetPosition = 0;
    }

    ElevatorFeedforward feedforward = new ElevatorFeedforward(kS.get(), kG.get(), kV.get(), kA.get());

    @Override
    public void periodic() {
        double currentPosition = inputs.elevatorPositionMeters;
        double currentVelocity = inputs.elevatorVelocityMetersPerSec;

        double feedforwardOutput = feedforward.calculate(currentVelocity);
        double pidOutput = pidController.calculate(currentPosition, targetPosition);

        LoggedTunableNumber.ifChanged(
                hashCode(),
                () -> {
                    pidController.setP(kP.get());
                    pidController.setI(kI.get());
                    pidController.setD(kD.get());
                    pidController.setConstraints(new Constraints(maxVelocity.get(), maxAcceleration.get()));
                },
                kP,
                kI,
                kD,
                maxAcceleration,
                maxVelocity);
        LoggedTunableNumber.ifChanged(
                hashCode(),
                () -> {
                    feedforward = new ElevatorFeedforward(kS.get(), kG.get(), kV.get(), kA.get());
                },
                kS,
                kG,
                kV,
                kA);
        // Feed forward
        // Maybe unnessicary
        double motorOutput = pidOutput + feedforwardOutput;
        motorOutput = MathUtil.clamp(motorOutput, -12.0, 12.0);
        io.setVoltage(motorOutput);
        io.updateInputs(inputs);
        Logger.processInputs("Elevator Inputs", inputs);
    }

    public Command goToPosition(LEVEL position) {
        return this.runOnce(
                () -> {
                    targetPosition = position.getPosition();
                } // function
                );
    }
}
