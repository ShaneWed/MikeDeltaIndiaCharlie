import java.util.Arrays;
import java.util.Random;

public class TrainingData {
    Random rand = new Random();
    double[][] trainingVectors;
    double[] trainingVectorOutputs;

    public double[][] xorInputData = new double[][]{
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };
    public double[] xorOutputData = new double[]{0, 1, 1, 0};

    public double[][] generateTrainingVectors(int noOfInputs, int noOfVectors) {
        trainingVectors = new double[noOfInputs][noOfVectors];
        trainingVectorOutputs = new double[noOfVectors];
        for(int i = 0; i < noOfVectors; i++) {
            for(int j = 0; j < noOfInputs; j++) {
                trainingVectors[j][i] = rand.nextDouble(-1, 1);
            }
        }
        for(int i = 0; i < noOfVectors; i++) {
            double combination = trainingVectors[0][i] - trainingVectors[1][i] + trainingVectors[2][i] - trainingVectors[3][i];
            trainingVectorOutputs[i] = Math.sin(combination);
        }
        return trainingVectors;
    }
}
