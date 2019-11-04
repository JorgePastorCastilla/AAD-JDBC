package cat.iespaucasesnoves.spadd.jdbc.baseDades;

import cat.iespaucasesnoves.spadd.jdbc.auxiliars.JDBCException;
import cat.iespaucasesnoves.spadd.jdbc.dades.Autor;
import cat.iespaucasesnoves.spadd.jdbc.dades.Nacionalitat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BaseDades {

    String connectionURL;
    Properties properties;

    public BaseDades(String connection, Properties properties) throws JDBCException{
        if( ( connection == "" || connection == null ) || ( properties == null ) ){
            throw new JDBCException("URL and Properties can't be null or empty strings");
        }
        setConnection(connection);
        setProperties(properties);
    }

    public List getLlengues() throws SQLException {
        Connection con = DriverManager.getConnection( connectionURL, properties );
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery("SELECT * from LLENGUES");
        List<String> resultatsQuery = new ArrayList<String>();
        while( result.next() ){
            String llengua = result.getString("llengua");
            resultatsQuery.add(llengua);
        }
        con.close();
        return resultatsQuery;
    }

    public List getLlibresLlengua(String llengua)throws SQLException{
        Connection con = DriverManager.getConnection( connectionURL, properties );
        Statement statement = con.createStatement();
        String sql = "SELECT * FROM LLIBRES where FK_LLENGUA = '" + llengua + "';";
        System.out.println( sql );
        ResultSet result = statement.executeQuery( sql );
        List<String> resultatsQuery = new ArrayList<String>();
        while( result.next() ){
            String resultString = result.getString("TITOL");
            resultatsQuery.add(resultString);
        }
        con.close();
        return resultatsQuery;
    }

    public List getLlibresLlenguaSafe(String llengua)throws SQLException{
        Connection con = DriverManager.getConnection( connectionURL, properties );
        Statement statement = con.createStatement();
        String sql = "SELECT * FROM LLIBRES where FK_LLENGUA = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,llengua);
        System.out.println( ps );
        ResultSet result = ps.executeQuery();
        List<String> resultatsQuery = new ArrayList<String>();
        while( result.next() ){
            String resultString = result.getString("TITOL");
            resultatsQuery.add(resultString);
        }
        con.close();
        return resultatsQuery;
    }

    public List getNacionalitats() throws SQLException {
        Connection con = DriverManager.getConnection( connectionURL, properties );
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery("SELECT * from NACIONALITATS");
        List<Nacionalitat> nacionalitats = new ArrayList<Nacionalitat>();
        while( result.next() ){
            String nacionalitat = result.getString("NACIONALITAT");
            nacionalitats.add( new Nacionalitat(nacionalitat) );
        }
        con.close();
        return nacionalitats;
    }

    public Autor getAutor(int ID_AUT) throws SQLException {
        Autor autor = null;
        Connection con = DriverManager.getConnection( connectionURL, properties );
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery("SELECT ID_AUT, NOM_AUT, FK_NACIONALITAT FROM AUTORS WHERE ID_AUT = " + ID_AUT + ";");
        while( result.next() ){
            int id = result.getInt("ID_AUT");
            String nom = result.getString("NOM_AUT");
            String nacionalitat = result.getString("FK_NACIONALITAT");
            autor = new Autor( id,nom,nacionalitat ) ;
            con.close();
            return autor;
        }
        return autor;
    }
    public void insereixNacionalitatAutors(Nacionalitat nacionalitat, List<Autor> autors)throws SQLException{
        Connection con = DriverManager.getConnection( connectionURL, properties );
        Statement statement = con.createStatement();
        int filesAfectades=statement.executeUpdate("INSERT INTO NACIONALITATS(NACIONALITAT) VALUES('" + nacionalitat.getNom() + "');");
        for (Autor autor: autors) {
            filesAfectades=statement.executeUpdate("INSERT INTO AUTORS(NOM_AUT, FK_NACIONALITAT) VALUES('" + autor.getNomAutor() + "','" + nacionalitat.getNom() + "')");
        }
    }

    //13- Crea el mètode insereixNacionalitatAutorsTransaccio() que rebi com a paràmetre una
    // nacionalitat i una llista d'autors d'aquesta nacionalitat. Els ha d'inserir a la
    // base de dades utilitzant una transacció. Captura l'SQLException i si hi ha hagut un error fes un rollback.

    public void insereixNacionalitatAutorsTransaccio(Nacionalitat nacionalitat, List<Autor> autors)throws SQLException{
        Connection con = null;
        try{
            con = DriverManager.getConnection( connectionURL, properties );
            con.setAutoCommit(false);
            Statement statement = con.createStatement();
            int filesAfectades=statement.executeUpdate("INSERT INTO NACIONALITATS(NACIONALITAT) VALUES('" + nacionalitat.getNom() + "');");
            for (Autor autor: autors) {
                filesAfectades=statement.executeUpdate("INSERT INTO AUTORS(NOM_AUT, FK_NACIONALITAT) VALUES('" + autor.getNomAutor() + "','" + nacionalitat.getNom() + "')");
            }
            con.commit();
        }catch(SQLException e){
            con.rollback();
        }finally {
            con.close();
        }
    }
    //12- Crea el mètode esborraNacionalitat() que donada una nacionalitat com a paràmetre l'esborri de la base de dades,
    // juntament amb tots els autors d'aquesta nacionalitat.
    public void esborraNacionalitat(Nacionalitat nacionalitat)throws SQLException{
        Connection con = DriverManager.getConnection( connectionURL, properties );
        Statement statement = con.createStatement();
        int filesAfectades=statement.executeUpdate("DELETE FROM AUTORS WHERE FK_NACIONALITAT = '" + nacionalitat.getNom() + "';");
        filesAfectades = statement.executeUpdate("DELETE FROM NACIONALITATS WHERE NACIONALITAT = '" + nacionalitat.getNom() + "';");
    }

    public List<Autor> getAutors( Nacionalitat nacionalitat ) throws SQLException{
        Connection con = DriverManager.getConnection( connectionURL, properties );
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery("SELECT ID_AUT, NOM_AUT, FK_NACIONALITAT FROM AUTORS, NACIONALITATS WHERE NACIONALITATS.NACIONALITAT = '" + nacionalitat.getNom() + "' AND AUTORS.FK_NACIONALITAT = NACIONALITATS.NACIONALITAT;");
        List<Autor> autors = new ArrayList<Autor>();
        while( result.next() ){
            int id = result.getInt("ID_AUT");
            String nom = result.getString("NOM_AUT");
            String fknacionalitat = result.getString("FK_NACIONALITAT");
            autors.add( new Autor( id, nom, fknacionalitat ) );
        }
        con.close();
        return autors;
    }

    public String getConnection() {
        return connectionURL;
    }

    public void setConnection(String connection) throws JDBCException{
        if( ( connection == "" || connection == null ) ){
            throw new JDBCException("URL  can't be null or empty string");
        }
        this.connectionURL = connection;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) throws JDBCException{
        if( properties == null ){
            throw new JDBCException("Properties can't be null");
        }
        this.properties = properties;
    }
}
