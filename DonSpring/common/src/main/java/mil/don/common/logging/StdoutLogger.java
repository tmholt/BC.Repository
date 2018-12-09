/*-------------------------------------------------------------------------------------------------
| *** UNCLASSIFIED ***
|--------------------------------------------------------------------------------------------------
| U.S. Army Research, Development, and Engineering Command
| Aviation and Missile Research, Development, and Engineering Center
| Software Engineering Directorate, Redstone Arsenal, AL
|--------------------------------------------------------------------------------------------------
| Export-Control Act Warning: Warning - This Document contains technical data whose export is
| restricted by the Arms Export Control Act (Title 22, U.S.C., Sec 2751, et seq) or the Export
| Administration Act of 1979, as amended, Title 50, U.S.C., App. 2401 et seq. Violations of these
| export laws are subject to severe criminal penalties. Disseminate in accordance with provisions
| of DoD Directive 5230.25.
|--------------------------------------------------------------------------------------------------
| Distribution Statement C:  Distribution authorized to U.S. Government Agencies and their
| contractors, Export Controlled, Critical Technology, 13 Feb 2017. Other request for this document
| shall be referred to U.S. Army Aviation and Missile Research Development and Engineering Center
| Software Engineering Directorate, Attn: RDMR-BAW, Redstone Arsenal, AL 35898.
--------------------------------------------------------------------------------------------------*/

package mil.don.common.logging;

import java.util.Date;

import mil.don.common.services.ILoggingService;

public class StdoutLogger implements ILoggingService
{
  // send a log message
  public String log(LoggingEntry log) {
    String s = log.toString();
    System.out.println(s);
    return s;
  }

  // send a trace level logging message
  public void trace(String source, String message) {
    log(new LoggingEntry(new Date(), LoggingLevel.TRACE, "", message));
  }

  // send a debug level logging message
  public void debug(String source, String message) {
    log(new LoggingEntry(new Date(), LoggingLevel.DEBUG, "", message));
  }

  // send a info level logging message
  public void info(String source, String message) {
    log(new LoggingEntry(new Date(), LoggingLevel.INFO, "", message));
  }

  // send a warning level logging message
  public void warn(String source, String message) {
    log(new LoggingEntry(new Date(), LoggingLevel.WARNING, "", message));
  }

  // send a error level logging message
  public void error(String source, String message) {
    log(new LoggingEntry(new Date(), LoggingLevel.ERROR, "", message));
  }

  // get recent set of logged entries
  public LoggingEntry[] getRecent() { return null; }
}
