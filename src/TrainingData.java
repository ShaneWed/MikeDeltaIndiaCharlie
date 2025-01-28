import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class TrainingData {
    // I don't like how most of this data is hardcoded, look to fix it at some point
    public double[][] xorInputData = new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    public double[][] xorOutputData = new double[][]{{0}, {1}, {1}, {0}};

    // Random training and testing vectors, should implement these better
    Random rand = new Random();
    double[][] trainingVectors;
    double[][] trainingVectorOutputs;
    double[][] testingVectors;
    double[][] testingVectorOutputs;

    // Irvine data
    double[][] irvineTrainingInput;
    double[][] irvineTrainingOutput;
    double[][] irvineTestingInput;
    double[][] irvineTestingOutput;

    // These generate functions are both currently hard coded to the sin function and one output
    public void generateTrainingVectors(int noOfInputs, int noOfOutputs, int noOfVectors) {
        trainingVectors = new double[noOfVectors][noOfInputs];
        trainingVectorOutputs = new double[noOfVectors][noOfOutputs];
        for(int i = 0; i < noOfVectors; i++) {
            for(int j = 0; j < noOfInputs; j++) {
                trainingVectors[i][j] = rand.nextDouble(-1, 1);
            }
        }
        for(int i = 0; i < noOfVectors; i++) {
            double combination = trainingVectors[i][0] - trainingVectors[i][1] + trainingVectors[i][2] - trainingVectors[i][3];
            trainingVectorOutputs[i][noOfOutputs - 1] = Math.sin(combination);
        }
    }
    // This is me being lazy because I didn't want to have to figure out how to split the array. It works so I don't really care!
    // I actually really do care now but thinking about fixing it gives me more of a headache than the knowledge that it exists.
    public void generateTestingVectors(int noOfInputs, int noOfOutputs, int noOfVectors) {
        testingVectors = new double[noOfVectors][noOfInputs];
        testingVectorOutputs = new double[noOfVectors][noOfOutputs];
        for(int i = 0; i < noOfVectors; i++) {
            for(int j = 0; j < noOfInputs; j++) {
                testingVectors[i][j] = rand.nextDouble(-1, 1);
            }
        }
        for(int i = 0; i < noOfVectors; i++) {
            double combination = testingVectors[i][0] - testingVectors[i][1] + testingVectors[i][2] - testingVectors[i][3];
            testingVectorOutputs[i][noOfOutputs - 1] = Math.sin(combination);
        }
    }

    private void readIrvineLetters() {
        irvineTrainingInput = new double[16000][16];
        irvineTrainingOutput = new double[16000][26];
        irvineTestingInput = new double[4000][16];
        irvineTestingOutput = new double[4000][26];
        String file = "IrvineLetters.txt"; // 1st character is expected output, then comma separated sixteen inputs
        try(FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] line = bufferedReader.readLine().split(",");
            for(int i = 0; i < 16000; i++) { // Char as double?? In Java?? I don't think this is legal or functional; It is in fact functional
                line = extractIntsFromFileString(bufferedReader, line, i, irvineTrainingOutput, irvineTrainingInput);
            }
            for(int i = 0; i < 3999; i++) {
                line = extractIntsFromFileString(bufferedReader, line, i, irvineTestingOutput, irvineTestingInput);
            }
        } catch (IOException e) {
            System.out.println("Error reading " + file + ", fix this now!");
        }
    }

    private String[] extractIntsFromFileString(BufferedReader bufferedReader, String[] line, int i, double[][] irvineTestingOutput, double[][] irvineTestingInput) throws IOException {
        int index;
        index = convertCharToInt(line[0].charAt(0));
        for(int j = 0; j < 26; j++) {
            irvineTestingOutput[i][j] = 0;
        }
        irvineTestingOutput[i][index - 1] = 1;
        for(int j = 1; j < line.length; j++) {
            irvineTestingInput[i][j - 1] = Double.parseDouble(line[j]);
        }

        line = bufferedReader.readLine().split(",");
        return line;
    }

    private int convertCharToInt(char input) {
        return switch (input) {
            case 'A' -> 1;
            case 'B' -> 2;
            case 'C' -> 3;
            case 'D' -> 4;
            case 'E' -> 5;
            case 'F' -> 6;
            case 'G' -> 7;
            case 'H' -> 8;
            case 'I' -> 9;
            case 'J' -> 10;
            case 'K' -> 11;
            case 'L' -> 12;
            case 'M' -> 13;
            case 'N' -> 14;
            case 'O' -> 15;
            case 'P' -> 16;
            case 'Q' -> 17;
            case 'R' -> 18;
            case 'S' -> 19;
            case 'T' -> 20;
            case 'U' -> 21;
            case 'V' -> 22;
            case 'W' -> 23;
            case 'X' -> 24;
            case 'Y' -> 25;
            case 'Z' -> 26;
            default -> 0;
        };
    }

    public TrainingData(int noOfInputs, int noOfOutputs, int noOfVectors) {

        generateTrainingVectors(noOfInputs, noOfOutputs, noOfVectors - 100);
        generateTestingVectors(noOfInputs, noOfOutputs, 100);
    }

    public TrainingData() {
        readIrvineLetters();
    }
}
