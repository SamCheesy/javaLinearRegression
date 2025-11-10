import java.io.*;
import java.util.*;

public class Main {
    public static double[][] readCSV_X(String filename) {
        List<double[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                double[] values = new double[parts.length - 1];
                for (int i = 0; i < parts.length - 1; i++) {
                    values[i] = Double.parseDouble(parts[i]);
                }
                rows.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows.toArray(new double[0][]);
    }

    public static double[] readCSV_y(String filename) {
        List<Double> yList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                yList.add(Double.parseDouble(parts[parts.length - 1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        double[] y = new double[yList.size()];
        for (int i = 0; i < yList.size(); i++) y[i] = yList.get(i);
        return y;
    }

    public static void main(String[] args) {
        System.out.println("==== SIMPLE LINEAR REGRESSION ====");
        double[][] X_simple = readCSV_X("data/Ice_cream_selling_data.csv");
        double[] y_simple = readCSV_y("data/Ice_cream_selling_data.csv");

        LinearRegression modelSimple = new LinearRegression();
        X_simple = modelSimple.scaleData(X_simple);
        modelSimple.fit(X_simple, y_simple);

        System.out.println("Weights: " + Arrays.toString(modelSimple.getWeights()));
        System.out.println("Bias: " + modelSimple.getBias());
        double[] y_pred_simple = modelSimple.predict(X_simple);
        System.out.println("Predictions: " + Arrays.toString(y_pred_simple));
        System.out.println("Score (MSE): " + modelSimple.score(X_simple, y_simple));

        System.out.println("\n==== MULTIPLE LINEAR REGRESSION ====");
        double[][] X_multi = readCSV_X("data/student_exam_scores.csv");
        double[] y_multi = readCSV_y("data/student_exam_scores.csv");

        LinearRegression modelMulti = new LinearRegression();
        X_multi = modelMulti.scaleData(X_multi);
        modelMulti.fit(X_multi, y_multi);

        System.out.println("Weights: " + Arrays.toString(modelMulti.getWeights()));
        System.out.println("Bias: " + modelMulti.getBias());
        double[] y_pred_multi = modelMulti.predict(X_multi);
        System.out.println("Score (MSE): " + modelMulti.score(X_multi, y_multi));
    }
}
