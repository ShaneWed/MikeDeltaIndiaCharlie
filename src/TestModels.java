import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestModels {
    private final int numOfInputs;
    private final int hiddenUnitsPerLayer;
    private final int numOfOutputs;
    private final double learningRate;
    private final ActivationFunction function;
    private final TrainingData data = new TrainingData();
    private final String outputFileName;
    private final int epochs;

    public TestModels(int numOfInputs, int hiddenUnitsPerLayer, int numOfOutputs, int epochs, double learningRate, ActivationFunction function)  throws IOException {
        this.numOfInputs = numOfInputs;
        this.hiddenUnitsPerLayer = hiddenUnitsPerLayer;
        this.numOfOutputs = numOfOutputs;
        this.epochs = epochs;
        this.learningRate = learningRate;
        this.function = function;
        outputFileName = "C://Users//shane//IdeaProjects//MikeDeltaIndiaCharlie//testingOutputs//irvineTestingReport";
        //irvineTestingReport = new FileWriter(outputFile);
    }

    public void runTrainings(int rounds) throws IOException {
        for(int i = 0; i < rounds; i++) {
            File outputFile = new File(outputFileName + (i + 1) + ".txt");
            FileWriter irvineTestingReport = new FileWriter(outputFile);
            MultiLayerPerceptron perceptron = new MultiLayerPerceptron(numOfInputs, hiddenUnitsPerLayer, numOfOutputs, learningRate, function);
            irvineTestingReport.write("Testing report #" + (i + 1) + "\nInputs: " + numOfInputs + ", Hidden Units: " + hiddenUnitsPerLayer + ", Outputs: " + numOfOutputs + "\nLearning Rate: " + learningRate + ", Epochs: " + epochs + "\n\n");
            perceptron.train(data.trainingVectors, data.trainingVectorOutputs, epochs, irvineTestingReport);
            System.out.println("Training set" + perceptron.testOutputs(data.trainingVectors, data.trainingVectorOutputs));
            System.out.println("Testing set" + perceptron.testOutputs(data.testingVectors, data.testingVectorOutputs));
            irvineTestingReport.write("\nTraining set" + perceptron.testOutputs(data.trainingVectors, data.trainingVectorOutputs) + "\n" + "Testing set" + perceptron.testOutputs(data.testingVectors, data.testingVectorOutputs));
            irvineTestingReport.flush();
            irvineTestingReport.close();
        }
    }
}
