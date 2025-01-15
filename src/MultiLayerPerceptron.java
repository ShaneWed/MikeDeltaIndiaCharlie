import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MultiLayerPerceptron {
    int numOfInputs;
    int numOfOutputs;
    int hiddenUnitsPerLayer; // These num of x are probably redundant, only used in the constructor
    double learningRate;
    TrainingData data;
    ActivationFunction activationFunction;
    Layer[] layers;

    FileWriter sinErrorReport = new FileWriter("SinErrorReport");
    FileWriter xorErrorReport = new FileWriter("XorErrorReport");
    FileWriter irvineErrorReport = new FileWriter("IrvineErrorReport");

    // This will have an input layer, one hidden layer, and an output layer
    // Try to implement variable hidden layers eventually
    public MultiLayerPerceptron(int numOfInputs, int hiddenUnitsPerLayer, int numOfOutputs, double learningRate, ActivationFunction function) throws IOException {
        this.activationFunction = function;
        this.numOfInputs = numOfInputs;
        this.hiddenUnitsPerLayer = hiddenUnitsPerLayer;
        this.numOfOutputs = numOfOutputs;
        this.learningRate = learningRate;
        // TODO Currently have to manually change which data set is used, implement this better
        this.data = new TrainingData();
        //this.data = new TrainingData(4, 1, 500);

        // Should move this to a fancy little layer factory
        Layer lowerLayer = new Layer(0, numOfInputs, numOfInputs); // input
        Layer upperLayer = new Layer(1, hiddenUnitsPerLayer, numOfInputs); // hidden
        Layer outputLayer = new Layer(2, numOfOutputs, hiddenUnitsPerLayer); // output
        layers = new Layer[] {lowerLayer, upperLayer, outputLayer};

        randomise();
    }
    
    public void exerciseIrvine(int epochs) throws IOException {
        System.out.println("Executing Irvine...");
        irvineErrorReport.write("Epochs: " + epochs + ", Learning Rate: " + learningRate + "\n");
        train(data.trainingVectors, data.trainingVectorOutputs, epochs, irvineErrorReport);
        System.out.print("Training set");
        testOutputs(data.trainingVectors, data.trainingVectorOutputs, 0.05);
        System.out.print("Testing set");
        testOutputs(data.testingVectors, data.testingVectorOutputs, 0.05);
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
                n.setValue(activationFunction.calculate(weightSum));
            }
        }
    }

    public double backwards(double[] outputs, double learningRate) {
        double error = 0;
        double delta;

        for(int i = layers.length - 1; i > 0; i--) {
            Layer currentLayer = layers[i];
            
            if(i == layers.length - 1) { // Output layer
                for(int j = 0; j < currentLayer.getNeurons().size(); j++) { // TODO error isn't being properly calculated or adjusted for during backpropagation
                    Neuron n = currentLayer.getNeurons().get(j);
                    //System.out.println("Value of " + j + "th neuron: " + n.getValue() + ", Expected: " + outputs[j]);
                    error = outputs[j] - n.getValue();
                    delta = error * activationFunction.calculateDerivative(n.getPreActivation());
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
                    delta *= activationFunction.calculateDerivative(n.getPreActivation());
                    n.updateWeights(delta, layers[i - 1], learningRate);
                }
            }
        }
        error = error / outputs.length;
        return error;
    }

    public void train(double[][] input, double[][] output, int epochs, FileWriter writer) throws IOException {
        double totalError = 0;
        for(int i = 0; i < epochs; i++) {
            totalError = 0;
            for(int j = 0; j < input.length; j++) { // Logic here is incorrect
                forward(input[j]);
                // Following line needs to pass an array of expected output for each neuron in output layer
                // Should be doing this ^ now, is still a potential source of bugs I haven't checked it out much
                double error = backwards(output[j], learningRate);
                totalError += Math.abs(error);
            }

            if(i % 100 == 0) {
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

    public void testOutputs(double[][] inputs, double[][] outputs, double acceptableError) { // Maybe move this to TrainingData to clean up the file
        int count = 0;
        int correctOutputs = 0;
        boolean correct = true;

        for(int i = 0; i < inputs.length; i++) {
            forward(inputs[i]);
            double maxOutput = -1;
            int maxOutputIndex = 0;
            int correctOutputIndex = 0;
            for(int j = 0; j < outputs[i].length; j++) {
                if(outputs[i][j] > maxOutput) {
                    maxOutput = outputs[i][j];
                    maxOutputIndex = j;
                }
                if(outputs[i][j] == 1) {
                    correctOutputIndex = j;
                }
                // check for max output of neuron, compare if this is the same as expected output
            }
            if(maxOutputIndex == correctOutputIndex) {
                correctOutputs++;
            }
        }


        /*for(int i = 0; i < inputs.length; i++) { // Use this for not Irvine
            forward(inputs[i]);
            for(int j = 0; j < outputs[i].length; j++) { // Probably could use some work
                double output = layers[layers.length - 1].getNeurons().get(j).getValue();
                //System.out.println("Actual output: " + output + ", Expected output: " + outputs[i][j]);
                if (Math.abs(output - outputs[i][j]) > acceptableError) {
                    correct = false;
                    break;
                }
            }
            if(correct) {
                correctOutputs++;
            }
        }*/
        System.out.println(" correct outputs: " + correctOutputs + "/" + outputs.length);
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
