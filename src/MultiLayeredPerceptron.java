public class MultiLayeredPerceptron {
    final int numOfInputs = 2;
    final int numOfOutputs = 1;
    final int hiddenUnitsPerLayer = 4;

    double[] dummyWeights;
    double[] lowerWeights; // Might have to be 2D, one for layer # and other for weight value
    double[] upperWeights;
    double[] outputs;

    double[] lowerDeltas;
    double[] upperDeltas;

    double[] lowerActivations;
    double[] upperActivations;

    Layer[] layers;

    // This will have an input layer, one hidden layer, and an output layer
    // Try to implement variable hidden layers eventually
    public MultiLayeredPerceptron(int[] inputs, double learningRate) {
        TrainingData data = new TrainingData();

        // Can probably move all of this outside of constructor, I assume code quality shouldn't matter as long as it works
        // TODO Yeah this is absolutely silly having all this in the constructor
        dummyWeights = new double[numOfInputs];
        lowerWeights = new double[numOfInputs];
        lowerDeltas = new double[numOfInputs];
        lowerActivations = new double[numOfInputs];
        upperWeights = new double[hiddenUnitsPerLayer];
        upperDeltas = new double[hiddenUnitsPerLayer];
        upperActivations = new double[hiddenUnitsPerLayer];
        outputs = new double[numOfOutputs];

        randomise(); // For funnies

        Layer lowerLayer = new Layer(0, numOfInputs, dummyWeights); // input
        Layer upperLayer = new Layer(1, hiddenUnitsPerLayer, lowerWeights); // hidden
        Layer output = new Layer(2, numOfOutputs, upperWeights); // output
        layers = new Layer[] {lowerLayer, upperLayer, output};

        forward(data.xorOutputData);
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

    public void forward(double[] I) {
        // Forward pass which will produce output stored into double[] outputs
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
        for(int i = 0; i < numOfInputs; i++) {
            System.out.println("Output " + i + ": " + outputs[i]);
        }
    }
}
