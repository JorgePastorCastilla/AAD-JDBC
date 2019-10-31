package cat.iespaucasesnoves.spadd.jdbc.dades;

public class Nacionalitat {
    String nom;

    public Nacionalitat(String nom){
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
