import java.util.Random;

public class TrainingData {
    Random rand = new Random();
    double[][] trainingVectors;
    double[] trainingVectorOutputs;
    double[][] testingVectors;
    double[] testingVectorOutputs;

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
    // This is me being lazy because I didn't want to have to figure out how to split the array. It works so I don't really care!
    public void generateTestingVectors(int noOfInputs, int noOfVectors) {
        testingVectors = new double[noOfVectors][noOfInputs];
        testingVectorOutputs = new double[noOfVectors];
        for(int i = 0; i < noOfVectors; i++) {
            for(int j = 0; j < noOfInputs; j++) {
                testingVectors[i][j] = rand.nextDouble(-1, 1);
            }
        }
        for(int i = 0; i < noOfVectors; i++) {
            double combination = testingVectors[i][0] - testingVectors[i][1] + testingVectors[i][2] - testingVectors[i][3];
            testingVectorOutputs[i] = Math.sin(combination);
        }
    }

    public TrainingData(int noOfInputs, int noOfVectors) {
        generateTrainingVectors(noOfInputs, noOfVectors - 100);
        generateTestingVectors(noOfInputs, 100);
    }
}
