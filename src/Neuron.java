import java.util.Arrays;
import java.util.Random;

public class Neuron {
    double value;
    double preActivation;
    double bias;
    double[] weights;
    double delta;

    public Neuron(int noOfWeights) {
        value = 0;
        preActivation = 0;
        bias = 0;
        // This represents the weights of neurons in the previous layer, which will determine the value of this neuron
        weights = new double[noOfWeights];
        delta = 0;

    }

    public void setWeights(double[] weights) {
        this.weights = weights;
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
    public double getBias() {
        return bias;
    }
    public double[] getWeights() {
        return weights;
    }
    public void updateWeights(int weightIndex, double delta) {
        weights[weightIndex] += delta;
        //setDelta(delta);
        //System.out.println(Arrays.toString(weights) + " " + delta);
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

    @Override
    public String toString() {
        return "Value: " + getValue() + ", Bias: " + getBias() + ", Weights: " + Arrays.toString(weights);
     }
}
