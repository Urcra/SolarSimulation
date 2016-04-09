public class Planet {

    private final double GM_SUN = 0.000295912208232212;

    private double radius = 20;
    private double time;
    private String name;
    private Vector3D position;
    private Vector3D speed;


    /**
     * Creates a Planet object, that uses the formulas given in the assignment.
     * To update it's position, and velocity
     * @param planet The name of the planet
     * @param creation The time of the first data for the planet
     * @param r The position vector for the planet
     * @param v The velocity vector for the planet
     */
    public Planet(String planet, double creation, Vector3D r, Vector3D v){
        name = planet;
        time = creation;
        position = r;
        speed = v;
    }

    /**
     * Updates the positon and velocity of the planet, to what it would be in the given time.
     * @param newTime The time, that the planet gets updated to
     */
    public void update(double newTime){
        if (newTime < time){
            return;
        }

        double timestep = newTime - time;
        speed = speed.add(newAccel().mult(timestep));
        position = position.add(speed.mult(timestep));
        time = newTime;
    }

    /**
     * Calculates what the acceleration would be for the planet
     * @return The acceleration for the planet
     */
    public Vector3D newAccel(){
        double r_3 = Math.pow(distToStar().length(),3);
        return distToStar().mult(-GM_SUN / r_3);
    }

    // We know that the sun is in the center, so we can just calculate the distance to the center

    /**
     * Calculates the distance to the star, we use in the model.
     * Since we know that the sun is always in <0,0,0>, we calculate the distance to that
     * @return Distance to the star that affects the planet
     */
    public Vector3D distToStar(){
        Vector3D center = new Vector3D(0,0,0);
        return position.subtract(center);
    }

    /**
     *
     * @return Position of the planet
     */
    public Vector3D getPos(){
        return position;
    }

    /**
     *
     * @return Radius of the planet
     */
    public double getRadius(){
        return radius;
    }

    /**
     *
     * @return The name of the planet
     */
    public String getName(){
        return name;
    }

    /**
     * Formats info about the planet into a string
     * @return Information about the planet
     */
    @Override
    public String toString(){
        return name + " current time is " + time + ". And current pos is: " + position;
    }



}
