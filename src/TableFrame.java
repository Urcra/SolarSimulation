import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.util.List;


public class TableFrame extends JFrame{
    private Universe universe;
    private JTable universeTable;

    public final static int FRAME_HEIGHT = 400;
    public final static int FRAME_WIDTH = 400;

    /**
     * Create a new window which shows the offset between the universe and the JPL Epheremis data
     * @param u The universe that is updated
     */
    public TableFrame(Universe u) {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        universe = u;
        universeTable = new JTable(new UniverseModel(u.universeTable));
        JScrollPane scrollPane = new JScrollPane(universeTable);
        universeTable.setFillsViewportHeight(true);
        add(scrollPane);
    }
}


class UniverseModel extends AbstractTableModel{

    private List<String[]> planetData;

    /**
     * The model we use to update the JTable with the data from the universe
     * @param data
     */
    UniverseModel(List<String[]> data){
        planetData = data;
    }

     @Override
    public int getRowCount() {
        return planetData.size();
    }

    @Override
    public String getColumnName(int col){
        switch (col){
            case 0 : return "Planet";
            case 1 : return "Date";
            case 2 : return "Offset";
            default : return Integer.toString(col);
        }
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return planetData.get(rowIndex)[columnIndex];
    }
}
