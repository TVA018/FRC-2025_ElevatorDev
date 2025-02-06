package ca.warp7.frc2025.subsystems.elevator;

import static ca.warp7.frc2025.subsystems.elevator.ElevatorConstants.*;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

public class ElevatorIOTalonFX implements ElevatorIO {
    /*Hardware*/
    private final TalonFX parentMotor;
    private final TalonFX followerMotor;
    private final TalonFXConfiguration config = new TalonFXConfiguration();
    // Have some sort of way to detect current

    public ElevatorIOTalonFX(int parentMotorId, int followerMotorId) {
        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        
        parentMotor = new TalonFX(parentMotorId, "rio");
        followerMotor = new TalonFX(followerMotorId, "rio");
        followerMotor.setControl(new Follower(parentMotor.getDeviceID(), true));
    }

    @Override
    public void setVoltage(double volts) {
        parentMotor.setVoltage(volts);
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        double rotations = parentMotor.getPosition().getValueAsDouble();
        inputs.elevatorPositionMeters = rotationsToMeters(rotations);
        inputs.elevatorAppliedVolts = parentMotor.getMotorVoltage().getValueAsDouble();
    }

    private double rotationsToMeters(double rotations) {
        return (rotations * GEAR_RATIO) * (2 * Math.PI) * DRUM_RADIUS_METERS;
    }
}
