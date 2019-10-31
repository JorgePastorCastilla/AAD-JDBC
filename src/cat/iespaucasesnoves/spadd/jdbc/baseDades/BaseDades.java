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
        Connection con = DriverManager.getConnection( connectionURL, properties );
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery("SELECT ID_AUT, NOM_AUT, FK_NACIONALITAT from AUTORS");
        Autor autor = new Autor( result.getInt("ID_AUT"), result.getString("NOM_AUT"), result.getString("FK_NACIONALITAT") ) ;
        while( result.next() ){
            int ID_AUT = result.getInt("ID_AUT");
            String nacionalitat = result.getString("FK_NACIONALITAT");
            String nacionalitat = result.getString("FK_NACIONALITAT");
            nacionalitats.add( new Nacionalitat(nacionalitat) );
        }
        con.close();
        return autor;
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
