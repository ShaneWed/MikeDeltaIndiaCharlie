public class Main {
    public static void main(String[] args) {
        int testingEpochs = 1000;
        double testingLearningRate = 0.1;
        int numOfLayers = 3;
        int instances = 1;

        for(int i = 0; i < instances; i++) {
            Thread multiThreadedTestModels = new Thread(new MultithreadedTestModels(16, 20, 26, testingEpochs, testingLearningRate, new Sigmoid(), numOfLayers));
            multiThreadedTestModels.setName(String.valueOf(i + 1));
            multiThreadedTestModels.start();
        }

        System.out.println("Multi layer perceptron created!");
    }
}
