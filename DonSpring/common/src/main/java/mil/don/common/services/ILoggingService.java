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

package mil.don.common.services;


import mil.don.common.logging.LoggingEntry;


// interface for our logging service
public interface ILoggingService
{
  // send a log message
  String log(LoggingEntry log);

  // send a trace level logging message
  void trace(String source, String message);

  // send a debug level logging message
  void debug(String source, String message);

  // send a info level logging message
  void info(String source, String message);

  // send a warning level logging message
  void warn(String source, String message);

  // send a error level logging message
  void error(String source, String message);


  // get recent set of logged entries
  LoggingEntry[] getRecent();
}
