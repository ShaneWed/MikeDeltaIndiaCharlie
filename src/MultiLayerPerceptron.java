import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class MultiLayerPerceptron {
    int numOfInputs;
    int numOfOutputs;
    int hiddenUnitsPerLayer; // These num of x are probably redundant, only used in the constructor
    double learningRate;
    TrainingData data;
    Layer[] layers;

    FileWriter sinErrorReport = new FileWriter("SinErrorReport");
    FileWriter xorErrorReport = new FileWriter("XorErrorReport");

    // This will have an input layer, one hidden layer, and an output layer
    // Try to implement variable hidden layers eventually
    public MultiLayerPerceptron(int numOfInputs, int hiddenUnitsPerLayer, int numOfOutputs, double learningRate) throws IOException {
        this.numOfInputs = numOfInputs;
        this.hiddenUnitsPerLayer = hiddenUnitsPerLayer;
        this.numOfOutputs = numOfOutputs;
        this.learningRate = learningRate;
        this.data = new TrainingData(4, 500);

        // Should move this to a fancy little layer factory
        Layer lowerLayer = new Layer(0, numOfInputs, numOfInputs); // input
        Layer upperLayer = new Layer(1, hiddenUnitsPerLayer, numOfInputs); // hidden
        Layer outputLayer = new Layer(2, numOfOutputs, hiddenUnitsPerLayer); // output
        layers = new Layer[] {lowerLayer, upperLayer, outputLayer};

        randomise();
    }

    public void executeSinFunction(int epochs) throws IOException {
        System.out.println("Executing with the sin function...");
        sinErrorReport.write("Epochs: " + epochs + ", Learning Rate: " + learningRate + "\n");
        train(data.trainingVectors, data.trainingVectorOutputs, epochs, sinErrorReport);
        System.out.print("Training set");
        testOutputs(data.trainingVectors, data.trainingVectorOutputs, 0.1);
        System.out.print("Testing set");
        testOutputs(data.testingVectors, data.testingVectorOutputs, 0.1);
    }

    public void executeXorFunction(int epochs) throws IOException {
        System.out.println("Executing with xor function...");
        xorErrorReport.write("Epochs: " + epochs + ", Learning Rate: " + learningRate + "\n");
        train(data.xorInputData, data.xorOutputData, epochs, xorErrorReport);
        System.out.print("Training set");
        testOutputs(data.xorInputData, data.xorOutputData, 0);
        correctlyPredicts(data);
    }

    public void randomise() {
        // Maybe make it so input doesn't get set weights? Or we can just ignore them during the other functions
        Random rand = new Random();
        for(Layer l : layers) {
            if(l.layerNumber() != 0) {
                for(Neuron n : l.getNeurons()) {
                    n.setRandomWeights(rand);
                    n.setBias(rand.nextDouble(-1, 1));
                }
            }
        }
    }

    public void forward(double[] I) {
        for(int i = 0; i < I.length; i++) {
            layers[0].getNeurons().get(i).setValue(I[i]);
        }

        for(int l = 1; l < layers.length; l++) {
            for(Neuron n : layers[l].getNeurons()) {
                double weightSum = 0;
                for(int i = 0; i < layers[l - 1].getNeurons().size(); i++) {
                    weightSum += layers[l - 1].getNeurons().get(i).getValue() * n.getWeights()[i];
                }
                weightSum += n.getBias();
                n.setPreActivation(weightSum);
                n.setValue(Tanh.calculateTanh(weightSum));
            }
        }
    }

    public double backwards(double[] t, double learningRate) {
        double error = 0;
        double delta;

        for(int i = layers.length - 1; i > 0; i--) {
            Layer currentLayer = layers[i];
            
            if(i == layers.length - 1) { // Output layer
                for(int j = 0; j < currentLayer.getNeurons().size(); j++) {
                    Neuron n = currentLayer.getNeurons().get(j);
                    error = t[j] - n.getValue();
                    delta = error * Tanh.tanhDerivative(n.getPreActivation());
                    n.updateWeights(delta, layers[i - 1], learningRate);
                }
            } else { // Hidden layers
                for(int j = 0; j < currentLayer.getNeurons().size(); j++) {
                    Neuron n = currentLayer.getNeurons().get(j);
                    delta = 0;

                    for(int k = 0; k < layers[i + 1].getNeurons().size(); k++) {
                        Neuron nextNeuron = layers[i + 1].getNeurons().get(k);
                        delta += nextNeuron.getDelta() * nextNeuron.getWeights()[j];
                    }
                    delta *= Tanh.tanhDerivative(n.getPreActivation());
                    n.updateWeights(delta, layers[i - 1], learningRate);
                }
            }
        }
        return error;
    }

    public void train(double[][] input, double[] output, int epochs, FileWriter writer) throws IOException {
        double totalError = 0;
        for(int i = 0; i < epochs; i++) {
            totalError = 0;
            for(int j = 0; j < input.length; j++) {
                forward(input[j]);
                double error = backwards(new double[]{output[j]}, learningRate);
                totalError += Math.abs(error);
            }
            if(i % 1000 == 0) {
                writer.write(i + "," + totalError + "\n");
                writer.flush();
                System.out.println("Epoch #" + i + ", Current error: " + totalError);
            }
        }
        writer.write(epochs + "," + totalError + "\n");
        writer.flush();
        System.out.println("Epoch #" + epochs + ", Current error: " + totalError);
        writer.close();
    }

    public void testOutputs(double[][] inputs, double[] outputs, double acceptableError) { // Maybe move this to TrainingData to clean up the file
        int count = 0;
        int correctOutputs = 0;
        for (double[] input : inputs) {
            forward(input);
            double output = layers[layers.length - 1].getNeurons().getFirst().getValue();
            if(Math.abs(output - outputs[count++]) < acceptableError) {
                correctOutputs++;
            }
            //System.out.println("Input: " + Arrays.toString(input) + ", Actual Output: " + output + ", Expected Output: " + outputs[count++]);
        }
        System.out.println(" correct outputs: " + correctOutputs + "/" + outputs.length);
    }

    public void correctlyPredicts(TrainingData data) { // Only works for XOR, sucks anyway
        double[] actualOutput = new double[data.xorOutputData.length];
        for(int i = 0; i < data.xorInputData.length; i++) {
            forward(data.xorInputData[i]);
            System.out.print("Expected " + Arrays.toString(data.xorInputData[i]) + " = " + data.xorOutputData[i]);
            System.out.println(", Actual " + Arrays.toString(data.xorInputData[i]) + " = " + layers[layers.length - 1].getNeurons().getFirst().getRoundedValue());
            actualOutput[i] = layers[layers.length - 1].getNeurons().getFirst().getRoundedValue();
        }
        boolean outputMatches = true;
        for(int i = 0; i < actualOutput.length; i++) {
            if(actualOutput[i] != (int) data.xorOutputData[i]) {
                System.out.println((int) data.xorOutputData[i]);
                outputMatches = false;
            }
        }
        if(outputMatches) {
            System.out.println("Network correctly predicts XOR!");
        } else {
            System.out.println("Network fails to correctly predict XOR...");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(Layer l : layers) {
            sb.append("Layer ").append(l.layerNumber()).append("\n");
            for(Neuron n : l.getNeurons()) {
                sb.append("Neuron ").append(count).append(" with weight ").append(n.getValue()).append(", bias ").append(n.getBias()).append(", and value ").append(n.getValue()).append("\n");
                count++;
            }
            count = 0;
        }
        return sb.toString();
    }
}
