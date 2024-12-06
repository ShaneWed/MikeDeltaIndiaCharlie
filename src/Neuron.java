public class Neuron {
    double value;
    double bias;
    double[] weights;
    double delta;

    public Neuron(int noOfWeights) {
        value = 0;
        bias = 0;
        // This represents the weights of neurons in the previous layer, which will determine the value of this neuron
        weights = new double[noOfWeights];
        delta = 0;

    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
    public void setRandomWeights() {
        for(int i = 0; i < weights.length; i++) {
            weights[i] = Math.random();
        }
    }
    public void setValue(double value) {
        this.value = value;
    }
    public void setBias(double bias) {
        this.bias = bias;
    }
    public double getValue() {
        return value;
    }
    public double getBias() {
        return bias;
    }
    public double[] getWeights() {
        return weights;
    }
    public void updateWeights(int weightIndex, double delta) {
        weights[weightIndex] *= delta;
    }
}
