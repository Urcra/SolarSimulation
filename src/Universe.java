import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class Universe extends JComponent{
    private double currentTime;
    private List<Planet> planets;
    private List<List<String[]>> planetData;
    private double scale = 10;
    public List<String[]> universeTable = new ArrayList<String[]>();


    /**
     * Creates a new universe with a list of plantes, which can be painted to a canvas
     * @param planetList A list of planet objects
     * @param creationTime The time that the simulation of the universe starts at
     * @param pd a list of lists of entrys, from JPL Epheremis
     */
    public Universe(List<Planet> planetList, double creationTime, List<List<String[]>> pd){
        currentTime = creationTime;
        planets = planetList;
        planetData = pd;

    }

    /**
     * Updates all of the planets in the universe
     * @param timeStep The timestep used in the calculation for the velocity and position
     */
    public void tick(double timeStep){
        currentTime += timeStep;
        for (Planet p : planets){
            updateTable(p, timeStep);
            p.update(currentTime);
        }
    }

    /**
     * Redraws all of the planets to the canvas
     */
    public void draw(){
        repaint();
    }

    /**
     * The way which the universere paints the planets to a canvas
     * @param g The graphic object thats used for adding the planets
     */
    public void paintComponent(Graphics g){
        Graphics2D brush = (Graphics2D) g;

        // Drawing the sun
        brush.setColor(Color.ORANGE);
        brush.fill(new Ellipse2D.Double(
                getWidth()/2
                , getHeight()/2
                , 15, 15));

        brush.setColor(Color.black);


        for (Planet p : planets){
            // Drawing each planet
            brush.fill(new Ellipse2D.Double(
                    (int) (p.getPos().val[0] * scale) + getWidth() / 2,
                    (int) (p.getPos().val[1] * scale)+ getHeight() / 2,
                    p.getRadius() / 2, p.getRadius() / 2));
            // Adding the name for the planet
            brush.drawString(p.getName(),
                    (int) (p.getPos().val[0] * scale) + getWidth() / 2,
                    (int) (p.getPos().val[1] * scale)+ getHeight() / 2);
        }

    }

    /**
     * Change the zoomlevel of the painting
     * @param amount The amount of which the zoomlevel is changed
     */
    public void changeScale(double amount){
        if (scale + amount < 1){
            scale *= 0.5;
        } else if (scale > 1){
            scale += amount;
        } else {
            scale *= 2;
        }

    }

    /**
     *
     * @return The current time for the universe
     */
    public double getTime(){
        return currentTime;
    }


    /**
     * Updates the table that stores the offset data between the simulation and the JPL Epheremis data
     * @param p A planet
     * @param ts The timestep that the planet has just been updated with
     */
    private void updateTable(Planet p, double ts){
        double eps = ts + 0.00000001;
        for (List<String[]> planetTimes : planetData){
            if (p.getName().equals(planetTimes.get(0)[0])){
                for (String[] planetTime : planetTimes){
                    if (Math.abs(Double.parseDouble(planetTime[1]) - currentTime) < eps){
                        Vector3D tmpvector = new Vector3D(
                                Double.parseDouble(planetTime[3]),
                                Double.parseDouble(planetTime[4]),
                                Double.parseDouble(planetTime[5]));

                        String errVal = Double.toString(p.getPos().distance(tmpvector));
                        universeTable.add(new String[]{planetTime[0], planetTime[2], errVal});
                    }
                }
            }
        }
    }


}
