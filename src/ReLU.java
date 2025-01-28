// Needs work still
public class ReLU extends ActivationFunction {
    public double calculate(double z) {
        return Math.max(0, z);
    }

    public double calculateDerivative(double z) {
        if(z >= 0) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return "ReLU";
    }
}
