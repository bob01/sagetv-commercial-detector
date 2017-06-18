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

import java.util.TimerTask;

import sagex.api.MediaFileAPI;

/**
 *
 * @author Tom Miranda
 */
public class MonitorClient extends TimerTask {

    @Override
    public void run() {
        Log.getInstance().write(Log.LOGLEVEL_VERBOSE, "MonitorClient: Looking for work.");

        // Read the first item off the queue.
        String IDString = CSC.getInstance().getFirstStatus(CSC.STATUS_QUEUE);
        
        // null or empty means there are no more items in the queue.
        while (IDString!=null && !IDString.isEmpty()) {

            // Convert the String to an int.
            int ID = 0;
            try {ID = Integer.parseInt(IDString);}
            catch (NumberFormatException e) {
                ID=0;
                Log.getInstance().write(Log.LOGLEVEL_ERROR, "MonitorClient: Malformed ID " + IDString);
            }

            // Get the corresponding MediaFile.
            Object MediaFile = MediaFileAPI.GetMediaFileForID(ID);
            if (MediaFile==null) {
                Log.getInstance().write(Log.LOGLEVEL_ERROR, "MonitorClient: null MediaFile.");
            } else {
                CommercialDetectorMediaFile CDMediaFile = new CommercialDetectorMediaFile(MediaFile);
                CDMediaFile.queue();
                Log.getInstance().write(Log.LOGLEVEL_TRACE, "MonitorClient: Queued " + MediaFileAPI.GetMediaTitle(MediaFile));
            }

            // Get the next item off the queue.
            IDString = CSC.getInstance().getFirstStatus(CSC.STATUS_QUEUE);
        }

        Log.getInstance().write(Log.LOGLEVEL_VERBOSE, "MonitorClient: Finished.");
    }
}
