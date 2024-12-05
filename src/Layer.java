import java.util.ArrayList;

public class Layer {
    ArrayList<Neuron> neurons = new ArrayList<Neuron>();
    int layerNumber;

    double[] previousWeights;

    public Layer(int layerNumber, int noOfNeurons, double[] previousWeights) {
        for(int i = 0; i < noOfNeurons; i++) { // Generate neurons, crazy stuff
            Neuron neuron = new Neuron(i);
            neuron.weights = previousWeights;
            neurons.add(neuron);

        }
    }


}
