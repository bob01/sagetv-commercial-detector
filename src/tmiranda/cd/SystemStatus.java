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

import java.util.List;

import sagex.api.Configuration;
import sagex.api.MediaFileAPI;
import sagex.api.ShowAPI;

/**
 *
 * @author Tom Miranda.
 */
public class SystemStatus {

    /**
     * Constructor.
     */
    private SystemStatus() {}

    private static SystemStatus instance = new SystemStatus();

    public static SystemStatus getInstance() {
        if (instance==null)
            instance = new SystemStatus();
        return instance;
    }
    
    public void destroy() {
        instance = null;
    }
    
    /**
     * Prints to the Sage logfile the complete system status.
     */
    public void printSystemStatus() {
        printNumberRunning();
        printJobsRunning();
        printJobQueue();
    }

    /**
     * Prints to the Sage logfile details about the jobs that are queued.
     */
    public void printJobQueue() {
        List<QueuedJob> Jobs = ComskipManager.getInstance().readQueuedJobs();

        if (Jobs==null || Jobs.isEmpty()) {
            System.out.println("CD: SystemStatus: No jobs in queue.");
            return;
        }

        System.out.println("CD: SystemStatus: Jobs in queue = " + Jobs.size());

        for (QueuedJob Job : Jobs) {

            Object MediaFile = Job.getMediaFile();

            if (MediaFile==null) {
                System.out.println("CD: SystemStatus:   null MediaFile.");
            } else {
                String Title = ShowAPI.GetShowTitle(MediaFile);
                String Episode = ShowAPI.GetShowEpisode(MediaFile);
                System.out.println("CD: SystemStatus:   Title : Episode = " + Title + " : " + Episode);
            }

            // Print all the file names.
            List<String> FileNames = Job.getFileName();

            if (FileNames==null || FileNames.isEmpty()) {
                System.out.println("CD: SystemStatus:   null FileNames.");
            } else {
                for (String Name : FileNames) {
                    System.out.println("CD: SystemStatus:   FileName = " + Name);
                }
            }

            //System.out.println("CD: SystemStatus:   JobHasFailed = " + Job.getJobHasFailed());
        }
    }

    /**
     * Prints to the Sage logfile the number of jobs currently running.
     */
    public void printNumberRunning() {
        Integer N = ComskipManager.getInstance().getNumberRunning();
        System.out.println("CD: SystemStatus: Number running = " + N.toString());
    }

    /**
     * Prints to the Sage logfile details about the jobs that are currently running.
     */
    public void printJobsRunning() {
        List<ComskipJob> RunningJobs = ComskipManager.getInstance().getRunningJobs();

        if (RunningJobs==null || RunningJobs.isEmpty()) {
            System.out.println("CD: SystemStatus: No jobs running.");
            return;
        }

        System.out.println("CD: SystemStatus: Jobs running = " + RunningJobs.size());

        for (ComskipJob Job : RunningJobs) {
            Integer MediaFileID = Job.getMediaFileID();

            System.out.println("CD: SystemStatus:   MediaFileID = " + MediaFileID.toString());

            Object MediaFile = MediaFileAPI.GetMediaFileForID(MediaFileID);

            if (MediaFile==null) {
                System.out.println("CD: SystemStatus:   null MediaFile.");
                continue;
            }

            String Title = ShowAPI.GetShowTitle(MediaFile);
            String Episode = ShowAPI.GetShowEpisode(MediaFile);
            System.out.println("CD: SystemStatus:   Title : Episode = " + Title + " : " + Episode);
        }
    }

    /**
     * Prints to the Sage logfile the time ratio property string.
     */
    public void printTimeRatios() {
        String S = Configuration.GetServerProperty(plugin.PROPERTY_TIME_RATIOS, plugin.PROPERTY_DEFAULT_TIME_RATIOS);
        System.out.println("CD: SystemStatus: Time Ratios = " + S);
    }

    public void printNumberOfOrphans() {
        Integer N = ComskipManager.getInstance().countAllOrphans();
        System.out.println("CD: SystemStatus: Number of orphans = " + N.toString());
    }

}
