/*
 * Copyright 2016 Tom Miranda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tmiranda.cd;

import sagex.api.Configuration;

/**
 *
 * @author Default
 */
public class Log {

    /**
     * Possible values for the LogLevel.
     */

    public static final String  PROPERTY_LOGLEVEL = "cd/loglevel";
    public static final int     LOGLEVEL_NONE       = 100;
    public static final int     LOGLEVEL_ERROR      = 75;
    public static final int     LOGLEVEL_WARN       = 50;
    public static final int     LOGLEVEL_TRACE      = 25;
    public static final int     LOGLEVEL_VERBOSE    = 10;
    public static final int     LOGLEVEL_ALL        = 0;

    private static int CurrentLogLevel = LOGLEVEL_WARN;

    private static Log instance = new Log();

    /*
     * Private constructor.  Only let getInstance return a valid instance.
     */
    private Log() {};

    /**
     * Gets the one and only instance for the Log class.
     * <p>
     * @return  The instance for the Logger.
     */
    public static Log getInstance() {
        if (instance==null)
            instance = new Log();
        return instance;
    }

    /**
     * Destroy the Log class.
     */
    public void destroy() {
        instance = null;
    }

    /**
     * Writes a string to the logfile if the level indicated is at least at the current LogLevel.
     * <p>
     * @param level The LogLevel at wich this message should be written.
     * @param s     The String to write.
     */
    public void write(int level, String s) {
        if (level >= CurrentLogLevel)
            System.out.println("CD: " + s);

        //if (level == Log.LOGLEVEL_ERROR) {
        //    Exception e = new Exception();
        //    e.printStackTrace();
        //}
    }

    /**
     * Set the current LogLevel.
     * <p>
     * @param NewLevel  The new Loglevel.
     */
    public void SetLogLevel(Integer NewLevel) {
        CurrentLogLevel = NewLevel;
        Configuration.SetServerProperty(PROPERTY_LOGLEVEL, NewLevel.toString());
    }

    /**
     * Gets the current LogLevel.
     * <p>
     * @return  The current LogLevel.
     */
    public int GetLogLevel() {
        return CurrentLogLevel;
    }

    public void Write(int level, String s) {
        if (level >= CurrentLogLevel)
            System.out.println("CD STV:"+s);
    }

}
