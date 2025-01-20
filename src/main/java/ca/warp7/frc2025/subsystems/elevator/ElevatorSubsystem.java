package ca.warp7.frc2025.subsystems.elevator;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
    ElevatorIO io;

    boolean enabled = true;

    double targetPosition;
    
    // Untuned PID constants
    private static final double kP = 0.1;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    final PIDController pidController;

    public ElevatorSubsystem(ElevatorIO io) {
        this.io = io;
        this.pidController = new PIDController(kP, kI, kD);
    }

    public void periodic(){
        double currentPosition = io.getPosition();

        double motorOutput = pidController.calculate(currentPosition, targetPosition); // Zero both motors using PID control
        io.setVoltage(motorOutput);
    }

    public Command goToPosition(double position){
        return new RunCommand(
            () -> {
                targetPosition = position;
            } //function
        );
    }
}
