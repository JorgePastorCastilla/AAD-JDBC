package cat.iespaucasesnoves.spadd.jdbc;

import cat.iespaucasesnoves.spadd.jdbc.auxiliars.JDBCException;
import cat.iespaucasesnoves.spadd.jdbc.baseDades.BaseDades;
import cat.iespaucasesnoves.spadd.jdbc.dades.Autor;
import cat.iespaucasesnoves.spadd.jdbc.dades.Nacionalitat;


import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ProvesJDBC {

    public static void main(String[] args) {



        Properties properties = new Properties();
        properties.put("user", "lector");
        properties.put("password", "seCret_19");
        try{
            BaseDades basedades = new BaseDades( "jdbc:mysql://192.168.1.134:3306/biblioteca", properties );
            try {
                //List<String> llengues = basedades.getLlengues();
                //List<String> llengues = basedades.getLlibresLlengua("Catalana");
                //List<String> llengues = basedades.getLlibresLlenguaSafe("Anglesa");
                List<Nacionalitat> llengues = basedades.getNacionalitats();
                Autor autor = basedades.getAutor(1);
                System.out.println( autor );


            }catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (JDBCException e){
            e.printStackTrace();
        }
    }

}
