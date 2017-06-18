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

import sagex.api.Global;

/**
 *
 * @author Tom Miranda
 */
public class JobProfiler extends TimerTask {

    private ComskipJob Job = null;
    
    public JobProfiler(ComskipJob CallingJob) {
        Job = CallingJob;
    }

    /*
     * When called this process counts how many comskip jobs are running, how many recordings are in
     * progress, and reports back to the calling ComskipJob.
     */
    @Override
    public void run() {
        int NumberRunning = ComskipManager.getInstance().getNumberRunning();

        int NumberRecording = 0;

        Object[] MediaFilesRecording = Global.GetCurrentlyRecordingMediaFiles();

        if (MediaFilesRecording!=null && MediaFilesRecording.length>0) {
            NumberRecording = MediaFilesRecording.length;
        }

        JobSnapshot Snapshot = new JobSnapshot(NumberRunning, NumberRecording);

        Job.addSnapshot(Snapshot);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JobProfiler other = (JobProfiler) obj;
        if (this.Job != other.Job && (this.Job == null || !this.Job.equals(other.Job))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.Job != null ? this.Job.hashCode() : 0);
        return hash;
    }
      
}
