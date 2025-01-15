package ca.warp7.frc2025.subsystems.elevator;

public class ElevatorIOTalonFX implements ElevatorIO {
    /*Hardware*/
    private final TalonFX motor;
    //Have some sort of way to detect current
    
    @Override
    public void zeroMotor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'zeroMotor'");
    }

    @Override
    public void limitSwitch() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'maxMotor'");
    }
    
}
