public class Main {
    public static void main(String[] args) {
        ActivationFunction sigmoid = new Sigmoid();

        int testingEpochs = 1000;
        double testingLearningRate = 0.05;
        int numOfLayers = 3;
        int instances = 3;

        for(int i = 0; i < instances; i++) {
            Thread multiThreadedTestModels = new Thread(new MultithreadedTestModels(16, 5, 26, testingEpochs, testingLearningRate, sigmoid, numOfLayers));
            multiThreadedTestModels.setName(String.valueOf(i + 1));
            multiThreadedTestModels.start();
        }

        System.out.println("Multi layer perceptron created!");
    }
}
