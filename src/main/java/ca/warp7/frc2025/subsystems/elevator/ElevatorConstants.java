package ca.warp7.frc2025.subsystems.elevator;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

public class ElevatorConstants {
    public static final double GEAR_RATIO = 12.5 / 1.0; // Temporary value, not accurate
    public static final double DRUM_RADIUS_METERS = Units.inchesToMeters(5); // Temporary value, not accurate
    public static final Rotation2d ELEVATOR_ANGLE = Rotation2d.fromDegrees(80.0);

    public static enum LEVEL {
        L1(0),
        L2(5),
        L3(10),
        L4(15);

        private final double position;

        private LEVEL(double position) {
            this.position = position;
        }

        public double getPosition() {
            return position;
        }
    }

    public static final record PIDGains(double kP, double kI, double kD) {}

    public static final PIDGains GAINS = new PIDGains(2, 0, 0);
}
