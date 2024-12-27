public class Sigmoid extends ActivationFunction{
    // Sigmoid is needed to "squash" values to between 0 and 1, not suitable for sin as range of sin is -1 to 1
    public double calculate(double z) {
        return  1 / (1 + Math.exp(-z));
    }

    // Need to implement for gradient descent
    public double calculateDerivative(double z) {
        return calculate(z) * (1 - calculate(z));
    }
}
