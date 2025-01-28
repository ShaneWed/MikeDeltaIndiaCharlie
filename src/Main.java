import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int testingEpochs = 1000;
        double testingLearningRate = 0.1;
        int numOfLayers = 3;
        int instances = 1;

        TrainingData data = new TrainingData(4, 1, 500);
        MultiLayerPerceptron multiLayerPerceptron = new MultiLayerPerceptron(4, 5, 1, 0.05, new Tanh(), data, 3);
        multiLayerPerceptron.executeSinFunction(1000);
        multiLayerPerceptron.testOutputs(data.testingVectors, data.testingVectorOutputs);

        for(int i = 0; i < instances; i++) {
            Thread multiThreadedTestModels = new Thread(new MultithreadedTestModels(16, 20, 26, testingEpochs, testingLearningRate, new ReLU(), numOfLayers));
            multiThreadedTestModels.setName(String.valueOf(i + 1));
            //multiThreadedTestModels.start();
        }

        System.out.println("Multi layer perceptron created!");
    }
}
