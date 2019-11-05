package cat.iespaucasesnoves.spadd.jdbc;

import cat.iespaucasesnoves.spadd.jdbc.auxiliars.JDBCException;
import cat.iespaucasesnoves.spadd.jdbc.baseDades.BaseDades;
import cat.iespaucasesnoves.spadd.jdbc.dades.Autor;
import cat.iespaucasesnoves.spadd.jdbc.dades.Nacionalitat;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class ProvesJDBC {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "seCret_19");
        try{
            //BaseDades basedades = new BaseDades( "jdbc:mysql://192.168.1.134:3306/biblioteca", properties );
            BaseDades basedades = new BaseDades( "jdbc:mysql://localhost:3306/biblioteca", properties );
            try {
                //List<String> llengues = basedades.getLlengues();
                //List<String> llengues = basedades.getLlibresLlengua("Catalana");
                //List<String> llengues = basedades.getLlibresLlenguaSafe("Anglesa");

                //9.4.1- Mostra per pantalla la informació de totes les nacionalitats de la base de dades.
                List<Nacionalitat> nacionalitats = basedades.getNacionalitats();
                for (Nacionalitat nacionalitat: nacionalitats) {
                    System.out.println( nacionalitat );
                }

                //9.4.2- Mostra per pantalla la fila Autor amb una certa clau primària.
                Autor autorPerId = basedades.getAutor(18);
                System.out.println( autorPerId );

                //9.4.3- Mostra per pantalla els autors associats a una determinada nacionalitat
                List<Autor> autors = basedades.getAutors( nacionalitats.get(10) );
                for (Autor autor: autors
                     ) {
                    System.out.println(autor);
                }


                //10- Crea a la classe BaseDades un mètode anomenat insereixNacionalitatAutors() que rebi com
                // a paràmetre una nacionalitat i una llista d'autors d'aquesta nacionalitat.
                // Els ha d'inserir a la base de dades.
                Nacionalitat nacionalitatPerInsertar = new Nacionalitat(" vaya");
                //basedades.insereixNacionalitatAutors(nacionalitatPerInsertar, autors);
                basedades.insereixNacionalitatAutorsTransaccio(nacionalitatPerInsertar, autors);

                //11- Comprova que la base de dades s'ha actualitzat correctament, pots utilitzar els mètodes fets anteriorment
                // per comprovar-ho.
                autors = basedades.getAutors( nacionalitatPerInsertar );
                for (Autor autor: autors
                ) {
                    System.out.println(autor);
                }
                basedades.corregir("IRLANDES","IRLANDESA");

            }catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (JDBCException e){
            e.printStackTrace();
        }
    }

}

