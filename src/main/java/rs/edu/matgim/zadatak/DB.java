package rs.edu.matgim.zadatak;

import java.sql.*;
import java.text.SimpleDateFormat;

public class DB {

    String connectionString = "jdbc:sqlite:src\\main\\java\\Banka.db";

    public void printPositiveRacun()  {
        try ( Connection conn = DriverManager.getConnection(connectionString);  Statement s = conn.createStatement()) {

            ResultSet rs = s.executeQuery("SELECT IdRac,Stanje,DozvMinus,Status FROM Racun ");
            while (rs.next()) {
                int idRac = rs.getInt(1);
                int sta=rs.getInt(2);
                int doz=rs.getInt(3);
                String x=rs.getString(4);
                


                System.out.println(String.format("%d,%d,%d,%s",idRac,sta,doz,x ));
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
              if(x-sum>-y)
                      
                  return true;
              return false;}
              
    }

    
       void menjaj() throws SQLException
       {             try ( Connection conn = DriverManager.getConnection(connectionString);Statement s = conn.createStatement() ){
             ResultSet rs1=s.executeQuery("SELECT Max(IdSta) FROM Stavka");
        int m=rs1.getInt(1)+1;
        System.out.println(m);
       }
       };
    
    boolean zadatak(int idRacFrom, int idRacTo, int sum) throws SQLException 
    {
        try ( Connection conn = DriverManager.getConnection(connectionString);  Statement s = conn.createStatement();Statement s1 = conn.createStatement();Statement s2=conn.createStatement();) {       
            String upit1="SELECT Stanje,DozvMinus FROM RACUN WHERE IdRac=idRacTo";
        String upit="Update Racun Set Stanje=Stanje+? Where IdRac=?";
       PreparedStatement ps=conn.prepareStatement(upit);
       conn.setAutoCommit(false);
       if(!(nesto(idRacFrom,sum))) {System.out.println("kurac na bicikli");return false;}
       ps.setInt(1, -sum);
       ps.setInt(2, idRacFrom);
       ps.executeUpdate();
       PreparedStatement ps2=conn.prepareStatement(upit);
       ps2.setInt(1, sum);
       ps2.setInt(2,idRacTo);
       ps2.execute();
       ResultSet rs=s.executeQuery("SELECT Stanje , DozvMinus FROM RACUN WHERE IdRac=idRac");
              int x=rs.getInt(1);
              int y=rs.getInt(2);
            if(x>-y) {PreparedStatement ps1=conn.prepareStatement("UPDATE Racun SET STATUS='A' WHERE IdRac=idRac"); ps1.execute(); }
        PreparedStatement ps3=conn.prepareStatement("INSERT INTO Stavka (IdSta, RedBroj, Datum, Vreme, Iznos, IdRac, IdFil) VALUES (?,?,?,?,?,?,?)");   
        ResultSet rs1=s1.executeQuery("SELECT MAX(IdSta)+1 FROM Stavka");
        int m=rs1.getInt(1);
        ps3.setInt(1,m);
        ResultSet rs2=s2.executeQuery("SELECT BrojStavki FROM RACUN WHERE IdRac=IdRacTo");
        int m1=rs2.getInt(1)+1;
        ps3.setInt(2,m1);
        SimpleDateFormat formatter1= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        ps3.setString(3,formatter1.format(date));
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
        ps3.setString(4,formatter1.format(date) );
        ps3.setFloat(5,sum);
        ps3.setInt(6,1);
        ps3.setInt(7, idRacTo);
        ps3.execute();
        PreparedStatement ps4=conn.prepareStatement("INSERT INTO Uplata (IdSta,Osnov) VALUES (?,?)");
        ps4.setInt(1,m);
        ps4.setString(2,"Uplata na racun");
        ps4.execute();
       System.out.println("Uspesna realizacija");
       
       conn.commit();
       conn.setAutoCommit(true);
       return true;
         } catch (SQLException ex) {
             System.out.println("Greska prilikom povezivanja na bazu");
            System.out.println(ex);
            return false;}



    }}

/*SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
Date date = new Date(System.currentTimeMillis());
System.out.println(formatter.format(date));*/