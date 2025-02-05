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
    public void updateInputs(ElevatorIOInputs inputs) {
        
        inputs.elevatorPositionMeters = motor1.getPosition().getValueAsDouble();
        inputs.elevatorAppliedVolts = motor1.getMotorVoltage().getValueAsDouble();
    }
}
