package cat.iespaucasesnoves.spadd.jdbc.dades;

public class Autor {

    /*
     El nom de la nacionalitat no pot ser null o una cadena només de blancs o buida.
     Autor ha de tenir atributs per els camps de la taula ID_AUT, NOM_AUT
     que no poden ser null ni buids, i FK_NACIONALITAT que si pot ser nul.
    * */
    int ID_AUT;
    String NOM_AUT;
    String FK_NACIONALITAT;

    public Autor(int ID_AUT, String NOM_AUT, String FK_NACIONALITAT){
        this.ID_AUT = ID_AUT;
        this.NOM_AUT = NOM_AUT;
        this.FK_NACIONALITAT = FK_NACIONALITAT;
    }
    public Autor(int ID_AUT, String NOM_AUT){
        this.ID_AUT = ID_AUT;
        this.NOM_AUT = NOM_AUT;
        this.FK_NACIONALITAT = null;
    }

    public int getID_AUT() {
        return ID_AUT;
    }

    public void setID_AUT( int ID_AUT ) {
        this.ID_AUT = ID_AUT;
    }

    public String getNOM_AUT() {
        return NOM_AUT;
    }

    public void setNOM_AUT( String NOM_AUT ) {
        if( !NOM_AUT.equals( "" ) && !NOM_AUT.equals( null ) ) {
            this.NOM_AUT = NOM_AUT;
        }
    }

    public String getFK_NACIONALITAT() {
        return FK_NACIONALITAT;
    }

    public void setFK_NACIONALITAT(String FK_NACIONALITAT) {
        this.FK_NACIONALITAT = FK_NACIONALITAT;
    }

    @Override
    public String toString() {
        return "ID_AUT: " + ID_AUT + ". NOM_AUT: " + NOM_AUT + ". FK_NACIONALITAT: " + FK_NACIONALITAT;
    }
}
