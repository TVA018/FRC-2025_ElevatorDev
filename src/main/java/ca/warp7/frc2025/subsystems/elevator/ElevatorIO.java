package ca.warp7.frc2025.subsystems.elevator;

public interface ElevatorIO {
    public abstract void setVoltage(double volts);
    public abstract boolean zeroMotor();
    public abstract boolean limitSwitch();
}
