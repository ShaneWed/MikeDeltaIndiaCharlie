import java.io.IOException;

public class MultithreadedTestModels extends TestModels implements Runnable {

    public MultithreadedTestModels(int numOfInputs, int hiddenUnitsPerLayer, int numOfOutputs, int epochs, double learningRate, ActivationFunction function, int numOfLayers) {
        super(numOfInputs, hiddenUnitsPerLayer, numOfOutputs, epochs, learningRate, function, numOfLayers);
    }

    @Override
    public void run() {
        try {
            // Sketchy but allows TestModels training to have less duplicate code
            runTrainingsByID(Integer.parseInt(Thread.currentThread().getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
