import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class TrainingData {
    // I don't like how most of this data is hardcoded, look to fix it at some point
    public double[][] xorInputData = new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    public double[] xorOutputData = new double[]{0, 1, 1, 0};

    // Random training and testing vectors, should implement these better
    Random rand = new Random();
    double[][] trainingVectors;
    double[] trainingVectorOutputs;
    double[][] testingVectors;
    double[] testingVectorOutputs;

    public void generateTrainingVectors(int noOfInputs, int noOfVectors) {
        trainingVectors = new double[noOfVectors][noOfInputs];
        trainingVectorOutputs = new double[noOfVectors];
        for(int i = 0; i < noOfVectors; i++) {
            for(int j = 0; j < noOfInputs; j++) {
                trainingVectors[i][j] = rand.nextDouble(-1, 1);
            }
        }
        for(int i = 0; i < noOfVectors; i++) {
            double combination = trainingVectors[i][0] - trainingVectors[i][1] + trainingVectors[i][2] - trainingVectors[i][3];
            trainingVectorOutputs[i] = Math.sin(combination);
        }
    }
    // This is me being lazy because I didn't want to have to figure out how to split the array. It works so I don't really care!
    // I actually really do care now but thinking about fixing it gives me more of a headache than the knowledge that it exists.
    public void generateTestingVectors(int noOfInputs, int noOfVectors) {
        testingVectors = new double[noOfVectors][noOfInputs];
        testingVectorOutputs = new double[noOfVectors];
        for(int i = 0; i < noOfVectors; i++) {
            for(int j = 0; j < noOfInputs; j++) {
                testingVectors[i][j] = rand.nextDouble(-1, 1);
            }
        }
        for(int i = 0; i < noOfVectors; i++) {
            double combination = testingVectors[i][0] - testingVectors[i][1] + testingVectors[i][2] - testingVectors[i][3];
            testingVectorOutputs[i] = Math.sin(combination);
        }
    }

    private void readIrvineLetters() throws IOException {
        trainingVectors = new double[16000][16];
        trainingVectorOutputs = new double[16000];
        String file = "IrvineLetters.txt"; // 1st character is expected output, then comma separated sixteen inputs
        try(FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] line = bufferedReader.readLine().split(",");

            for(int i = 0; i < 16000; i++) { // Char as double?? In Java?? I don't think this is legal or functional
                trainingVectorOutputs[i] = convertCharToDouble(line[0].charAt(0));
                for(int j = 1; j < line.length; j++) {
                    trainingVectors[i][j - 1] = Double.parseDouble(line[j]);
                }

                line = bufferedReader.readLine().split(",");
            }
        } catch (IOException e) {
            System.out.println("Error reading " + file + ", fix this now!");
        }
    }
    private double convertCharToDouble(char input) {
        double output = 0;
        switch(input) {
            case 'A': output = 1; break;
            case 'B': output = 2; break;
            case 'C': output = 3; break;
            case 'D': output = 4; break;
            case 'E': output = 5; break;
            case 'F': output = 6; break;
            case 'G': output = 7; break;
            case 'H': output = 8; break;
            case 'I': output = 9; break;
            case 'J': output = 10; break;
            case 'K': output = 11; break;
            case 'L': output = 12; break;
            case 'M': output = 13; break;
            case 'N': output = 14; break;
            case 'O': output = 15; break;
            case 'P': output = 16; break;
            case 'Q': output = 17; break;
            case 'R': output = 18; break;
            case 'S': output = 19; break;
            case 'T': output = 20; break;
            case 'U': output = 21; break;
            case 'V': output = 22; break;
            case 'W': output = 23; break;
            case 'X': output = 24; break;
            case 'Y': output = 25; break;
            case 'Z': output = 26; break;
        }
        return output;
    }

    public TrainingData(int noOfInputs, int noOfVectors) {
        generateTrainingVectors(noOfInputs, noOfVectors - 100);
        generateTestingVectors(noOfInputs, 100);
    }

    public TrainingData() throws IOException {
        readIrvineLetters();
    }
}
