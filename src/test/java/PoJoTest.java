import com.eleme.po.Admin;
import com.eleme.po.Business;
import org.junit.Test;

public class PoJoTest {

    @Test
    public void testToString(){
        Business business=new Business();
        System.out.println(business.toString());
    }
}
