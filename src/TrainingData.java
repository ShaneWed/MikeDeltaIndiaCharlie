public class TrainingData {

    public double[][] xorInputData = new double[][]{
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };
    public double[] xorOutputData = new double[]{0, 1, 1, 0};

    public double[][] orInputData = new double[][]{
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
    };
    public double[] orOutputData = new double[]{0, 1, 1, 1};

    public TrainingData() {}
}
