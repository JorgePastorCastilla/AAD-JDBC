package cat.iespaucasesnoves.spadd.jdbc.dades;

public class Autor {

    /*
     El nom de la nacionalitat no pot ser null o una cadena només de blancs o buida.
     Autor ha de tenir atributs per els camps de la taula ID_AUT, NOM_AUT
     que no poden ser null ni buids, i FK_NACIONALITAT que si pot ser nul.
    * */
    int idAutor;
    String nomAutor;
    String nacionalitat;

    public Autor(int idAutor, String nomAutor, String nacionalitat){
        this.idAutor = idAutor;
        this.nomAutor = nomAutor;
        this.nacionalitat = nacionalitat;
    }
    public Autor(int idAutor, String nomAutor){
        this.idAutor = idAutor;
        this.nomAutor = nomAutor;
        this.nacionalitat = null;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNomAutor() {
        return nomAutor;
    }

    public void setNomAutor(String nomAutor) {
        if( !nomAutor.equals( "" ) && !nomAutor.equals( null ) ) {
            this.nomAutor = nomAutor;
        }
    }

    public String getNacionalitat() {
        return nacionalitat;
    }

    public void setNacionalitat(String nacionalitat) {
        this.nacionalitat = nacionalitat;
    }

    @Override
    public String toString() {
        return "ID_AUT: " + idAutor + ". NOM_AUT: " + nomAutor + ". FK_NACIONALITAT: " + nacionalitat;
    }
}
