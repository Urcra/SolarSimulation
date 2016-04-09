import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class SimulationFrame extends JFrame{

    private Universe universe;
    private Timer tickTimer;
    private Timer drawTimer;
    private double timeStep;
    private double stopTime;

    private int tickRate = -1;
    private int drawRate = 17;


    public final static int FRAME_HEIGHT = 800;
    public final static int FRAME_WIDTH = 1200;

    /**
     * Create a new window, which updates the universe, shows the updates live in the window
     * @param u The universe that the window simulates
     * @param ts The timestep that is going to be used in the formulas for the planets
     * @param stop How long the simulation needs to run
     */
    public SimulationFrame(Universe u, double ts, double stop){
        universe = u;
        timeStep = ts;
        stopTime = stop;
        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int scroll = e.getWheelRotation();
                universe.changeScale(scroll);
            }
        });

        tickTimer = new Timer(tickRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (universe.getTime() < stopTime){
                    universe.tick(timeStep);
                }
            }
        });

        drawTimer = new Timer(drawRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                universe.draw();
            }
        });

        add(universe);
        tickTimer.start();
        drawTimer.start();
    }
}
