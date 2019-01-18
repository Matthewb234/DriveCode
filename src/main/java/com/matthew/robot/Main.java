package com.matthew.robot;

import com.matthew.robot.SimpleRobot;
import edu.wpi.first.wpilibj.RobotBase;

public class Main {


    public static void main(String[] args) {
        RobotBase.startRobot(SimpleRobot::new);
    }
}
