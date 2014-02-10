import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.junit.Test;

/**
 *
 * @author jvergara <jvergara@gocatapult.com>
 */
public class Test1
{

    @Test
    public void test() throws Exception
    {
        PreparedStatement stm = null;
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            ResultSetMetaData meta = rs.getMetaData();

            for (int i = 1; i <= meta.getColumnCount(); ++i) {
                meta.getColumnLabel(i);
            }

            do {
                
            }
            while (rs.next());
        }
    }

}
