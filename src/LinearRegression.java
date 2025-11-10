import java.io.*;
import java.util.*;

public class LinearRegression {
    private double[] weights;
    private double bias;
    private double[] mean;
    private double[] std;

    public LinearRegression() {
        this.bias = 0.0;
    }

    public double[][] scaleData(double[][] X) {
        int n = X.length;
        int m = X[0].length;
        mean = new double[m];
        std = new double[m];
        double[][] scaled = new double[n][m];

        for (int j = 0; j < m; j++) {
            double sum = 0;
            for (int i = 0; i < n; i++) sum += X[i][j];
            mean[j] = sum / n;
            double sqSum = 0;
            for (int i = 0; i < n; i++) sqSum += Math.pow(X[i][j] - mean[j], 2);
            std[j] = Math.sqrt(sqSum / n);

            for (int i = 0; i < n; i++) {
                scaled[i][j] = (X[i][j] - mean[j]) / (std[j] + 1e-9);
            }
        }
        return scaled;
    }

    public void fit(double[][] X, double[] y) {
        int n = X.length;
        int m = X[0].length;
        double[][] Xb = new double[n][m + 1];
        for (int i = 0; i < n; i++) {
            Xb[i][0] = 1.0;
            for (int j = 0; j < m; j++) {
                Xb[i][j + 1] = X[i][j];
            }
        }

        double[][] XtX = multiply(transpose(Xb), Xb);
        double[][] XtX_inv = invert(XtX);
        double[][] Xty = multiply(transpose(Xb), y);
        double[][] theta = multiply(XtX_inv, Xty);

        bias = theta[0][0];
        weights = new double[m];
        for (int j = 0; j < m; j++) {
            weights[j] = theta[j + 1][0];
        }
    }

    public double[] predict(double[][] X) {
        int n = X.length;
        int m = X[0].length;
        double[] yPred = new double[n];

        for (int i = 0; i < n; i++) {
            double sum = bias;
            for (int j = 0; j < m; j++) {
                sum += X[i][j] * weights[j];
            }
            yPred[i] = sum;
        }
        return yPred;
    }

    public double score(double[][] X, double[] y) {
        double[] yPred = predict(X);
        double mse = 0.0;
        for (int i = 0; i < y.length; i++) {
            mse += Math.pow(y[i] - yPred[i], 2);
        }
        mse /= y.length;
        return mse;
    }

    private double[][] transpose(double[][] A) {
        int n = A.length;
        int m = A[0].length;
        double[][] T = new double[m][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                T[j][i] = A[i][j];
        return T;
    }

    private double[][] multiply(double[][] A, double[][] B) {
        int n = A.length, m = A[0].length, p = B[0].length;
        double[][] C = new double[n][p];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < m; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    private double[][] multiply(double[][] A, double[] y) {
        int n = A.length, m = A[0].length;
        double[][] result = new double[m][1];
        for (int i = 0; i < m; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += A[j][i] * y[j];
            }
            result[i][0] = sum;
        }
        return result;
    }

    private double[][] invert(double[][] A) {
        int n = A.length;
        double[][] aug = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                aug[i][j] = A[i][j];
            }
            aug[i][i + n] = 1;
        }
        for (int i = 0; i < n; i++) {
            double diag = aug[i][i];
            for (int j = 0; j < 2 * n; j++) aug[i][j] /= diag;
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = aug[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        aug[k][j] -= factor * aug[i][j];
                    }
                }
            }
        }
        double[][] inv = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                inv[i][j] = aug[i][j + n];
        return inv;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getBias() {
        return bias;
    }
}
