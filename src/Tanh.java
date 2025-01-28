public class Tanh extends ActivationFunction {
    // Has replaced the Sigmoid function in all uses
    // Use tanh for sin calculations because its range is -1 to 1
    public double calculate(double z) {
        return Math.tanh(z);
    }

    public double calculateDerivative(double z) {
        return (1 - Math.pow(calculate(z), 2));
    }

    public String toString() {
        return "Tanh";
    }
}
