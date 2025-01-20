import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTests {
    @Test
    public void testXor() throws IOException { // Make this actually assert failure eventually, for now just makes sure it runs for all examples
        TrainingData trainingData = new TrainingData();
        MultiLayerPerceptron xor = new MultiLayerPerceptron(2, 5, 1, 0.01, new Tanh());
        xor.executeXorFunction(1000);
        xor.testOutputs(trainingData.xorInputData, trainingData.xorOutputData);
    }

    @Test
    public void testSin() throws IOException { // This one is busted because of TrainingData constructors
        TrainingData trainingData = new TrainingData();
        MultiLayerPerceptron sin = new MultiLayerPerceptron(4, 5, 1, 0.01, new Tanh());
        sin.executeSinFunction(1000);
        sin.testOutputs(trainingData.testingVectors, trainingData.testingVectorOutputs);
    }

    @Test
    public void testIrvine() throws IOException {
        TrainingData trainingData = new TrainingData();
        MultiLayerPerceptron irvine = new MultiLayerPerceptron(16, 10, 26, 0.01, new Sigmoid());
        irvine.exerciseIrvine(1000);
        irvine.testOutputs(trainingData.irvineTestingInput, trainingData.irvineTestingOutput);
    }
}
