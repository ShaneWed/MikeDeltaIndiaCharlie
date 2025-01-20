import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ActivationFunction sigmoid = new Sigmoid();

        int testingEpochs = 500;
        double testingLearningRate = 0.05;

        for(int i = 0; i < 5; i++) {
            Thread multiThreadedTestModels = new Thread(new MultithreadedTestModels(16, 15, 26, testingEpochs, testingLearningRate, sigmoid));
            multiThreadedTestModels.setName(String.valueOf(i + 1));
            multiThreadedTestModels.start();
        }

        System.out.println("Multi layer perceptron created!");
    }
}
