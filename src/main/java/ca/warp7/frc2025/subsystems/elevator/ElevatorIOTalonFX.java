package ca.warp7.frc2025.subsystems.elevator;

import com.ctre.phoenix6.hardware.TalonFX;

public class ElevatorIOTalonFX implements ElevatorIO {
    /*Hardware*/
    private final TalonFX motor1;
    private final TalonFX motor2;
    // Have some sort of way to detect current

    public ElevatorIOTalonFX(int motor1Id, int motor2Id) {
        motor1 = new TalonFX(motor1Id);
        motor2 = new TalonFX(motor2Id);
    }

    @Override
    public void setVoltage(double volts) {
        motor1.setVoltage(volts);
        motor2.setVoltage(volts);
    }

    @Override
    public void setPosition(double position) {
        motor1.setPosition(position);
        motor2.setPosition(position);
    }

    @Override
    public void setSpeed(double speed) {
        motor1.set(speed);
        motor2.set(speed);
    }

    @Override
    public double getPosition() {
        return motor1.getPosition().getValueAsDouble(); // motor1 and motor2 should be synced
    }
}
