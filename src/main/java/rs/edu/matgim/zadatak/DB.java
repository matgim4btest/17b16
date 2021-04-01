package rs.edu.matgim.zadatak;

import java.sql.*;

public class DB {

    String connectionString = "jdbc:sqlite:src\\main\\java\\Banka.db";

    public void printPositiveRacun()  {
        try ( Connection conn = DriverManager.getConnection(connectionString);  Statement s = conn.createStatement()) {

            ResultSet rs = s.executeQuery("SELECT IdRac FROM Racun WHERE Stanje>0");
            while (rs.next()) {
                int idRac = rs.getInt(1);
                
                

                System.out.println(String.format("%d",idRac ));
            }

        } catch (SQLException ex) {
            System.out.println("Greska prilikom povezivanja na bazu");
            System.out.println(ex);
        }
    }
    
    boolean nesto(int idRac,float sum) throws SQLException
            {
              try ( Connection conn = DriverManager.getConnection(connectionString);  Statement s = conn.createStatement()){
              ResultSet rs=s.executeQuery("SELECT Stanje , DozvMinus FROM RACUN WHERE IdRac=idRac");
              int x=rs.getInt(1);
              int y=rs.getInt(2);
              if(x-sum>y)
                      
                  return true;
              else return false;}
              
    }
       void bm(int idRac) throws SQLException
            {
              try ( Connection conn = DriverManager.getConnection(connectionString);  Statement s = conn.createStatement()){
              ResultSet rs=s.executeQuery("SELECT Stanje , DozvMinus FROM RACUN WHERE IdRac=idRac");
              int x=rs.getInt(1);
              int y=rs.getInt(2);
              if(x>y)
              {ResultSet rs1=s.executeQuery("UPDATE Racun SET STATUS 'A'"); 
                  
              }
              
              }              
    }
    
    boolean zadatak(int idRacFrom, int idRacTo, float sum) throws SQLException 
    {
        try ( Connection conn = DriverManager.getConnection(connectionString);  Statement s = conn.createStatement()) {       String upit1="SELECT Stanje,DozvMinus FROM RACUN WHERE IdRac=idRacTo";
        String upit="Update Racun Set Stanje=Stanje+? Where IdRac=?";
       PreparedStatement ps=conn.prepareStatement(upit);
       conn.setAutoCommit(false);
       if(!(nesto(idRacFrom,sum))) {System.out.println("Uspesna realizacija");return false;}
       ps.setDouble(1, sum);
       ps.setInt(2, idRacFrom);
       ps.executeUpdate();
       PreparedStatement ps2=conn.prepareStatement(upit);
       ps2.setDouble(1, -sum);
       ps2.setInt(2,idRacTo);
       ps2.execute();
       bm(idRacTo);
      
       System.out.println("Uspesna realizacija");
       conn.commit();
       conn.setAutoCommit(true);
       return true;
         } catch (SQLException ex) {
             System.out.println("Greska prilikom povezivanja na bazu");
            System.out.println(ex);
            return false;}



    }}

