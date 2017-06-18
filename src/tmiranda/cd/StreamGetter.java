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

/**
 *
 * @author Tom Miranda.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class StreamGetter extends Thread {

    InputStream is;
    String type;

    StreamGetter(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    @Override
    public void run() {

        InputStreamReader isr = null;
        BufferedReader br = null;

        String LastLine = null;
        int DuplicateLines = 0;

        try {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line=null;
            while ((line = br.readLine()) != null) {
                if (LastLine!=null && line.equalsIgnoreCase(LastLine)) {
                    DuplicateLines++;
                } else {
                    LastLine = line;
                    if (type.equalsIgnoreCase("error")) {
                        Log.getInstance().write(Log.LOGLEVEL_TRACE, "StreamGetter: stderr: " + line);
                    } else {
                        Log.getInstance().write(Log.LOGLEVEL_TRACE, "StreamGetter: stdout: " + line);
                    }
                }
            }
        } catch (IOException ioe) {
            Log.getInstance().write(Log.LOGLEVEL_ERROR, "StreamGetter: Error-Exception " + type + ":" + ioe.getMessage());
            ioe.printStackTrace();
        } finally {
            try {br.close(); isr.close(); } catch (IOException e) {
                Log.getInstance().write(Log.LOGLEVEL_ERROR, "StreamGetter: Exception closing " + e.getMessage());
            }
            if (DuplicateLines != 0)
                Log.getInstance().write(Log.LOGLEVEL_WARN, "StreamGetter: Duplicate lines = " + DuplicateLines + ":" + LastLine);
        }
    }
}