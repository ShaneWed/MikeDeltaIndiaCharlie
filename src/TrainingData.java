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

    public void generateTrainingVectors(int noOfInputs, int noOfVectors) {
        trainingVectors = new double[noOfVectors][noOfInputs];
        trainingVectorOutputs = new double[noOfVectors];
        for(int i = 0; i < noOfVectors; i++) {
            for(int j = 0; j < noOfInputs; j++) {
                trainingVectors[i][j] = rand.nextDouble(-1, 1);
            }
        }
        for(int i = 0; i < noOfVectors; i++) {
            double combination = trainingVectors[i][0] - trainingVectors[i][1] + trainingVectors[i][2] - trainingVectors[i][3];
            trainingVectorOutputs[i] = Math.sin(combination);
        }
    }

    public TrainingData(int noOfInputs, int noOfVectors) {
        generateTrainingVectors(noOfInputs, noOfVectors);
    }
}
