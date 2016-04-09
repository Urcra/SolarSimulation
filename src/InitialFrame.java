import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class InitialFrame extends JFrame {
    private File datafolder;
    private JTextField creation = new JTextField("Start time");
    private JTextField timestep;
    private JButton getFile;
    private JLabel path;
    private JTextField runtime;
    private JLabel timestepLabel;
    private JLabel creationLabel;
    private JLabel runTimeLabel;
    private JButton launch;

    public final static int FRAME_HEIGHT = 300;
    public final static int FRAME_WIDTH = 400;

    /**
     * Create a window, that gets relevant information from the user about how he wants to run the simulation
     */
    public InitialFrame(){
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        setLayout(new GridLayout(0,2, 20, 20));

        timestepLabel = new JLabel("Timetep(days): ");
        creationLabel = new JLabel("Start time(EJD): ");
        runTimeLabel = new JLabel("Running time(days): ");
        timestep = new JTextField("Timestep");
        runtime = new JTextField("Runtime");
        path = new JLabel("No folder selected");
        getFile = new JButton("Choose file");
        launch = new JButton("Launch simulation");

        getFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    datafolder = fileChooser.getSelectedFile();
                    path.setText("Path: " + fileChooser.getSelectedFile().getPath());
                }
            }
        });

        launch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double cTime = Double.parseDouble(creation.getText());
                List<Planet> planets = PlanetLoader.getPlanets(datafolder);
                Universe universe = new Universe(planets, cTime, PlanetLoader.getAllPlanetData(datafolder));
                double ts = Double.parseDouble(timestep.getText());
                double stopTime = Double.parseDouble(runtime.getText()) + cTime;

                SimulationFrame sim = new SimulationFrame(universe, ts, stopTime);
                sim.setTitle("Simulation of " + datafolder.getPath());
                sim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                sim.setLocationRelativeTo(null);
                sim.setVisible(true);

                TableFrame table = new TableFrame(universe);
                table.setTitle("Comparison table for " + datafolder.getPath());
                table.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                table.setLocationRelativeTo(sim);
                table.setVisible(true);

                dispose();
            }
        });

        add(getFile);
        add(path);
        add(creationLabel);
        add(creation);
        add(runTimeLabel);
        add(runtime);
        add(timestepLabel);
        add(timestep);
        add(launch);

    }


}
