package cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserAuthTest {
  @Test
  void gibberishLogin() {
    DbConnector.connect();
    assertEquals(null, UserAuth.authenticate("thisdoesnot", "existindatabase"));
  }
}
