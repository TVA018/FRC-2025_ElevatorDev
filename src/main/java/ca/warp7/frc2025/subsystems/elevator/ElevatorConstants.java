package ca.warp7.frc2025.subsystems.elevator;

import edu.wpi.first.math.geometry.Rotation2d;

public class ElevatorConstants {
    public static final double GEAR_RATIO = 80.0 / 14.0;
    public static final double DRUM_RADIUS_METERS = 0.04514 / 2.0;
    public static final Rotation2d ELEVATOR_ANGLE = Rotation2d.fromDegrees(80.0);

    public static enum LEVEL {
        L1(0),
        L2(0.6),
        L3(1.2),
        L4(1.8);

        private final double position;

        private LEVEL(double position) {
            this.position = position;
        }

        public double getPosition() {
            return position;
        }
    }

    public static final record PIDGains(double kP, double kI, double kD) {}

    public static final PIDGains GAINS = new PIDGains(5, 0, 0); // a P value of 25 seems to work well
}
