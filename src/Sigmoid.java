// Sigmoid function has been deprecated in favor of the Tanh function, would
// like to implement functionality that allows switching between the two or more functions,
// however I may wait a while to do add this feature.
public class Sigmoid {
    // Sigmoid is needed to "squash" values to between 0 and 1
    public static double calculateSigmoid(double z) { // Should this return an int or double? Ideally it will always be 0 or 1
        return  1 / (1 + Math.exp(-z));
    }

    // Need to implement for gradient descent
    public static double sigmoidDerivative(double z) {
        return Tanh.tanhDerivative(z);
        //return calculateY(z) * (1 - calculateY(z)); // Changed z to go through calculateY
    }
}
