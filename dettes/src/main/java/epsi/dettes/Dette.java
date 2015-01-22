package epsi.dettes;

/**
 * Created by Etienne on 21/01/15.
 */
public class Dette {
    public int dette_id;
    public String title;
    public String name;
    public String numero;
    public String montant;

    public Dette(int _id, String _title, String _name, String _numero, String _montant){
        dette_id = _id;
        title = _title;
        name = _name;
        numero = _numero;
        montant = _montant;
    }
}
