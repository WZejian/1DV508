package cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DbConnTest {
  @Test
  /**
   * Blocks execution if connection fails.
   */
  void testConnection() {
    DbConnector.connect();
    assertEquals(true, DbConnector.getStatus());
  }
}
