import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ActivationFunction tanh = new Tanh();

        double mdicLearningRate = 0.3;
        int mdicEpochs = 5000;
        double dariusLearningRate = 0.2;
        int dariusEpochs = 10000;

        MultiLayerPerceptron MikeDeltaIndiaCharlie = new MultiLayerPerceptron(2, 5, 1, mdicLearningRate, tanh);
        MultiLayerPerceptron Darius = new MultiLayerPerceptron(4, 5,1, dariusLearningRate, tanh);

        MikeDeltaIndiaCharlie.executeXorFunction(mdicEpochs);
        Darius.executeSinFunction(dariusEpochs);

        System.out.println("Multi layer perceptron created!");
    }
}
