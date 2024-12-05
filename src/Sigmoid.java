public class Sigmoid {
    // Sigmoid is needed to "squash" values to between 0 and 1
    public static double calculateY(double z) { // Should this return an int or double? Ideally it will always be 0 or 1
        return  1 / (1 + Math.pow(Math.E, -z));
    }

    // Need to implement for gradient descent
    public double sigmoidDerivative(double z) {
        return 0.0;
    }
}
