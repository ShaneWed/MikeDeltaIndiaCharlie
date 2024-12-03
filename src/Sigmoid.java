public class Sigmoid {
    public Sigmoid(double z) {

    }

    public int calculateY(double z) {
        return  (int) (1 / (1 + Math.pow(Math.E, -z)));
    }
}
