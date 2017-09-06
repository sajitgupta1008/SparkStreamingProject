import java.sql.{Connection, DriverManager, ResultSet, SQLException}
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.internal.Logging
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CustomReceiver extends Receiver[String](StorageLevel.MEMORY_AND_DISK_2) with Logging {

  override def onStart(): Unit = {
    Future {
      val connection = {
        val config: Config = ConfigFactory.load()
        import config._

        Class.forName(getString("postgres.driver"))
        DriverManager.getConnection(getString("postgres.url"), getString("postgres.username"),
          getString("postgres.password"))
      }

      val statement = connection.createStatement()
      val resultSet: ResultSet = statement.executeQuery("select * from usertable;")

      while (resultSet.next()) {
        logInfo("received name :" + resultSet.getString("username"))
        store(resultSet.getString("username"))
      }

      resultSet.close()
      statement.close()
      connection.close()
    }
    restart("reconnecting again")
  }

  override def onStop(): Unit = {}
}
