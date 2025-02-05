package ca.warp7.frc2025.subsystems.elevator;

import static ca.warp7.frc2025.subsystems.elevator.ElevatorConstants.*;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

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

    Mechanism2d mech = new Mechanism2d(10, 10);
    MechanismRoot2d root = mech.getRoot("elevator", 0, 0);
    MechanismLigament2d m_targetLength =
            root.append(new MechanismLigament2d("targetLength", 0, ELEVATOR_ANGLE.getDegrees()));
    MechanismLigament2d m_elevator = root.append(new MechanismLigament2d("elevator", 0, ELEVATOR_ANGLE.getDegrees()));
    double currentVolts = 0.0;

    public ElevatorIOSim() {
        SmartDashboard.putData("ElevatorMech2d", mech);
        m_elevator.setLineWeight(5);
        m_elevator.setColor(new Color8Bit(Color.kBlue));
        m_targetLength.setLineWeight(1);
        m_targetLength.setColor(new Color8Bit(Color.kOrange));
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        elevatorSim.update(0.020);
        inputs.elevatorPositionMeters = elevatorSim.getPositionMeters();
        inputs.elevatorVelocityMetersPerSec = elevatorSim.getVelocityMetersPerSecond();
        inputs.elevatorAppliedVolts = currentVolts;
        inputs.elevatorCurrentAmps = elevatorSim.getCurrentDrawAmps();
        m_elevator.setLength(inputs.elevatorPositionMeters);
    }

    @Override
    public void setVoltage(double volts) {
        currentVolts = volts;
        elevatorSim.setInputVoltage(currentVolts);
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

    public void setTarget(double position) {
        m_targetLength.setLength(position);
    }
}
