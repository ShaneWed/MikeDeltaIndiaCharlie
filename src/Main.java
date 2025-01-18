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
        int irvineEpochs = 1000; // 10007, 2438; 9465, 2275; 9903, 2395; 10008, 2477

        MultiLayerPerceptron Mike = new MultiLayerPerceptron(2, 5, 1, mikeLearningRate, tanh);
        MultiLayerPerceptron Darius = new MultiLayerPerceptron(4, 5,1, dariusLearningRate, tanh);
        MultiLayerPerceptron Irvine = new MultiLayerPerceptron(16, 15, 26, irvineLearningRate, sigmoid);

        //Mike.executeXorFunction(mikeEpochs);
        //Darius.executeSinFunction(dariusEpochs);
        //Irvine.exerciseIrvine(irvineEpochs);

        System.out.println("Multi layer perceptron created!");

        int testingEpochs = 1000;
        double testingLearningRate = 0.01;

        TestModels testing = new TestModels(16, 15, 26, testingEpochs, testingLearningRate, sigmoid);

        testing.runTrainings(1);
    }
}
