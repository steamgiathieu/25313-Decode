package org.firstinspires.ftc.team25313.subsystems.util;

import java.util.List;

public class RegressionUtil {

    public static class RegressionResult {
        public final double slope;
        public final double intercept;

        public RegressionResult(double slope, double intercept) {
            this.slope = slope;
            this.intercept = intercept;
        }

        public double predict(double x) {
            return slope * x + intercept;
        }
    }

    // Tính hồi quy tuyến tính từ 2 list giá trị
    public static RegressionResult compute(List<Double> x, List<Double> y) {
        if (x.size() != y.size() || x.isEmpty()) {
            throw new IllegalArgumentException("Lists must have same non-zero length");
        }

        int n = x.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += x.get(i);
            sumY += y.get(i);
            sumXY += x.get(i) * y.get(i);
            sumX2 += x.get(i) * x.get(i);
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        return new RegressionResult(slope, intercept);
    }
}
