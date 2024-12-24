public class Tanh {
    // Use tanh for sin calculations because its range is -1 to 1
    public static double calculateTanh(double z) {
        return Math.tanh(z);
    }

    // Tanh derivative?
    public static double tanhDerivative(double z) {
        return (1 - Math.pow(calculateTanh(z), 2));
    }
}
