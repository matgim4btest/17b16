package rs.edu.matgim.zadatak;

import java.sql.SQLException;

public class Program {

    public static void main(String[] args) throws SQLException {

        DB _db = new DB();
        _db.printPositiveRacun();
        
         _db.zadatak(1, 2, (float) 1.4);
        
    }
}
