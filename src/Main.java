public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        int[] inputs = new int[] {0, 1};
        double learningRate = 0.0;

        MultiLayeredPerceptron MikeDeltaIndiaCharlie = new MultiLayeredPerceptron(inputs, learningRate);

        System.out.println("Multi layered perceptron created!");

    }
}
