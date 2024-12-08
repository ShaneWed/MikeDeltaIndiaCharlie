import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class MultiLayerPerceptron {
    int numOfInputs;
    int numOfOutputs;
    int hiddenUnitsPerLayer;
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

    public double backwards(double[] t, double learningRate) {
        double error = 0;

        for(int i = layers.length - 1; i > 0; i--) {
            Layer currentLayer = layers[i];
            
            if(i == layers.length - 1) { // Output layer
                for(int j = 0; j < currentLayer.getNeurons().size(); j++) {
                    Neuron neuron = currentLayer.getNeurons().get(j);
                    error = t[j] - neuron.getValue();
                    double delta = error * Sigmoid.sigmoidDerivative(neuron.getPreActivation());
                    neuron.setDelta(delta);
                    
                    // Update weights
                    for(int k = 0; k < layers[i - 1].getNeurons().size(); k++) {
                        double weightDelta = learningRate * delta * layers[i - 1].getNeurons().get(k).getValue();
                        neuron.updateWeights(k, weightDelta);
                    }
                    double biasDelta = learningRate * delta;
                    neuron.setBias(neuron.getBias() + biasDelta);
                }
            } else { // Hidden layer
                for(int j = 0; j < currentLayer.getNeurons().size(); j++) {
                    Neuron neuron = currentLayer.getNeurons().get(j);
                    double delta = 0;

                    for(int k = 0; k < layers[i + 1].getNeurons().size(); k++) {
                        Neuron nextNeuron = layers[i + 1].getNeurons().get(k);
                        delta += nextNeuron.getDelta() * nextNeuron.getWeights()[j];
                    }
                    delta *= Sigmoid.sigmoidDerivative(neuron.getPreActivation());
                    neuron.setDelta(delta);

                    for(int k = 0; k < layers[i - 1].getNeurons().size(); k++) {
                        double weightDelta = learningRate * delta * layers[i - 1].getNeurons().get(k).getValue();
                        neuron.updateWeights(k, weightDelta);
                    }
                    double biasDelta = learningRate * delta;
                    neuron.setBias(neuron.getBias() + biasDelta);
                }
            }
        }
        return error;
    }

    public void forward(double[] I) {
        // Set input layer values
        for(int i = 0; i < I.length; i++) {
            layers[0].getNeurons().get(i).setValue(I[i]);
        }

        // Process hidden and output layers
        for(int l = 1; l < layers.length; l++) {
            for(Neuron currentNeuron : layers[l].getNeurons()) {
                double weightSum = 0;
                // Sum up inputs * weights from previous layer
                for(int i = 0; i < layers[l - 1].getNeurons().size(); i++) {
                    weightSum += layers[l - 1].getNeurons().get(i).getValue() * currentNeuron.getWeights()[i];
                }
                weightSum += currentNeuron.getBias();
                currentNeuron.setPreActivation(weightSum);
                currentNeuron.setValue(Sigmoid.calculateY(weightSum));
            }
        }
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

    public void train(double[][] input, double[] output, int epochs, FileWriter writer) throws IOException {
        double totalError = 0;
        for(int epoch = 0; epoch < epochs; epoch++) {
            totalError = 0;
            for(int i = 0; i < input.length; i++) {
                forward(input[i]);
                double error = backwards(new double[]{output[i]}, learningRate);
                totalError += Math.abs(error);
            }
            if(epoch % 1000 == 0) {
                writer.write(epoch + "," + totalError + "\n");
                writer.flush();
                System.out.println("Epoch #" + epoch + ", Current error: " + totalError);
            }
        }
        writer.write(epochs + "," + totalError + "\n");
        writer.flush();
        System.out.println("Epoch #" + epochs + ", Current error: " + totalError);
        writer.close();
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
