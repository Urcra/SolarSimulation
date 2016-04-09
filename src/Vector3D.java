public class Vector3D {
    public double[] val;

    /**
     * Creates a 3 dimensional vector
     * @param x the x value of the vector
     * @param y the y value of the vector
     * @param z the z value of the vector
     */
    public Vector3D(double x, double y, double z){
        val = new double[]{x, y, z};
    }

    /**
     * Calculates the resulting vector of adding another vector to this
     * @param v The vector which is going to be added
     * @return A new vector which is the result of adding the given vector to this vector
     */
    public Vector3D add(Vector3D v){
        Vector3D tmp = new Vector3D(0,0,0);

        for (int i = 0; i < 3; i++) {
            tmp.val[i] = val[i] + v.val[i];
        }
        return tmp;
    }
    /**
     * Calculates the resulting vector of subtracting another vector from this
     * @param v The vector which is going to be subtracted
     * @return A new vector which is the result of subtracting the given vector from this vector
     */
    public Vector3D subtract(Vector3D v){
        Vector3D tmp = new Vector3D(0,0,0);

        for (int i = 0; i < 3; i++) {
            tmp.val[i] = val[i] - v.val[i];
        }
        return tmp;
    }

    /**
     * Calculates the resulting vector, from multiplying each value in this vector with a number
     * @param k The number that is multiplied with the vector
     * @return A new vector which is the result of multiplying a number with this vector
     */
    public Vector3D mult(double k){
        Vector3D tmp = new Vector3D(0,0,0);

        for (int i = 0; i < 3; i++) {
            tmp.val[i] = val[i] * k;
        }
        return tmp;
    }

    /**
     * Calculates the eucledian distance between <0,0,0> and the vector
     * @return eucledian distance between <0,0,0> and the vector
     */
    public double length(){
        return Math.sqrt(Math.pow(val[0], 2) + Math.pow(val[1], 2) + Math.pow(val[2], 2));
    }

    /**
     * Calculates the eucledian distance between a vector and this vector
     * @param v The vector of which the distance is calculated to
     * @return eucledian distance between a vector and this vector
     */
    public double distance(Vector3D v){
        return subtract(v).length();
    }

    /**
     * Formats info about the vector into a string
     * @return Information about the vector
     */
    public String toString(){
        return "" + val[0] + ", " + val[1] + ", " + val[2];
    }


}
