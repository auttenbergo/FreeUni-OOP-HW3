import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MetropolisTableModel extends AbstractTableModel {


    private List<Metropolis> metropolisList;
    private Connection dbconnection;

    private MetropolisDatabase db;

    /**
     * Given a metropolis,continent and population, Adds new record to table, as well as adds in database
     *
     * @param metropolis
     * @param continent
     * @param population
     */
    public void add(String metropolis,String continent,int population){
        Metropolis newMetropolis = new Metropolis(metropolis,continent,population);
        metropolisList.add(newMetropolis);
        db.addMetropolis(newMetropolis);
        fireTableDataChanged();
    }

    /**
     * Given search information, uses MetropolisDatabase.search() and sets table with  its returned records
     * @param metropolis
     * @param continent
     * @param population
     * @param populationLargerThan is "Pupulation Larger Than" selected or not
     * @param exactMatch is "Exact Match" selected or not
     */
    public void search(String metropolis,String continent,String population,boolean populationLargerThan,boolean exactMatch){
        List<Metropolis> searchResult = db.searchMetropolises(metropolis,continent,population,populationLargerThan,exactMatch);
        metropolisList = searchResult;
        fireTableDataChanged();
    }

    /**
     * Given a row index, uses MetropolisDatabase.deleteMetropolis() and deletes the record from table, as well as in database
     * @param rowIndex
     */
    private void delete(int rowIndex){
        int id = metropolisList.get(rowIndex).getID();
        metropolisList.remove(rowIndex);
        db.deleteMetropolis(id);
        fireTableDataChanged();
    }

    /**
     * Constructor, which receives MetropolisDatabase and stores inside class
     * @param db Metropolis database variable
     */
    public MetropolisTableModel(MetropolisDatabase db){
        this.db = db;
        metropolisList = db.getMetropolises();
    }

    /**
     * Returns column name based on index
     * @param columnIndex
     * @return Column name
     */
    @Override
    public String getColumnName(int columnIndex){
        switch(columnIndex){
            case 0: return "Metropolis";
            case 1: return "Continent";
            case 2: return "Population";
            case 3: return "Delete";
        }
        throw new RuntimeException(String.valueOf(columnIndex));
    }

    /**
     *
     * @return Row count
     */
    @Override
    public int getRowCount() {
        return metropolisList.size();
    }

    /**
     *
     * @return Column count
     */
    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * Given a position (rowIndex,columnIndex), changes value based on these coordinates, as well as uses MetropolisDatabase to update the record
     * @param aValue
     * @param rowIndex
     * @param columnIndex
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex,int columnIndex){
        String str = (String)aValue;
        Metropolis metropolis = metropolisList.get(rowIndex);
        switch (columnIndex) {
            case 0 -> metropolis.setMetropolis(str);
            case 1 -> metropolis.setContinent(str);
            case 2 -> metropolis.setPopulation(Integer.valueOf(str));
            default -> throw new RuntimeException("Column index out of bounds : " + columnIndex);
        }
        db.editMetropolis(metropolis.getID(),str,columnIndex);
        fireTableDataChanged();
    }

    /**
     * Given a position (rowIndex,columnIndex), returns the value of the position
     * @param rowIndex
     * @param columnIndex
     * @return Metropolis data based on columnIndex
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Metropolis metropolis = metropolisList.get(rowIndex);


        JButton button = new JButton("delete");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete(rowIndex);
            }
        });
        switch (columnIndex){
            case 0: return metropolis.getMetropolis();
            case 1: return metropolis.getContinent();
            case 2: return metropolis.getPopulation();
            case 3: return button;
        }
        throw new RuntimeException("Column index " + String.valueOf(columnIndex) + " doesn't exist");
    }

    /**
     * Given a position(rowIndex,columnIndex), returns if the cell is editable
     * @param rowIndex
     * @param columnIndex
     * @return If cell is editable
     */
    @Override
    public boolean isCellEditable(int rowIndex,int columnIndex){
        if(columnIndex == 3) return false;
        return true;
    }
}
