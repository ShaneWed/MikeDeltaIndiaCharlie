public class Neuron {
    double value;
    double bias;
    double[] weights;
    double delta;

    public Neuron(int noOfWeights) {
        value = 0;
        bias = 0;
        weights = new double[noOfWeights];
        delta = 0;

    }
}
