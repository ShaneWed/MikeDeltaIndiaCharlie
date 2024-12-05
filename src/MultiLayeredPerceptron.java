public class MultiLayeredPerceptron {
    final int numOfInputs = 2;
    final int numOfOutputs = 1;
    final int hiddenUnitsPerLayer = 4;

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
        lowerWeights = new double[numOfInputs];
        upperWeights = new double[numOfInputs];
        lowerDeltas = new double[numOfInputs];
        upperDeltas = new double[numOfInputs];

        Layer lowerLayer = new Layer(0, 2); // input
        Layer upperLayer = new Layer(1, 4); // hidden
        Layer output = new Layer(2, 1); // output
        layers = new Layer[] {lowerLayer, upperLayer, output};

        randomise();
    }

    public void generateNeurons(Layer[] layers) {
        // Unnecessary? Layers generate their own neurons

    }

    public void randomise() {
        // Will have to change this if number of layers changes
        for(int i = 0; i < lowerWeights.length; i++) {
            lowerWeights[i] = Math.random();
            lowerDeltas[i] = 0;
        }
        for (int i = 0; i < upperWeights.length; i++) {
            upperWeights[i] = Math.random();
            upperDeltas[i] = 0;
        }
    }

    public void forward(double[] I) {

    }
}
