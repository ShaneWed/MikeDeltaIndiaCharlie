import java.util.ArrayList;
import java.util.Arrays;

public class Layer {
    ArrayList<Neuron> neurons = new ArrayList<>();
    int layerNumber;
    double[] previousWeights;

    public Layer(int layerNumber, int noOfNeurons, double[] previousWeights) {
        this.previousWeights = previousWeights;
        this.layerNumber = layerNumber;

        for(int i = 0; i < noOfNeurons; i++) { // Generate neurons, crazy stuff
            Neuron neuron = new Neuron(i);
            neuron.setWeights(previousWeights);
            neurons.add(neuron);

            // For debugging
            System.out.println("Neuron " + i + " added to layer " + layerNumber + " with weights " + Arrays.toString(previousWeights));
        }
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void updateNeurons(double[] previousWeights) {
        // This should be called whenever the weights change
        for(Neuron neuron : neurons) {
            neuron.setWeights(previousWeights);
        }
    }
}
