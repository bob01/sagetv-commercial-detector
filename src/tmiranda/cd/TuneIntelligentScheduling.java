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

import java.util.ArrayList;
import java.util.List;

import sagex.api.Global;
import sagex.api.Utility;

/**
 *
 * @author Tom Miranda.
 */
public class TuneIntelligentScheduling {

    private List<Object> SampleMediaFiles = null;
    private boolean isRunning = false;
    private boolean recScheduleHasChanged = false;
    private InitISTuner initISTuner = null;

    public static final String THIRTY_MINUTE_FILE = "CD-30.mpg";
    public static final String SIXTY_MINUTE_FILE = "CD-60.mpg";
    
    public TuneIntelligentScheduling() {
        SampleMediaFiles = new ArrayList<Object>();
        isRunning = false;
        recScheduleHasChanged = false;
        initISTuner = null;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isInitialized() {
        return (SampleMediaFiles==null ? false : true);
    }

    public boolean isInitializerRunning() {
        return (initISTuner == null ? false : true);
    }

    public boolean isSageRecording() {
        Object[] MediaFiles = Global.GetCurrentlyRecordingMediaFiles();
        return (MediaFiles==null || MediaFiles.length==0 ? false : true);
    }

    public boolean willSageBeRecording(long stopTime) {
        Object[] MediaFiles = Global.GetScheduledRecordingsForTime(Utility.Time(), stopTime);
        return (MediaFiles==null || MediaFiles.length==0 ? false : true);
    }

    public boolean isComskipping() {
        return (ComskipManager.getInstance().getNumberRunning()==0 ? false : true);
    }

    public boolean isAnythingQueued() {
        return (ComskipManager.getInstance().getQueueSize(false)==0 ? false : true);
    }

    public void recScheduleHasChanged() {
        recScheduleHasChanged = true;
    }

    /**
     * Starts the initialiation process.  We need a separate process for this because initialization can take
     * a lot of time due to the fact that we need to copy files.
     */
    public void startInitialize() {
        Log.getInstance().write(Log.LOGLEVEL_TRACE, "TIS.startInitialize: Starting initialization process.");
        initISTuner = new InitISTuner();
        new Thread(initISTuner).start();
        return;
    }
}

class InitISTuner implements Runnable {
    @Override
    public void run() {

        Log.getInstance().write(Log.LOGLEVEL_TRACE, "TIS: InitISTuner begin.");

        if (!createThirtyMinuteFile()) {
            Log.getInstance().write(Log.LOGLEVEL_TRACE, "TIS.InitISTuner: Failed to make 30 minute test file.");
            return;
        }

        if (!createSixtyMinuteFile()) {
            Log.getInstance().write(Log.LOGLEVEL_TRACE, "TIS.InitISTuner: Failed to make 60 minute test file.");
            return;
        }
    }

    private boolean createThirtyMinuteFile() {
        return true;
    }

    private boolean createSixtyMinuteFile() {
        return true;
    }
}
