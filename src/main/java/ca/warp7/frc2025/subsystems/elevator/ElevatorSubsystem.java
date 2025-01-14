package ca.warp7.frc2025.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
    ElevatorIO io;

    boolean enabled = true;
    boolean isZeroing;



    public ElevatorSubsystem(ElevatorIO io){
        this.io = io;
    }

    public Command zeroElevator(){
        return new RunCommand(
            () -> {}
        );
    }
}
