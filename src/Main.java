import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        double mdicLearningRate = 0.3;
        int mdicEpochs = 1000;
        double dariusLearningRate = 0.2;
        int dariusEpochs = 2000;

        MultiLayerPerceptron MikeDeltaIndiaCharlie = new MultiLayerPerceptron(4, 5, 1, mdicLearningRate);
        MultiLayerPerceptron Darius = new MultiLayerPerceptron(4, 5,1, dariusLearningRate);

        MikeDeltaIndiaCharlie.executeXorFunction(mdicEpochs);
        Darius.executeSinFunction(dariusEpochs);

        System.out.println("Multi layer perceptron created!\n");
    }
}
