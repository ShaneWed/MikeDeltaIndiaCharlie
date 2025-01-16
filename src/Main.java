import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ActivationFunction tanh = new Tanh();
        ActivationFunction sigmoid = new Sigmoid();

        double mikeLearningRate = 0.3;
        int mikeEpochs = 5000;
        double dariusLearningRate = 0.05;
        int dariusEpochs = 10000;
        double irvineLearningRate = 0.2;
        int irvineEpochs = 1000; // 9733, 2361;

        MultiLayerPerceptron Mike = new MultiLayerPerceptron(2, 5, 1, mikeLearningRate, tanh);
        MultiLayerPerceptron Darius = new MultiLayerPerceptron(4, 5,1, dariusLearningRate, tanh);
        MultiLayerPerceptron Irvine = new MultiLayerPerceptron(16, 10, 26, irvineLearningRate, sigmoid);

        //Mike.executeXorFunction(mikeEpochs);
        //Darius.executeSinFunction(dariusEpochs);
        Irvine.exerciseIrvine(irvineEpochs);

        System.out.println("Multi layer perceptron created!");
    }
}
