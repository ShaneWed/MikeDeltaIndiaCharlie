import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MultiLayerPerceptron MikeDeltaIndiaCharlie = new MultiLayerPerceptron(4, 5, 1, 0.2);
        MultiLayerPerceptron Darius = new MultiLayerPerceptron(4, 5,1, 0.6);

        MikeDeltaIndiaCharlie.executeXorFunction();
        Darius.executeSinFunction();

        System.out.println("Multi layer perceptron created!\n");
    }
}
