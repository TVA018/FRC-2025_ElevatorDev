package ca.warp7.frc2025.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
    ElevatorIO io;

    boolean enabled = true;
    boolean isZeroing;
    boolean isExtending;

    public ElevatorSubsystem(ElevatorIO io){
        this.io = io;
    }

    public Command zeroElevator(){
        return new RunCommand(
            () -> {
                io.zeroMotor();
            }
        ); //until the elevator is fully zeroed
    }

    public Command extendToLimit(){
        return new RunCommand(
            () -> {
                io.setVoltage(0); //Replace the 0 with an actual voltage later
            }
        ).until(null); //until the elevator is fully extended
    }
}
