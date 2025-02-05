package ca.warp7.frc2025.subsystems.elevator;

import static ca.warp7.frc2025.subsystems.elevator.ElevatorConstants.*;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

public class ElevatorIOSim implements ElevatorIO {
    ElevatorSim elevatorSim = new ElevatorSim(
            DCMotor.getKrakenX60Foc(2),
            GEAR_RATIO,
            // Add half of first stage mass bc its on a 2:1 ratio compared to carriage
            Units.lbsToKilograms(10.8 + (2.5 / 2)),
            DRUM_RADIUS_METERS,
            0.0,
            15,
            true,
            0);

    double currentVolts = 0.0;

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        elevatorSim.update(0.020);
        inputs.elevatorPositionMeters = elevatorSim.getPositionMeters();
        inputs.elevatorVelocityMetersPerSec = elevatorSim.getVelocityMetersPerSecond();
        inputs.elevatorAppliedVolts = currentVolts;
    }

    @Override
    public void setVoltage(double volts) {
        currentVolts = volts;
        elevatorSim.setInputVoltage(currentVolts);
    }
}
