package ca.warp7.frc2025.subsystems.elevator;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorIOSim implements ElevatorIO {
    ElevatorSim elevatorSim = new ElevatorSim(
            DCMotor.getKrakenX60(2),
            ElevatorSubsystem.GEAR_RATIO,
            0.5, // Not accurate, just a placeholder value
            ElevatorSubsystem.DRUM_RADIUS_METERS,
            0.0,
            5.0,
            true,
            0.0);

    Mechanism2d mech = new Mechanism2d(10, 10);
    MechanismRoot2d root = mech.getRoot("elevator", 0, 0);
    MechanismLigament2d m_elevator = root.append(new MechanismLigament2d("elevator", 5, 80));

    private double numRotations = 0.0; // Temporary tracking variable for testing before implementing ElevatorSim
    double currentVolts = 0.0;

    public ElevatorIOSim() {
        SmartDashboard.putData("ElevatorMech2d", mech);
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        elevatorSim.update(0.020);
        inputs.elevatorPositionMeters = elevatorSim.getPositionMeters();
        inputs.elevatorVelocityMetersPerSec = elevatorSim.getVelocityMetersPerSecond();
        inputs.elevatorAppliedVolts = currentVolts;
        inputs.elevatorCurrentAmps = elevatorSim.getCurrentDrawAmps();
        m_elevator.setLength(currentVolts);
    }

    @Override
    public void setVoltage(double volts) {
        currentVolts = volts;
    }

    @Override
    public void setPosition(double position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPosition'");
    }

    @Override
    public void setSpeed(double speed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSpeed'");
    }

    @Override
    public double getPosition() {
        return 0.0;
    }
}
