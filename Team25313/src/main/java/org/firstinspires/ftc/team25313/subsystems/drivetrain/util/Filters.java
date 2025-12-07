package org.firstinspires.ftc.team25313.subsystems.drivetrain.util;

import java.util.Arrays;

public class Filters {
    public class LowPassFilter {
        private double alpha;
        private double state = 0;

        public LowPassFilter(double alpha) {
            this.alpha = alpha;
        }
        public double update(double input) {
            state = alpha * input + (1 - alpha) * state;
            return state;
        }
    }


    public class MedianFilter {
        private final int size;
        private final double[] buffer;
        private int index = 0;

        public MedianFilter(int size) {
            this.size = size;
            buffer = new double[size];
        }

        public double update(double value) {
            buffer[index] = value;
            index = (index + 1) % size;

            double[] temp = buffer.clone();
            Arrays.sort(temp);
            return temp[size / 2];
        }
    }

    public class MovingAvarage {
        private final double[] buffer;
        private int idx = 0;

        public MovingAvarage(int size) {
            buffer = new double[size];
        }

        public double update(double input) {
            buffer[idx] = input;
            idx = (idx + 1) % buffer.length;

            double sum = 0;
            for (double v : buffer) sum += v;
            return sum / buffer.length;
        }
    }
}
