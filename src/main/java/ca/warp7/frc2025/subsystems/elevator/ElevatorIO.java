package ca.warp7.frc2025.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
    @AutoLog
    public static class ElevatorIOInputs {
        public double elevatorPositionRad = 0.0;
        public double elevatorPositionMeters = 0.0;
        public double elevatorVelocityMetersPerSec = 0.0;
        public double elevatorAppliedVolts = 0.0;
        public double elevatorCurrentAmps = 0.0;
    }

    public default void updateInputs(ElevatorIOInputs inputs) {}

    public abstract void setVoltage(double volts);

    public abstract void setPosition(double position);

    public abstract void setSpeed(double speed);

    public abstract double getPosition();
}
