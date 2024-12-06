public class MultiLayeredPerceptron {
    final int numOfInputs = 2;
    final int numOfOutputs = 1;
    final int hiddenUnitsPerLayer = 4;
    double learningRate;

    double[] lowerWeights = new double[numOfInputs];
    double[] upperWeights = new double[hiddenUnitsPerLayer];

    Layer[] layers;

    // This will have an input layer, one hidden layer, and an output layer
    // Try to implement variable hidden layers eventually
    public MultiLayeredPerceptron(double learningRate) {
        this.learningRate = learningRate;
        TrainingData data = new TrainingData();

        // can probably just leave previousWeights as nothing since randomise changes it
        Layer lowerLayer = new Layer(0, numOfInputs, 0); // input
        Layer upperLayer = new Layer(1, hiddenUnitsPerLayer, numOfInputs); // hidden
        Layer outputLayer = new Layer(2, numOfOutputs, hiddenUnitsPerLayer); // output
        layers = new Layer[] {lowerLayer, upperLayer, outputLayer};

        /*forwardPasses(data.xorInputData);
        backwards(data.xorInputData, data.xorOutputData, learningRate);
        forwardPasses(data.xorInputData);
        forwardPassesWithBackwards(data.xorInputData, data.xorOutputData, learningRate);*/

        randomise();
        System.out.println(this);
        for(int i = 0; i < 10000; i++) {
            forwardPassesWithBackwards(data.xorInputData, data.xorOutputData, learningRate);
        }

        System.out.println(this);
    }

    public void randomise() {
        // Maybe make it so input doesn't get set weights? Or we can just ignore them during the other functions
        for(Layer l : layers) {
            if(l.layerNumber() != 0) {
                for(int i = 0; i < l.previousWeights.length; i++) {
                    l.previousWeights[i] = Math.random();
                }
                for(Neuron n : l.getNeurons()) {
                    n.setRandomWeights();
                    n.setBias(Math.random());
                }
            }
        }
    }

    public double backwards(double[][] inputs, double[] t, double learningRate) {
        // Backpropagation!
        // TODO fix calculations so weights are calculated properly
        double error = 0;
        double newDelta = 0;
        for(int i = layers.length - 1; i >= 0; i--) { // Will be lovely to debug
            if(i == layers.length - 1) { // Output layer
                for(int j = 0; j < layers[i].getNeurons().size(); j++) { // Compare these to expected outputs
                    error = t[j] - layers[i].getNeurons().get(j).getValue();
                    newDelta = error * Sigmoid.sigmoidDerivative(layers[i].getNeurons().get(j).getValue());

                    for (int k = 0; k < layers[i].getNeurons().get(j).getWeights().length; k++) {
                        double weightDelta = newDelta * layers[i - 1].getNeurons().get(k).getValue() * learningRate;
                        layers[i].getNeurons().get(j).updateWeights(k, weightDelta);
                    }
                }
            } else if(i > 0) { // Not output layer
                for(int j = 0; j < layers[i].getNeurons().size(); j++) {
                    newDelta = 0; // Reset delta for the current neuron
                    for (int k = 0; k < layers[i + 1].getNeurons().size(); k++) {
                        newDelta += layers[i + 1].getNeurons().get(k).getWeights()[j] * layers[i + 1].getNeurons().get(k).getDelta();
                    }
                    newDelta *= Sigmoid.sigmoidDerivative(layers[i].getNeurons().get(j).getValue());

                    for (int k = 0; k < layers[i].getNeurons().get(j).getWeights().length; k++) {
                        double weightDelta = newDelta * layers[i - 1].getNeurons().get(k).getValue() * learningRate;
                        layers[i].getNeurons().get(j).updateWeights(k, weightDelta);
                    }
                }
            }
        }
        return error;
    }

    public void forward(double[] I) {
        // Forward pass which will produce output stored into double[] outputs
        // I think it works! Yeah, it probably didn't

        // I believe I have cooked
        for(int i = 0; i < I.length; i++) {
            layers[0].getNeurons().get(i).setValue(I[i]);
        }

        double weightSum = 0;
        for(Layer l : layers) {
            if(l.layerNumber() != 0) {
                for(Neuron n : l.getNeurons()) {
                    for(int i = 0; i < layers[l.layerNumber() - 1].getNeurons().size(); i++) {
                        weightSum += layers[l.layerNumber() - 1].getNeurons().get(i).getValue() * layers[l.layerNumber() - 1].getNeurons().get(i).getWeights()[i] + n.getBias();
                    }
                    n.setValue(Sigmoid.calculateY(weightSum));
                    weightSum = 0;
                }
            }
        }
    }

    public void forwardPasses(double[][] I) {
        //System.out.println("Performing forward passes...");
        for(double[] j : I) { // Should probably make a method for this
            forward(j);
        }
    }

    public void forwardPassesWithBackwards(double[][] I, double[] O, double learningRate) {
        System.out.println("\nPerforming forward passes and then backpropagation");
        forwardPasses(I);
        System.out.println("Backpropagation error: " + backwards(I, O, learningRate));
        forwardPasses(I);
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
