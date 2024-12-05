import java.util.ArrayList;

public class Layer {
    ArrayList<Neuron> neurons = new ArrayList<Neuron>();
    int layerNumber;

    public Layer(int layerNumber, int noOfNeurons) {
        for(int i = 0; i < noOfNeurons; i++) { // Generate neurons, crazy stuff
            neurons.add(new Neuron(i));

        }
    }


}
