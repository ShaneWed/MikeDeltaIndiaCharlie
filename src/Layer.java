import java.util.ArrayList;

public class Layer {
    private final ArrayList<Neuron> neurons = new ArrayList<>();
    private final int layerNumber;
    double[] previousWeights;

    public Layer(int layerNumber, int noOfNeurons, int noOfPreviousWeights) {
        this.previousWeights = new double[noOfPreviousWeights];
        this.layerNumber = layerNumber;

        for(int i = 0; i < noOfNeurons; i++) { // Generate neurons, crazy stuff
            Neuron neuron = new Neuron(noOfPreviousWeights);
            neurons.add(neuron);
        }
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public int layerNumber() {
        return layerNumber;
    }

}
