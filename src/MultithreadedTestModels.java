import java.io.IOException;

public class MultithreadedTestModels extends TestModels implements Runnable {

    public MultithreadedTestModels(int numOfInputs, int hiddenUnitsPerLayer, int numOfOutputs, int epochs, double learningRate, ActivationFunction function) throws IOException {
        super(numOfInputs, hiddenUnitsPerLayer, numOfOutputs, epochs, learningRate, function);
    }

    @Override
    public void run() {
        try {
            runTrainingsByID((int) Thread.currentThread().threadId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        run();
    }
}
