import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// This class is not intended to test that the code works, but instead to test the results of training multiple models.
// Only implemented for the irvine problem, won't do it for xor or sin because they are simple.
public class TestModels {
    private final int numOfInputs;
    private final int hiddenUnitsPerLayer;
    private final int numOfOutputs;
    private final double learningRate;
    private final ActivationFunction function;
    private final TrainingData data = new TrainingData();
    private final String outputFileName;
    private final int epochs;
    private final int numOfLayers;

    public TestModels(int numOfInputs, int hiddenUnitsPerLayer, int numOfOutputs, int epochs, double learningRate, ActivationFunction function, int numOfLayers) {
        this.numOfInputs = numOfInputs;
        this.hiddenUnitsPerLayer = hiddenUnitsPerLayer;
        this.numOfOutputs = numOfOutputs;
        this.epochs = epochs;
        this.learningRate = learningRate;
        this.function = function;
        this.numOfLayers = numOfLayers;
        // Will cause issues if switching computers
        //outputFileName = "C://Users//shane//IdeaProjects//MikeDeltaIndiaCharlie//testingOutputs//irvineTestingReport" + numOfInputs + "-" + hiddenUnitsPerLayer + "-" + numOfOutputs + "-" + learningRate * 100 + "-" + epochs + "-#";
        outputFileName = "/home/shane/IdeaProjects/MikeDeltaIndiaCharlie/testingOutputs/irvineTestingReport" + numOfInputs + "-" + hiddenUnitsPerLayer + "-" + numOfOutputs + "-" + learningRate * 100 + "-" + epochs + "-" + numOfLayers + "-#";
    }

    public void runTrainingsByID(int id) throws IOException {
        training(id);
    }

    private void training(int id) throws IOException {
        long startTime = System.currentTimeMillis();
        File outputFile = new File(outputFileName + id + ".txt");
        FileWriter irvineTestingReport = new FileWriter(outputFile);
        MultiLayerPerceptron perceptron = new MultiLayerPerceptron(numOfInputs, hiddenUnitsPerLayer, numOfOutputs, learningRate, function, data, numOfLayers);
        irvineTestingReport.write("Testing report #" + id + "\nInputs: " + numOfInputs + ", Hidden Units: " + hiddenUnitsPerLayer + ", Outputs: " + numOfOutputs + "\nLearning Rate: " + learningRate + ", Epochs: " + epochs + "\nActivation Function: " + function + ", Layers: " + numOfLayers + "\n\n");
        perceptron.train(data.irvineTrainingInput, data.irvineTrainingOutput, epochs, irvineTestingReport);
        System.out.println("Training set" + perceptron.testOutputs(data.irvineTrainingInput, data.irvineTrainingOutput));
        System.out.println("Testing set" + perceptron.testOutputs(data.irvineTestingInput, data.irvineTestingOutput));
        irvineTestingReport.write("\nTraining set" + perceptron.testOutputs(data.irvineTrainingInput, data.irvineTrainingOutput) + "\n" + "Testing set" + perceptron.testOutputs(data.irvineTestingInput, data.irvineTestingOutput) + "\n");
        irvineTestingReport.write("Time taken: " + (System.currentTimeMillis() - startTime) / 1000 + " seconds\n");
        irvineTestingReport.flush();
        irvineTestingReport.close();
    }
}
