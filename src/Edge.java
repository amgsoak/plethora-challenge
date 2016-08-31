import java.util.ArrayList;

/**
 * Created by Adam on 8/31/2016.
 */
public abstract class Edge {
    public EdgeType type;
    public int id;

    public Edge(EdgeType type, int id) {
        this.type = type;
        this.id = id;
    }

    public abstract double getLength();
}
