package ca.warp7.frc2025.subsystems.elevator;

import edu.wpi.first.math.geometry.Rotation2d;

public class ElevatorConstants {
    public static final double GEAR_RATIO = 80.0 / 14.0;
    public static final double DRUM_RADIUS_METERS = 0.04514 / 2.0;
    public static final Rotation2d ELEVATOR_ANGLE = Rotation2d.fromDegrees(80.0);
    public static final double MAX_VELOCITY = 0.1;
    public static final double MAX_ACCELERATION = 2.0;

    public static enum LEVEL {
        L1(0),
        L2(0.2),
        L3(0.4),
        L4(0.6);

        private final double position;

        private LEVEL(double position) {
            this.position = position;
        }

        public double getPosition() {
            return position;
        }
    }

    public static final record Gains(double kP, double kI, double kD, double kS, double kG, double kV, double kA) {}

    public static final Gains GAINS = new Gains(5, 0, 0, 0, 0, 0, 0); // a P value of 25 seems to work well
}
