import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MetropolisDatabase {

    private Connection connection;

    private String TABLE_NAME;

    private String GET_METROPOLISES;
    private String ADD_METROPOLIS;
    private String SEARCH_METROPOLISES_UNFINISHED;
    private String EDIT_METROPOLIS_UNFINISHED;
    private String DELETE_METROPOLIS_PREPARED;


    /**
     * Selects every record from database and returns
     * @return List of all metropolises in database
     */
    protected List<Metropolis> getMetropolises(){
        List<Metropolis> rv = new ArrayList<>();
        try{
            Statement stm = connection.createStatement();
            ResultSet result = stm.executeQuery(GET_METROPOLISES);
            while(result.next()){
                int id = result.getInt("id");
                String metropolis = result.getString("metropolis");
                String continent = result.getString("continent");
                int population = result.getInt("population");
                rv.add(new Metropolis(id,metropolis,continent,population));
            }
        } catch(Exception exception){
            System.out.println(exception);
        }
        return rv;
    }

    /**
     * Adds given metropolis to the database
     * @param metropolis New record which should be added to the database
     */
    protected void addMetropolis(Metropolis metropolis){
        try{
            PreparedStatement stm = connection.prepareStatement(ADD_METROPOLIS,PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1,metropolis.getMetropolis());
            stm.setString(2,metropolis.getContinent());
            stm.setInt(3,metropolis.getPopulation());
            int affected_rows = stm.executeUpdate();

            if(affected_rows == 0)
                throw new SQLException("Unable to create new record");
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();

            metropolis.setID(generatedKeys.getInt(1));

        } catch(Exception exception){
            System.out.println(exception);
        }
    }

    /**
     * Given a specific id for a record, deletes in the database
     * @param id id of record
     */
    protected void deleteMetropolis(int id){
        try{
            PreparedStatement stm = connection.prepareStatement(DELETE_METROPOLIS_PREPARED);
            stm.setInt(1,id);
            stm.executeUpdate();
        } catch(SQLException exception){
            System.out.println(exception);
        }

    }

    /**
     * Edits a record in the database based on given parameters
     * @param id Identifier of metropolis
     * @param value New value
     * @param columnIndex Identifier of column
     */
    protected void editMetropolis(int id,String value,int columnIndex){
        String query = EDIT_METROPOLIS_UNFINISHED;
        switch (columnIndex) {
            case 0 -> query += "metropolis = ? WHERE id = ?;";
            case 1 -> query += "continent = ? WHERE id = ?;";
            case 2 -> query += "population = ? WHERE id = ?;";
            default -> throw new RuntimeException("Column index out of bounds : " + columnIndex);
        }
        try{
            PreparedStatement stm = connection.prepareStatement(query);
            switch(columnIndex){
                case 0, 1 -> stm.setString(1,value);
                case 2 -> stm.setInt(1,Integer.parseInt(value));
                default -> throw new RuntimeException("Column index out of bounds : " + columnIndex);
            }
            stm.setInt(2,id);
            stm.executeUpdate();
        } catch(SQLException exception){
            System.out.println(exception);
        }
    }

    /**
     * Provided search options, searches records in the database which fulfill given options
     * @param metropolis
     * @param continent
     * @param population
     * @param populationLargerThan Is population larger than the given number or not
     * @param exactMatch Should given parameters be exact match in the database records or not
     * @return List of records, which fulfilled the search options
     */
    protected List<Metropolis> searchMetropolises(String metropolis,String continent,String population,boolean populationLargerThan,boolean exactMatch){
        if(metropolis.length() == 0 && continent.length() == 0 && population.length() == 0)
            return getMetropolises();

        List<Metropolis> rv = new ArrayList<>();
        String query = SEARCH_METROPOLISES_UNFINISHED;

        if(metropolis.length() != 0){
            if(exactMatch)
                query += "metropolis = ?";
            else
                query += "metropolis LIKE ?";
        }
        if(continent.length() != 0){
            if(metropolis.length() != 0) query += " AND ";
            if(exactMatch)
                query += "continent = ?";
            else
                query += "continent LIKE ?";
        }
        if(population.length() != 0){
            if(metropolis.length() != 0 || continent.length() != 0) query += " AND ";
            if(populationLargerThan)
                query += "population >= ?";
            else
                query += "population < ?";
        }
        query += ";";

        try{
            PreparedStatement stm = connection.prepareStatement(query);
            if(metropolis.length() != 0){
                if(exactMatch)
                    stm.setString(1,metropolis);
                else
                    stm.setString(1,"%"+metropolis+"%");
            }
            if(continent.length() != 0){
                int index = (metropolis.length() != 0) ? 2 : 1;
                if(exactMatch)
                    stm.setString(index,continent);
                else
                    stm.setString(index,"%"+continent+"%");
            }
            if(population.length() != 0){
                int index;
                if(metropolis.length() == 0 && continent.length() == 0)
                    index = 1;
                else if (metropolis.length() != 0 && continent.length() != 0)
                    index = 3;
                else
                    index = 2;
                stm.setInt(index,Integer.parseInt(population));
            }

            ResultSet resultSet = stm.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String m = resultSet.getString(2);
                String c = resultSet.getString(3);
                int p = resultSet.getInt(4);
                rv.add(new Metropolis(id,m,c,p));
            }
        } catch(SQLException exception){
            System.out.println(exception);
        }
        return rv;
    }

    /**
     * Constructor, which receives already open connection and throws exception if not so
     * @param conn Connection, which should be already open
     */
    public MetropolisDatabase(Connection conn,String TABLE_NAME){
        if(conn == null)
            throw new RuntimeException("Connection should be initialized");
        connection = conn;
        this.TABLE_NAME = TABLE_NAME;
        GET_METROPOLISES = "SELECT id,metropolis,continent,population FROM " + TABLE_NAME + ";";
        ADD_METROPOLIS = "INSERT INTO "+TABLE_NAME+"(metropolis,continent,population) VALUES(?,?,?);";
        SEARCH_METROPOLISES_UNFINISHED = "SELECT id,metropolis,continent,population FROM "+TABLE_NAME+" WHERE ";
        EDIT_METROPOLIS_UNFINISHED = "UPDATE "+TABLE_NAME+" SET ";
        DELETE_METROPOLIS_PREPARED= "DELETE FROM "+TABLE_NAME+" WHERE id = ?;";
    }
}
