import java.util.Arrays;
import java.util.Random;

public class Neuron {
    double value;
    double bias;
    double delta;
    double preActivation;
    double[] weights;

    public Neuron(int noOfWeights) {
        value = 0;
        bias = 0;
        delta = 0;
        preActivation = 0;
        weights = new double[noOfWeights];
    }

    public void setRandomWeights(Random rand) {
        for(int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble(-1, 1);
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
    public int getRoundedValue() {
        return (int)Math.round(value);
    }
    public double getBias() {
        return bias;
    }
    public double[] getWeights() {
        return weights;
    }
    public void setDelta(double delta) {
        this.delta = delta;
    }
    public double getDelta() {
        return delta;
    }
    public double getPreActivation() {
        return preActivation;
    }
    public void setPreActivation(double preActivation) {
        this.preActivation = preActivation;
    }
    public void updateWeights(double delta, Layer previousLayer, double learningRate) {
        this.setDelta(delta);
        for(int i = 0; i < previousLayer.getNeurons().size(); i++) {
            this.updateWeightsHelper(i, learningRate * delta * previousLayer.getNeurons().get(i).getValue());
        }
        this.setBias(this.getBias() + learningRate * delta);
    }
    private void updateWeightsHelper(int weightIndex, double delta) {
        weights[weightIndex] += delta;
    }

    @Override
    public String toString() {
        return "Value: " + getValue() + ", Bias: " + getBias() + ", Weights: " + Arrays.toString(weights);
     }
}
