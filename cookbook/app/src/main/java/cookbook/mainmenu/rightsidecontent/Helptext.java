package cookbook.mainmenu.rightsidecontent;

/**
 * This is used for the search features in help.
 * Find implementation in HelpScene. 
 */
public class Helptext {
  private String match;
  private String text;

  public Helptext(String match, String text) {
    this.match = match;
    this.text = text;
  }

  public void setMatch(String match) {
    this.match = match;
  }

  public String getMatch() {
    return match;
  }

  public void setText(String text) {
    this.text = text;

  }

  public String getText() {
    return text;
  }
}
