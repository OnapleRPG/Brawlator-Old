import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ylinor.brawlator.MonsterAction;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.MonsterBuilder;
import com.ylinor.brawlator.data.beans.TestBean;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import com.ylinor.brawlator.data.handler.SqliteHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class test {

    @Test
  public void test(){
        ConnectionSource connectionSource = null;
        try {
           /* Statement stmt = connection.createStatement();

            stmt.execute(MonsterDAO.tableCreation);
            stmt.execute(EffectDAO.tableCreation);*/

            // create our data-source for the database
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:test.db");
            TableUtils.createTable(connectionSource, TestBean.class);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // SqliteHandler.getConnection();
    }
    @Test
    public void testbdd() {
    SqliteHandler.getConnection();
    }
}


