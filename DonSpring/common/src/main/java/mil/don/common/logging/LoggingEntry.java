package mil.don.common.logging;


import java.io.Serializable;
import java.util.Date;


public class LoggingEntry implements Serializable
{
  private Date _timestamp;
  private LoggingLevel _loggingLevel;
  private String _source;
  private String _message;



  public LoggingEntry()
  {
  }

  public LoggingEntry(Date ts, LoggingLevel ll, String source, String message)
  {
    this._timestamp = ts;
    this._loggingLevel = ll;
    this._source = source;
    this._message = message;
  }

  public Date getTimestamp()
  {
    return _timestamp;
  }
  public LoggingEntry setTimestamp(Date timestamp) { _timestamp = timestamp; return this; }


  public LoggingLevel getLoggingLevel()
  {
    return _loggingLevel;
  }
  public LoggingEntry setLoggingLevel(LoggingLevel loggingLevel) { _loggingLevel = loggingLevel; return this; }

  public String getSource()
  {
    return _source;
  }
  public LoggingEntry setSource(String source) { _source = source; return this; }

  public String getMessage()
  {
    return _message;
  }
  public LoggingEntry setMessage(String message) { _message = message; return this; }


  @Override
  public String toString()
  {
    return String.format("%tr: P=%s S=%s : %s", getTimestamp(), getLoggingLevel(), getSource(), getMessage());
  }
}
