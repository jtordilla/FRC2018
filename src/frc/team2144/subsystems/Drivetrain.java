package frc.team2144.subsystems;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team2144.RobotMap;
import frc.team2144.commands.GatorDrive;


public class Drivetrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private Spark fl, fr, bl, br;
    private Encoder flenc, frenc, blenc, brenc;
    public MecanumDrive drive;

    /**
     * Constructs a Drivetrain using port constants found in RobotMap.
     */
    public Drivetrain() {
        fl = new Spark(RobotMap.fl_drive_port);
        fr = new Spark(RobotMap.fr_drive_port);
        bl = new Spark(RobotMap.bl_drive_port);
        br = new Spark(RobotMap.br_drive_port);
        flenc = new Encoder(RobotMap.fl_drive_enc_port_a, RobotMap.fl_drive_enc_port_b);
        frenc = new Encoder(RobotMap.fr_drive_enc_port_a, RobotMap.fr_drive_enc_port_b);
        blenc = new Encoder(RobotMap.bl_drive_enc_port_a, RobotMap.bl_drive_enc_port_b);
        brenc = new Encoder(RobotMap.br_drive_enc_port_a, RobotMap.br_drive_enc_port_b);
        drive = new MecanumDrive(fl, bl, fr, br);
    }

    /**
     * @param x   How fast to move sideways. Positive for right.
     * @param y   How fast to move forward. Positive forward.
     * @param rot How fast to turn. Positive right.
     */
    public void mecanumCartesian(double x, double y, double rot) {
        drive.driveCartesian(x, y, rot);
    }

    /**
     * @param x   How fast to move sideways. Positive for right.
     * @param y   How fast to move forward. Positive forward.
     * @param rot How fast to turn. Positive right.
     */
    public void mecanumCartesian(double x, double y, double rot, double gyro) {
        drive.driveCartesian(x, y, rot, gyro);
    }

    /**
     * Stops all drive motors.
     */
    public void stop() {
        this.arcade(0, 0, false);
    }

    /**
     * @param spd The speed to move at [-1.0..1.0]. Positive for forward.
     * @param rot The speed to turn at [-1.0..1.0]. Positive for right.
     */
    public void arcade(double spd, double rot) {
        this.arcade(spd, rot, false);
    }

    /**
     * @param spd     The speed to move at [-1.0..1.0]. Positive for forward.
     * @param rot     The speed to turn at [-1.0..1.0]. Positive for right.
     * @param squared Whether to square the move and turn speeds for better precision.
     */
    public void arcade(double spd, double rot, boolean squared) {
        drive.driveCartesian(0, squared ? spd * Math.abs(spd) : spd, squared ? rot * Math.abs(rot) : rot);
    }

    public void reset_encoders() {
        flenc.reset();
        frenc.reset();
        blenc.reset();
        brenc.reset();
    }

    public double average_encoders() {
        return (Math.abs(flenc.get()) + Math.abs(frenc.get()) + /*Math.abs(blenc.get()) +*/ Math.abs(brenc.get())) / 4;
    }

    public void updateEncoderData() {
        SmartDashboard.putNumber("flenc", flenc.get());
        SmartDashboard.putNumber("frenc", frenc.get());
        SmartDashboard.putNumber("blenc", blenc.get());
        SmartDashboard.putNumber("brenc", brenc.get());
    }

    public boolean have_encoders_reached(int position) {
        position = Math.abs(position);
        double avg = average_encoders();
        return avg >= position;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new GatorDrive());
    }
}

