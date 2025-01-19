package ca.warp7.frc2025.subsystems.elevator;

public interface ElevatorIO {
    public abstract void setVoltage(double volts);

    public abstract void setPosition(double position);

    public abstract void setSpeed(double speed);

    public abstract double getPosition();
}
