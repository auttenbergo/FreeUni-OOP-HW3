import junit.framework.TestCase;

import java.util.List;

public class MetropolisDatabaseTest extends TestCase {
    MetropolisDatabase db;

    private int[] id = new int[7];
    private static final String[] persons = {"Sicho","Aleko","Devasha","Shvango","Gachecha","Giorgobiana","Dedamisa"};
    private static final String[] continents = {"Europe","Asia","Africa","Antarctica","North America","South America","Australia"};
    private static final int[] populations = {1000,2000,3000,4000,5000,6000,7000};

    protected void setUp() throws Exception{
        DBConfig dbconfig;
        dbconfig = new DBConfig();
        db = new MetropolisDatabase(dbconfig.getConnection(),DBConfig.TEST_TABLE_NAME);
    }

    public void test1(){
        List<Metropolis> metropolises = db.getMetropolises();
        assertEquals(0,metropolises.size());
    }
    public void test2(){
        for(int i=0;i<persons.length;i++){
            Metropolis metropolis = new Metropolis(persons[i],continents[i],populations[i]);
            db.addMetropolis(metropolis);
            id[i] = metropolis.getID();
        }
        List<Metropolis> metropolises = db.getMetropolises();
        assertEquals(persons.length,metropolises.size());
        for(int i=0;i<metropolises.size();i++){
            Metropolis current = metropolises.get(i);
            assertEquals(current.getID(),id[i]);
            assertEquals(current.getMetropolis(),persons[i]);
            assertEquals(current.getContinent(),continents[i]);
            assertEquals(current.getPopulation(),populations[i]);
        }
        for(int i=0;i<id.length;i++){
            db.deleteMetropolis(id[i]);
        }
    }
    public void test3(){
        for(int i=0;i<persons.length;i++){
            Metropolis metropolis = new Metropolis(persons[i],continents[i],populations[i]);
            db.addMetropolis(metropolis);
            id[i] = metropolis.getID();
        }
        for(int i=0;i<id.length;i++){
            db.deleteMetropolis(id[i]);
        }
        List<Metropolis> metropolises = db.getMetropolises();
        assertEquals(0,metropolises.size());
    }
    public void test4(){
        for(int i=0;i<persons.length;i++){
            Metropolis metropolis = new Metropolis(persons[i],continents[i],populations[i]);
            db.addMetropolis(metropolis);
            id[i] = metropolis.getID();
        }
        List<Metropolis> metropolisList = db.getMetropolises();
        for(int i=0;i<metropolisList.size();i++){
            Metropolis current = metropolisList.get(i);
            int nextIndex = (i+1) % persons.length;
            db.editMetropolis(current.getID(),persons[nextIndex],0);
            db.editMetropolis(current.getID(),continents[nextIndex],1);
            db.editMetropolis(current.getID(),String.valueOf(populations[nextIndex]),2);
        }
        List<Metropolis> metropolises = db.getMetropolises();
        for(int i=0;i<metropolises.size();i++){
            Metropolis current = metropolises.get(i);
            int nextIndex = (i+1) % persons.length;
            assertEquals(current.getMetropolis(),persons[nextIndex]);
            assertEquals(current.getContinent(),continents[nextIndex]);
            assertEquals(current.getPopulation(),populations[nextIndex]);
        }
        for(int i=0;i<id.length;i++){
            db.deleteMetropolis(id[i]);
        }
    }
    public void test5(){
        for(int i=0;i<persons.length;i++){
            Metropolis metropolis = new Metropolis(persons[i],continents[i],populations[i]);
            db.addMetropolis(metropolis);
            id[i] = metropolis.getID();
        }
        String searchOption1 = persons[0];
        String searchOption2 = "";
        String searchOption3 = "2000";

        List<Metropolis> searchResult = db.searchMetropolises(searchOption1,searchOption2,searchOption3,true,false);
        assertEquals(0,searchResult.size());
        searchResult = db.searchMetropolises(searchOption1,searchOption2,searchOption3,false,false);
        assertEquals(1,searchResult.size());

        Metropolis searchM = searchResult.get(0);
        assertEquals(searchM.getID(),id[0]);
        assertEquals(searchM.getMetropolis(),persons[0]);
        assertEquals(searchM.getContinent(),continents[0]);
        assertEquals(searchM.getPopulation(),populations[0]);

        for(int i=0;i<id.length;i++){
            db.deleteMetropolis(id[i]);
        }
    }
    public void test6(){
        for(int i=0;i<persons.length;i++){
            Metropolis metropolis = new Metropolis(persons[i],continents[i],populations[i]);
            db.addMetropolis(metropolis);
            id[i] = metropolis.getID();
        }
        String searchOption1 = "si";
        String searchOption2 = "as";
        String searchOption3 = "2000";

        List<Metropolis> searchResult = db.searchMetropolises(searchOption1,searchOption2,searchOption3,false,false);
        assertEquals(0,searchResult.size());

        for(int i=0;i<id.length;i++){
            db.deleteMetropolis(id[i]);
        }
    }
    public void test7(){
        for(int i=0;i<persons.length;i++){
            Metropolis metropolis = new Metropolis(persons[i],continents[i],populations[i]);
            db.addMetropolis(metropolis);
            id[i] = metropolis.getID();
        }
        String searchOption1 = "eko";
        String searchOption2 = "as";
        String searchOption3 = "255";

        List<Metropolis> searchResult = db.searchMetropolises(searchOption1,searchOption2,searchOption3,false,true);
        assertEquals(0,searchResult.size());
        searchResult = db.searchMetropolises(searchOption1,searchOption2,searchOption3,true,false);
        Metropolis searchM = searchResult.get(0);
        assertEquals(searchM.getID(),id[1]);
        assertEquals(searchM.getMetropolis(),persons[1]);
        assertEquals(searchM.getContinent(),continents[1]);
        assertEquals(searchM.getPopulation(),populations[1]);

        for(int i=0;i<id.length;i++){
            db.deleteMetropolis(id[i]);
        }
    }
    public void test8(){

    }


}
