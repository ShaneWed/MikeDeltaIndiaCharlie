public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        double learningRate = 0.5;

        MultiLayeredPerceptron MikeDeltaIndiaCharlie = new MultiLayeredPerceptron(learningRate);

        System.out.println("Multi layered perceptron created!\n");
        System.out.println(MikeDeltaIndiaCharlie); // Because I'm sick of IntelliJ warnings
    }
}
