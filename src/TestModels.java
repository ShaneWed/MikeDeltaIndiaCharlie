import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestModels {
    int numOfInputs = 16;
    int hiddenUnitsPerLayer = 15;
    int numOfOutputs = 26;
    double learningRate = 0.01;
    ActivationFunction sigmoid = new Sigmoid();
    TrainingData data = new TrainingData();
    String outputFileName;
    FileWriter irvineTestingReport;

    int epochs = 1000;

    public TestModels() throws IOException {
        outputFileName = "C://Users//shane//IdeaProjects//MikeDeltaIndiaCharlie//testingOutputs//irvineTestingReport";
        //irvineTestingReport = new FileWriter(outputFile);
    }

    public static void main(String[] args) throws IOException {


        //MultiLayerPerceptron UCIrvine = new MultiLayerPerceptron(16, 15, 26, learningRate, sigmoid);
    }

    public void runTrainings(int rounds) throws IOException {
        for(int i = 0; i < rounds; i++) {
            File outputFile = new File(outputFileName + (i + 1) + ".txt");
            irvineTestingReport = new FileWriter(outputFile);
            MultiLayerPerceptron perceptron = new MultiLayerPerceptron(numOfInputs, hiddenUnitsPerLayer, numOfOutputs, learningRate, sigmoid);
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
