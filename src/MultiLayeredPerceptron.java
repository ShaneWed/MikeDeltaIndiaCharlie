public class MultiLayeredPerceptron {
    final int numOfInputs = 2;
    final int numOfOutputs = 1;
    final int hiddenUnitsPerLayer = 4;
    double learningRate;

    double[] lowerWeights = new double[numOfInputs];
    double[] lowerDeltas = new double[numOfInputs];
    double[] lowerActivations = new double[numOfInputs];
    double[] upperWeights = new double[hiddenUnitsPerLayer];
    double[] upperDeltas = new double[hiddenUnitsPerLayer];
    double[] upperActivations = new double[hiddenUnitsPerLayer];
    double[] outputs = new double[numOfOutputs];

    Layer[] layers;

    // This will have an input layer, one hidden layer, and an output layer
    // Try to implement variable hidden layers eventually
    public MultiLayeredPerceptron(double learningRate) {
        this.learningRate = learningRate;
        TrainingData data = new TrainingData();

        // can probably just leave previousWeights as nothing since randomise changes it
        Layer lowerLayer = new Layer(0, numOfInputs, null); // input
        Layer upperLayer = new Layer(1, hiddenUnitsPerLayer, lowerWeights); // hidden
        Layer outputLayer = new Layer(2, numOfOutputs, upperWeights); // output
        layers = new Layer[] {lowerLayer, upperLayer, outputLayer};

        /*forwardPasses(data.xorInputData);
        backwards(data.xorInputData, data.xorOutputData, learningRate);
        forwardPasses(data.xorInputData);
        forwardPassesWithBackwards(data.xorInputData, data.xorOutputData, learningRate);*/

        randomise();
        System.out.println(this);
        for(int i = 0; i < 100; i++) {
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
        double newDelta = 0;
        double error = 0;
        for(int i = 0; i < numOfInputs; i++) {
            double[] input = inputs[i];
            for (double v : t) { // Loop for every example, IntelliJ changed loop
                for (int j = 0; j < numOfOutputs; j++) {
                    newDelta = (v - outputs[j]) * Sigmoid.sigmoidDerivative(outputs[j]);

                    for(int k = 0; k < upperWeights.length; k++) {
                        upperDeltas[k] = Sigmoid.sigmoidDerivative(upperActivations[k]) * upperWeights[k] * newDelta;
                    }

                    for(int k = 0; k < upperWeights.length; k++) {
                        upperWeights[k] += learningRate * newDelta * upperActivations[k];
                    }

                    for(int k = 0; k < lowerWeights.length; k++) {
                        lowerWeights[k] += learningRate * upperDeltas[k] * input[j];
                    }
                }
            }
            error += Math.pow(t[i] - outputs[0], 2);
        }

        return error; // TODO change this to actually return the error of the example
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
                    for(Neuron n2 : layers[l.layerNumber() - 1].getNeurons()) {
                        for(double d : n.getWeights()) {
                            weightSum += d * n2.getValue() + n2.getBias();
                        }
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
