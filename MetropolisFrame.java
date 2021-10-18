
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MetropolisFrame extends JFrame {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 500;

    private static MetropolisTableModel model;
    private static JTextField metropolisInput;
    private static JTextField continentInput;
    private static JTextField populationInput;
    private static JComboBox populationFilter;
    private static JComboBox matchFilter;

    private void starterRecords(){
        String[] continents = {"Europe","Asia","Africa","Antarctica","North America","South America","Australia"};
        String[] persons = {"Sicho","Aleko","Devasha","Shvango","Gachecha","Giorgobiana","Dedamisa"};
        int[] populations = {1000,2000,3000,4000,5000,6000,7000};
        for(int i=0; i<7; i++){
            model.add(persons[i],continents[i],populations[i]);
        }
    }

    private JPanel createInputPanel(){
        JPanel inputPanel = new JPanel(new GridLayout(0,6));

        inputPanel.add(new JLabel("Metropolis:"));
        metropolisInput = new JTextField();
        inputPanel.add(metropolisInput);

        inputPanel.add(new JLabel("Continent:"));
        continentInput = new JTextField();
        inputPanel.add(continentInput);

        inputPanel.add(new JLabel("Population:"));
        populationInput = new JTextField();
        inputPanel.add(populationInput);

        return inputPanel;
    }
    private JPanel createOptionsPanel(){
        JPanel optionsPanel = new JPanel(new BorderLayout());
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String metropolis = metropolisInput.getText();
                String continent = continentInput.getText();
                String population = populationInput.getText();
                if(metropolis.length() == 0 || continent.length() == 0 || population.length() == 0) {
                    System.out.println("Fill the input fields");
                    return;
                }
                model.add(metropolis,continent,Integer.valueOf(population));
            }
        });
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String metropolis = metropolisInput.getText();
                String continent = continentInput.getText();
                String population = populationInput.getText();
                boolean exactMatch = ((String)matchFilter.getSelectedItem()).equals("Exact Match");
                boolean populationLargerThan = ((String)populationFilter.getSelectedItem()).equals("Population Larger Than");
                model.search(metropolis,continent,population,populationLargerThan,exactMatch);
            }
        });

        JPanel filterPanel = new JPanel(new GridLayout(2,0));

        String[] populationFilters = {"Population Larger Than", "Population Less Than"};
        populationFilter = new JComboBox(populationFilters);

        String[] matchFilters = {"Exact Match","Partial Match"};
        matchFilter = new JComboBox(matchFilters);

        filterPanel.add(populationFilter);
        filterPanel.add(matchFilter);

        filterPanel.setBorder(new TitledBorder("Search Options"));

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(addButton,BorderLayout.NORTH);
        buttonsPanel.add(searchButton,BorderLayout.CENTER);

        optionsPanel.add(buttonsPanel,BorderLayout.NORTH);
        optionsPanel.add(filterPanel,BorderLayout.CENTER);

        return optionsPanel;
    }


    public MetropolisFrame(String title,MetropolisTableModel model){
        super(title);
        this.model = model;

        JPanel mainPanel = new JPanel(new BorderLayout(4,4));
        JPanel inputPanel = createInputPanel();
        JPanel optionsPanel = createOptionsPanel();

        JTable table = new JTable();
        table.setModel(model);
        table.setFillsViewportHeight(true);
        table.addMouseListener(new JTableButtonMouseListener(table));

        /*
            For starter purposes call starterRecords();
         */

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumn("Delete").setCellRenderer(buttonRenderer);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(table.getTableHeader(),BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),BorderLayout.CENTER);


        mainPanel.add(inputPanel,BorderLayout.NORTH);
        mainPanel.add(tablePanel,BorderLayout.CENTER);
        mainPanel.add(optionsPanel,BorderLayout.EAST);

        setContentPane(mainPanel);
    }

    public static void main(String[] args) throws SQLException {
        DBConfig dbconfig;
        MetropolisDatabase db;
        try{
            dbconfig = new DBConfig();
            db = new MetropolisDatabase(dbconfig.getConnection(),DBConfig.MAIN_TABLE_NAME);
        } catch(Exception exception){
            System.out.println(exception); return;
        }

        MetropolisFrame main = new MetropolisFrame("Metropolises",new MetropolisTableModel(db));
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        main.setVisible(true);
    }
}
