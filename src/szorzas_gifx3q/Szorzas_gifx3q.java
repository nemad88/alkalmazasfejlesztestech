package szorzas_gifx3q;

/**
 * 
 * @author Adam Nemeth
 * @version 1.0
 *
 */
public class Szorzas_gifx3q {

    public static void main(String[] args) {
        
    }

    /**
     * Két nem negatív számot szoroz össze. Ha negatív számot kap akkor -1-et 
     * ad vissza.
     * @author Adam Nemeth
     * @version 1.0
     * @param first első szám
     * @param second második szám
     */
    public static int multiplyNaturals(int first, int second) {
        if (first >= 0 && second >= 0) {
            return first * second;
        } else {
            return -1;
        }
    }
}
