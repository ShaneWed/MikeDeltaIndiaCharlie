public class Sigmoid {
    // Sigmoid is needed to "squash" values to between 0 and 1
    public static double calculateY(double z) { // Should this return an int or double? Ideally it will always be 0 or 1
        return Tanh.calculateTanh(z);
        //return  1 / (1 + Math.exp(-z));
    }

    // Need to implement for gradient descent
    public static double sigmoidDerivative(double z) {
        return Tanh.tanhDerivative(z);
        //return calculateY(z) * (1 - calculateY(z)); // Changed z to go through calculateY
    }
}
