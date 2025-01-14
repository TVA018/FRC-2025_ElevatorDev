package ca.warp7.frc2025.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
    ElevatorIO io;

    public ElevatorSubsystem(ElevatorIO io){
        this.io = io;
    }

    
}
