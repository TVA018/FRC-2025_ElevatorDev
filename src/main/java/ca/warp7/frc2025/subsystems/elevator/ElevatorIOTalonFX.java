package ca.warp7.frc2025.subsystems.elevator;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.controller.PIDController;

public class ElevatorIOTalonFX implements ElevatorIO {
    /*Hardware*/
    private final TalonFX motor1;
    private final TalonFX motor2;
    private final PIDController pidController1;
    private final PIDController pidController2;

    // Untuned PID constants
    private static final double kP = 0.1;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    // Have some sort of way to detect current

    public ElevatorIOTalonFX(int motor1Id, int motor2Id) {
        motor1 = new TalonFX(motor1Id);
        motor2 = new TalonFX(motor2Id);
        pidController1 = new PIDController(kP, kI, kD);
        pidController2 = new PIDController(kP, kI, kD);
    }

    @Override
    public void setVoltage(double volts) {
        motor1.setVoltage(volts);
        motor2.setVoltage(volts);
    }

    @Override
    public boolean zeroMotor() {
        double motor1CurrentPosition = motor1.getPosition()
                .getValueAsDouble(); // The function for returning the encoder value may be wrong here
        double motor2CurrentPosition = motor2.getPosition().getValueAsDouble();

        if (motor1CurrentPosition < 0) {
            double motor1Output =
                    pidController1.calculate(motor1CurrentPosition, 0); // Zero both motors using PID control
            motor1.set(motor1Output);

            double motor2Output = pidController2.calculate(motor2CurrentPosition, 0);
            motor2.set(motor2Output);
            return true; // Keep zeroing
        } else {
            motor1.setPosition(0);
            motor2.setPosition(0);
            return false; // Goal reached, stop zeroing
        }
    }

    @Override
    public boolean limitSwitch() {
        // Move the motor forward
        throw new UnsupportedOperationException("Unimplemented method 'maxMotor'");
    }

    @Override
    public void setPosition(double position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPosition'");
    }
}
