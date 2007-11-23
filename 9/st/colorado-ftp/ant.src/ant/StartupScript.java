/**
 * Creates startup scripts.
 */
package ant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class StartupScript {

  public static void main(String[] args) throws Exception {
    System.out.println();
    System.out.println("Startup scripts writer. Use 'StartupScript libDir destDir'");

    String fromDir;
    String destDir;
    if (args.length == 0) {
      fromDir = "build/dist/lib";
      destDir = "build/dist";
    } else {
      fromDir = args[0];
      destDir = args[1];
    }

    StringBuffer libpathWin = new StringBuffer();
    StringBuffer libpathNix = new StringBuffer();

    File libdir = new File(fromDir);
    System.out.println("Reading libraries from: "+libdir.getAbsolutePath());

    int count = 0;
    File[] files = libdir.listFiles();
    for (File f : files) {
      if (f.isDirectory()) continue;
      libpathWin.append("lib/").append(f.getName()).append(";");
      libpathNix.append("lib/").append(f.getName()).append(":");
      count++;
    }
    System.out.println("Read "+count+" files");

    File distdir = new File(destDir);
    System.out.println("Creating startup scripts in: "+distdir.getAbsolutePath());

    StringBuffer text = new StringBuffer();
    text.append("@echo off").append("\r\n");
    text.append("\r\n");
    text.append("rem You can set JAVA_HOME and JAVA_OPTS variables:").append("\r\n");
    text.append("rem JAVA_HOME points to JVM").append("\r\n");
    text.append("rem JAVA_OPTS sets JVM options").append("\r\n");
    text.append("\r\n");
    text.append("set JAVA_CMD=java").append("\r\n");
    text.append("if NOT \"%JAVA_HOME%\"==\"\" set JAVA_CMD=%JAVA_HOME%\\bin\\java").append("\r\n");
    text.append("set CONF=conf").append("\r\n");
    text.append("set LIBS=").append(libpathWin).append("\r\n");
    text.append("echo \"%JAVA_CMD%\" %JAVA_OPTS% -cp \"%CONF%;%LIBS%\" Launcher").append("\r\n");
    text.append("\"%JAVA_CMD%\" %JAVA_OPTS% -cp \"%CONF%;%LIBS%\" Launcher").append("\r\n");
    text.append("@pause").append("\r\n");

    OutputStream out = null;
    try {
      out = new BufferedOutputStream(new FileOutputStream(new File(distdir, "start.bat")));
      out.write(text.toString().getBytes());
    } finally {
      try {out.close();} catch (Exception e) {}
    }

    text = new StringBuffer();
    text.append("#!/bin/sh").append("\n");
    text.append("\n");
    text.append("# You can set JAVA_HOME and JAVA_OPTS variables:").append("\n");
    text.append("# JAVA_HOME points to JVM").append("\n");
    text.append("# JAVA_OPTS sets JVM options").append("\n");
    text.append("\n");
    text.append("JAVA_CMD=java").append("\n");
    text.append("if [ ! -z \"$JAVA_HOME\" ] ; then ").append("\n");
    text.append("  JAVA_CMD=$JAVA_HOME/bin/java").append("\n");
    text.append("fi").append("\n");
    text.append("CONF=conf").append("\n");
    text.append("LIBS=").append(libpathNix).append("\n");
    text.append("echo \"$JAVA_CMD\" $JAVA_OPTS -cp \"$CONF:$LIBS\" Launcher").append("\n");
    text.append("\"$JAVA_CMD\" $JAVA_OPTS -cp \"$CONF:$LIBS\" Launcher").append("\n");

    try {
      out = new BufferedOutputStream(new FileOutputStream(new File(distdir, "start.sh")));
      out.write(text.toString().getBytes());
    } finally {
      try {out.close();} catch (Exception e) {}
    }

    System.out.println("Startup scripts created");
  }
}
