import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlanetLoader {

    /**
     * Finds the first entry for each planet in a given folder
     * @param folder A folder that contains one or more JPL Epheremis files
     * @return A list of data that can be used to generate planets
     */
    public static List<String[]> getInitPlanetData(File folder){
        List<String[]> initPlanetData = new ArrayList<String[]>();

        for (List<String[]> planetData : getAllPlanetData(folder)){
            initPlanetData.add(planetData.get(0));
        }

        return initPlanetData;
    }

    /**
     * Finds all of the entries for each planet in a given folder
     * @param folder A folder that contains one or more JPL Epheremis files
     * @return A list of lists of dataentrys, that can be used to lookup, offsets from the simulation
     */
    public static List<List<String[]>> getAllPlanetData(File folder){
        List<List<String>> planetData = new ArrayList<List<String>>();
        List<List<String[]>> planetValues = new ArrayList<List<String[]>>();

        try{
            for (File file : folder.listFiles()) {
                Scanner in = new Scanner(file);
                List<String> tmpdata= new ArrayList<String>();
                while (in.hasNextLine()){
                    tmpdata.add(file.getName().replaceAll(".txt","") + ", " + in.nextLine());
                }
                planetData.add(tmpdata);
                in.close();
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }



        for (List<String> data : planetData){
            List<String[]> tmpentry = new ArrayList<String[]>();
            for (String entry : data){
                tmpentry.add(entry.replaceAll("\\s", "").split(","));
            }
            planetValues.add(tmpentry);
        }

        return planetValues;
    }

    /**
     * Makes a planet obejct from a a data entry from JPL Epheremis
     * @param values Values from JPL Epheremis
     * @return A planet, with the data from the given values
     */
    public static Planet makePlanet(String[] values){

        String name = values[0];
        double time = Double.parseDouble(values[1]);

        double posX = Double.parseDouble(values[3]);
        double posY = Double.parseDouble(values[4]);
        double posZ = Double.parseDouble(values[5]);

        Vector3D pos = new Vector3D(posX,posY,posZ);

        double speedX = Double.parseDouble(values[6]);
        double speedY = Double.parseDouble(values[7]);
        double speedZ = Double.parseDouble(values[8]);


        Vector3D speed = new Vector3D(speedX,speedY,speedZ);

        return new Planet(name, time, pos, speed);

    }

    /**
     * Generates a list of planets from a list of initial entrys from one or more JPL Epheremis files
     * @param PlanetData a list of initial entrys from one or more JPL Epheremis files
     * @return A list of planets
     */
    public static List<Planet> makePlanets(List<String[]> PlanetData){
        List<Planet> planets = new ArrayList<Planet>();
        for (String[] values : PlanetData){
            planets.add(makePlanet(values));
        }
        return planets;
    }

    /**
     * Generates a list of planets from a folder which contains atleast one JPL Epheremis file
     * @param folder A folder that contains one or more JPL Epheremis files
     * @return A list of planets generated from the data entries in the folder
     */
    public static List<Planet> getPlanets(File folder){
        return makePlanets(getInitPlanetData(folder));
    }


}
