package dat.backend.model.entities;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;
public class test {
    // Added lines to XML
    // manually added external libraries from javaCSG
    //should work as intended.
    public static void main(String[] args)
    {
        JavaCSG csg = JavaCSGFactory.createDefault();
        Geometry3D cyl = csg.cylinder3D(10, 20, 32, true);
        csg.view(cyl);
    }
}
