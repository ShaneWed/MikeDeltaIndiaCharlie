public class MultiLayeredPerceptron {
    final int numOfInputs = 2;
    final int numOfOutputs = 1;
    final int hiddenUnitsPerLayer = 4;

    double[] dummyWeights = new double[numOfInputs];
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
        TrainingData data = new TrainingData();

        Layer lowerLayer = new Layer(0, numOfInputs, dummyWeights); // input
        Layer upperLayer = new Layer(1, hiddenUnitsPerLayer, lowerWeights); // hidden
        Layer output = new Layer(2, numOfOutputs, upperWeights); // output
        layers = new Layer[] {lowerLayer, upperLayer, output};

        forwardPasses(data.xorInputData);
        backwards(data.xorInputData, data.xorOutputData, learningRate);
        forwardPasses(data.xorInputData);
        forwardPassesWithBackwards(data.xorInputData, data.xorOutputData, learningRate);

        for(int i = 0; i < 100; i++) {
            forwardPassesWithBackwards(data.xorInputData, data.xorOutputData, learningRate);
        }

        randomise();
    }

    public void randomise() {
        // Will have to change this if number of layers changes
        for(int i = 0; i < lowerWeights.length; i++) {
            dummyWeights[i] = 0;
            lowerWeights[i] = Math.random();
            lowerDeltas[i] = 0;
            lowerActivations[i] = 0; // IDK if this should be here or if it's even correct
        }
        for (int i = 0; i < upperWeights.length; i++) {
            upperWeights[i] = Math.random();
            upperDeltas[i] = 0;
            upperActivations[i] = 0;
        }
        for(int i = 0; i < numOfOutputs; i++) {
            outputs[i] = 0;
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
        // I think it works!
        // First set the input layer neurons to the inputs
        lowerActivations = I; // This seems wrong

        // Then actually do some calculations for the activations of the next layer
        // since these will be based off the input layer
        for(int i = 0; i < upperWeights.length; i++) {
            for(int j = 0; j < lowerWeights.length; j++) {
                upperActivations[i] += lowerActivations[j] * lowerWeights[j];
            }
            // TODO How tf am I going to get the bias; We'll add that later
            upperActivations[i] = Sigmoid.calculateY(upperActivations[i]);
        }

        // Output layer
        for(int i = 0; i < numOfOutputs; i++) {
            for(int j = 0; j < upperWeights.length; j++) {
                outputs[i] += upperActivations[j] * upperWeights[j];
            }
            outputs[i] = Sigmoid.calculateY(outputs[i]);
        }

        // Results?
        for(int i = 0; i < numOfOutputs; i++) { // Probably one of the dumbest array mistakes I've made
            System.out.println("Output " + i + ": " + outputs[i]);
        }
    }

    public void forwardPasses(double[][] I) {
        System.out.println("Performing forward passes...");
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
}
