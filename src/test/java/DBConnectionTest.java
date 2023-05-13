import com.eleme.dao.BusinessDao;
import com.eleme.dao.impl.BusinessDaoImpl;
import com.eleme.po.Business;
import com.eleme.util.DBUtil;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class DBConnectionTest {
    @Test
    public void testConnection(){
        Connection connection= DBUtil.getConnection();
        System.out.println("11111");
    }
    @Test
    public  void testBusinessDao(){
        BusinessDao dao=new BusinessDaoImpl();
        List<Business>list=dao.listBusiness("饭","");
        System.out.println("111111");
    }
    @Test
    public void testSaveBuisness(){
        BusinessDao dao=new BusinessDaoImpl();
        dao.removeBusiness(10008);
    }
    @Test
    public void testUdpBuisness(){
        BusinessDao dao=new BusinessDaoImpl();
        List<Business> list=dao.listBusiness("南屏街麦当劳","");
        Business buss=list.get(0);
        buss.setDeliveryPrice(5.0);
        buss.setBusinessAddress("南屏步行街");
        buss.setStarPrice(20.0);
        buss.setBusinessExplain("我们家很好吃!");
        dao.updateBusiness(buss);
    }
}


