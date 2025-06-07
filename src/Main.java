public class Main {
    public static void main(String[] args) {
        int testingEpochs = 1000;
        double testingLearningRate = 0.05;
        int numOfLayers = 5;
        int instances = 1;

        for(int i = 0; i < instances; i++) {
            Thread multiThreadedTestModels = new Thread(new MultithreadedTestModels(16, 80, 26, testingEpochs, testingLearningRate, new Sigmoid(), numOfLayers));
            multiThreadedTestModels.setName(String.valueOf(i + 1));
            multiThreadedTestModels.start();
        }

        System.out.println("Multi layer perceptron created!");
    }
}
