public class Metropolis{

    private static final int NO_ID = 1;

    private int id;
    private String metropolis;
    private String continent;
    private int population;

    public Metropolis(int id,String metropolis, String continent, int population){
        this.id = id;
        this.metropolis = metropolis;
        this.continent = continent;
        this.population = population;
    }

    public Metropolis(String metropolis, String continent, int population) {
        this(NO_ID,metropolis,continent,population);
    }

    public int getID(){
        return id;
    }
    public void setID(int id){
        this.id = id;
    }

    public String getMetropolis() {
        return metropolis;
    }

    public String getContinent() {
        return continent;
    }

    public int getPopulation() {
        return population;
    }

    public void setMetropolis(String metropolis) {
        this.metropolis = metropolis;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
