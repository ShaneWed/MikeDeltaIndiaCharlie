import java.util.Arrays;
import java.util.Random;

public class MultiLayeredPerceptron {
    final int numOfInputs = 4;
    final int numOfOutputs = 1;
    final int hiddenUnitsPerLayer = 5;
    double learningRate;

    Layer[] layers;

    // This will have an input layer, one hidden layer, and an output layer
    // Try to implement variable hidden layers eventually
    public MultiLayeredPerceptron(double learningRate) {
        this.learningRate = learningRate;
        TrainingData data = new TrainingData(4, 500);

        // can probably just leave previousWeights as nothing since randomise changes it
        Layer lowerLayer = new Layer(0, numOfInputs, numOfInputs); // input
        Layer upperLayer = new Layer(1, hiddenUnitsPerLayer, numOfInputs); // hidden
        Layer outputLayer = new Layer(2, numOfOutputs, hiddenUnitsPerLayer); // output
        layers = new Layer[] {lowerLayer, upperLayer, outputLayer};

        randomise();

        // XOR function
        /*
        train(data.xorInputData, data.xorOutputData, 100000);
        testOutputs(data.xorInputData);

        System.out.println("Does the network correctly predict the XOR function?");
        correctlyPredicts(data);*/

        train(data.trainingVectors, data.trainingVectorOutputs, 100000);
        System.out.println("Training set");
        testOutputs(data.trainingVectors, data.trainingVectorOutputs, 0.1);
        System.out.println("Testing set");
        testOutputs(data.testingVectors, data.testingVectorOutputs, 0.1);
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

    public double backwards(double[][] inputs, double[] t, double learningRate) {
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
            } else { // Hidden layers
                for(int j = 0; j < currentLayer.getNeurons().size(); j++) {
                    Neuron neuron = currentLayer.getNeurons().get(j);
                    double delta = 0;
                    
                    // Calculate delta based on next layer
                    for(int k = 0; k < layers[i + 1].getNeurons().size(); k++) {
                        Neuron nextNeuron = layers[i + 1].getNeurons().get(k);
                        delta += nextNeuron.getDelta() * nextNeuron.getWeights()[j];
                    }
                    delta *= Sigmoid.sigmoidDerivative(neuron.getPreActivation());
                    neuron.setDelta(delta);
                    
                    // Update weights
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
        System.out.println("Testing outputs...");
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
        System.out.println("Correct outputs: " + correctOutputs + "/" + outputs.length);
    }

    public void train(double[][] input, double[] output, int epochs) {
        for(int epoch = 0; epoch < epochs; epoch++) {
            double totalError = 0;

            // Process each training example individually
            for(int i = 0; i < input.length; i++) {
                // Forward pass
                forward(input[i]);

                // Backward pass
                double error = backwards(
                        new double[][]{input[i]},
                        new double[]{output[i]},
                        learningRate
                );
                totalError += Math.abs(error);
            }

            if(epoch % 1000 == 0) {
                System.out.printf("Epoch %d, Error: %.4f%n", epoch, totalError);
            }
        }
    }

    public void correctlyPredicts(TrainingData data) {
        int[] actualOutput = new int[data.xorOutputData.length];
        for(int i = 0; i < data.xorInputData.length; i++) {
            forward(data.xorInputData[i]);
            System.out.println("Expected " + Arrays.toString(data.xorInputData[i]) + " = " + data.xorOutputData[i]);
            System.out.println("Actual " + Arrays.toString(data.xorInputData[i]) + " = " + layers[layers.length - 1].getNeurons().getFirst().getRoundedValue());
            actualOutput[i] = layers[layers.length - 1].getNeurons().getFirst().getRoundedValue();
        }
        boolean outputMatches = true;
        for(int i = 0; i < actualOutput.length; i++) {
            if(actualOutput[i] != (int) data.xorOutputData[i]) {
                outputMatches = false;
            }
        }
        if(outputMatches) {
            System.out.println("Network correctly predicts XOR!");
        } else {
            System.out.println("L network");
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
