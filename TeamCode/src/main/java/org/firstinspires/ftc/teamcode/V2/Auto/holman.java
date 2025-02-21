package org.firstinspires.ftc.teamcode.V2.Auto;

import static org.firstinspires.ftc.teamcode.V2.TeleOp.roombaBotTeleOpRed.pterm;
import static org.firstinspires.ftc.teamcode.V2.TeleOp.roombaBotTeleOpRed.pterm2;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.elbowPositions;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.suitcasePositions;
import static org.firstinspires.ftc.teamcode.V2.hardwareMapV2.wristPositions;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.V1.TeleOp.teleBase;
import org.firstinspires.ftc.teamcode.V2.hardwareMapV2;

@Autonomous
public class holman extends teleBase {
    public org.firstinspires.ftc.teamcode.V2.hardwareMapV2 robot = new hardwareMapV2();
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    public Pose2d startPose = new Pose2d(-2,0,Math.toRadians(-90));

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        robot.elbowPort.setPosition(elbowPositions.get("up"));
        robot.elbowStar.setPosition(1-elbowPositions.get("up"));
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        TrajectorySequence thingy3 =drive.trajectorySequenceBuilder(startPose)
                //.splineToLinearHeading(startPose.plus(new Pose2d(1, 9, Math.toRadians(0))), Math.toRadians(90))
                .back(8)
                .addDisplacementMarker(()->{
                    robot.elbowPort.setPosition(elbowPositions.get("down"));
                    robot.elbowStar.setPosition(1-elbowPositions.get("down"));
                })
                .addDisplacementMarker(19.25, ()->{
                    robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                    robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                })
                .addDisplacementMarker(19.5, ()->{
                    robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                    robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                })
                .addDisplacementMarker(19.75, ()->{
                    robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                    robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                })

                .addDisplacementMarker(20, ()->{
                    robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                    robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                })
                .addDisplacementMarker(20.25, ()->{
                    robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                    robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                })
                .addDisplacementMarker(20.5, ()->{
                    robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                    robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                })
                .addDisplacementMarker(20.75, ()->{
                    robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                    robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                })
                .addDisplacementMarker(21, ()->{
                    robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                    robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                })
                .splineToLinearHeading(startPose.plus(new Pose2d(2, 22, Math.toRadians(0))), Math.toRadians(90))
                .build();
        TrajectorySequence thingy1 = drive.trajectorySequenceBuilder(thingy3.end())
                .back(7.5)
                .addDisplacementMarker(7, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                })
                .waitSeconds(0.3)
                .addDisplacementMarker(7.1, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(7.2, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(7.3, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(7.4, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(7.5, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(7.6, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(7.7, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(7.8, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(7.9, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(8, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(8.1, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(8.2, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(8.3, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                }).addDisplacementMarker(8.4, ()->{
                    robot.portArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-500 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                })
                .splineToLinearHeading(startPose.plus(new Pose2d(13,13,Math.toRadians(0))), Math.toRadians(-90))
                .splineToLinearHeading(startPose.plus(new Pose2d(24,13,Math.toRadians(0))), Math.toRadians(45))
                .splineToLinearHeading(startPose.plus(new Pose2d(34,50,Math.toRadians(0))),Math.toRadians(0))
                .splineToLinearHeading(startPose.plus(new Pose2d(42,50,Math.toRadians(0))), Math.toRadians(-90))
                .splineToSplineHeading(startPose.plus(new Pose2d(42,12,Math.toRadians(0))), Math.toRadians(90))
                .back(5)
                .splineToSplineHeading(startPose.plus(new Pose2d(39,20,Math.toRadians(180))), Math.toRadians(90))
                /*
                .splineToLinearHeading(startPose.plus(new Pose2d(50, 50, Math.toRadians(0))), Math.toRadians(-90))
                .splineToLinearHeading(startPose.plus(new Pose2d(50, 12, Math.toRadians(0))), Math.toRadians(90))
                .splineToLinearHeading(startPose.plus(new Pose2d(48, 50, Math.toRadians(0))), Math.toRadians(90))
                .splineToLinearHeading(startPose.plus(new Pose2d(61, 50, Math.toRadians(0))), Math.toRadians(-90))
                .splineToLinearHeading(startPose.plus(new Pose2d(61, 8, Math.toRadians(0))), Math.toRadians(-90))
                .strafeRight(9)
                .turn(Math.PI)
                 */
                        .splineToSplineHeading(startPose.plus(new Pose2d(39,-10,Math.toRadians(180))), Math.toRadians(-90))
                .addDisplacementMarker(() -> {
                    // This marker runs after the first splineTo()
                    robot.portArm.setPower((double) (1000 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (1000 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    // Run your action in here!
                })
                .forward(4)
                        .splineToSplineHeading(startPose.plus(new Pose2d(-8,24,Math.toRadians(0))), Math.toRadians(90))
                        .addDisplacementMarker(200.5, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        })
                        .addDisplacementMarker(201, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(201.5, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(202, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(202.5, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(203, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(203.5, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(204, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(204.5, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(205, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(205.5, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(206, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(206.5, ()->{
                            robot.portArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                            robot.starArm.setPower((double) (2300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                        }).addDisplacementMarker(207, ()->{
                            robot.portArm.setPower(0.3);
                            robot.starArm.setPower(0.3);
                        })
                        .splineToSplineHeading(startPose.plus(new Pose2d(-8,29,Math.toRadians(0))), Math.toRadians(90))
                .addDisplacementMarker(()->{
                    robot.portArm.setPower((double) (-300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                })
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    robot.portArm.setPower((double) (-300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                })
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    robot.portArm.setPower((double) (-300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                })
                .waitSeconds(0.1)
                .addDisplacementMarker(()->{
                    robot.portArm.setPower((double) (-300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                    robot.starArm.setPower((double) (-300 - robot.portArm.getCurrentPosition()) /(((2500*2.548)*0.7) /4));
                })
                .waitSeconds(0.1)
                .forward(5)
                .splineToLinearHeading( startPose.plus(new Pose2d (39,5,Math.toRadians(-90))), Math.toRadians(0))
                .build();
        waitForStart();
        if(opModeIsActive()) {
            drive.setPoseEstimate(startPose);
            robot.wrist.setPosition(wristPositions.get("mid"));
            robot.elbowPort.setPosition(elbowPositions.get("up"));
            robot.elbowStar.setPosition(1 - elbowPositions.get("up"));
            drive.followTrajectorySequence(thingy3);
            resetRuntime();
            while (getRuntime() < 1) {
                robot.portArm.setPower((double) (2150 - robot.portArm.getCurrentPosition()) / (((2500 * 2.548) * 0.7) / 4));
                robot.starArm.setPower((double) (2150 - robot.portArm.getCurrentPosition()) / (((2500 * 2.548) * 0.7) / 4));
                robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
            }
            drive.followTrajectorySequence(thingy1);
            /*while (getRuntime()<1) {
                robot.starSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
                robot.portSuitcase.setPower((suitcasePositions.get("up") - robot.starSuitcase.getCurrentPosition()) * (pterm / 950));
            }

             */
            resetRuntime();
            while (getRuntime()<20){
                robot.starSuitcase.setPower((suitcasePositions.get("down") - robot.starSuitcase.getCurrentPosition()) * (pterm2 / 1000));
                robot.portSuitcase.setPower((suitcasePositions.get("down") - robot.starSuitcase.getCurrentPosition()) * (pterm2 / 1000));
            }
        }
    }
}
