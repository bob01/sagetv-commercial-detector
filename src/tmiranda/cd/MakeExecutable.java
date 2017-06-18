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

import java.io.IOException;

import sagex.api.Global;

/**
 *
 * @author Tom Miranda.
 */
public class MakeExecutable implements Runnable {
 
    private String fileName = null;
    
    MakeExecutable() {
        fileName = "comskip";
    }
    
    MakeExecutable(String name) {
        fileName = name;
    }  
    
    public static void init() {
        new MakeExecutable().makeExecutable();
    }
    
    public static void init(String f) {
        new MakeExecutable(f).makeExecutable();
    }    
    
    public void makeExecutable() {
        
        // No need to do this if we are running on Windows.
        if (Global.IsWindowsOS()) {
            Log.getInstance().write(Log.LOGLEVEL_TRACE, "MakeExecutable.makeExecutable: Running on Windows, nothing to do.");
            return;
        } 
        
        Log.getInstance().write(Log.LOGLEVEL_TRACE, "MakeExecutable.makeExecutable: About to run chmod on " + fileName);
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        
        Process process = null;
        
        //String commandLine = "chmod +x " + fileName;
        String commandLine[] = new String[] {"/bin/chmod", "ug+x", fileName};

        //Log.getInstance().write(Log.LOGLEVEL_TRACE, "MakeExecutable.run: Command " + commandLine);      
        
        try {process = Runtime.getRuntime().exec(commandLine); } catch (IOException e) {
            Log.getInstance().write(Log.LOGLEVEL_ERROR, "MakeExecutable.run: Exception running chmod " + e.getMessage());
            return;
        }  
        
        // int status = 0;
        // try {status=process.waitFor(); } catch (InterruptedException e) {
        try {process.waitFor(); } catch (InterruptedException e) {
            Log.getInstance().write(Log.LOGLEVEL_WARN, "MakeExecutable.run: chmod was interrupted " + e.getMessage());
            return;
        }
        
        Log.getInstance().write(Log.LOGLEVEL_WARN, "MakeExecutable.run: chmod completed.");             
    }
    
    public void OLDrun() {
       
        Process process = null;
        
        String commandLine = "chmod +x " + fileName;

        Log.getInstance().write(Log.LOGLEVEL_TRACE, "MakeExecutable.run: Command " + commandLine);      
        
        try {process = Runtime.getRuntime().exec(commandLine); } catch (IOException e) {
            Log.getInstance().write(Log.LOGLEVEL_ERROR, "MakeExecutable.run: Exception running chmod " + e.getMessage());
            return;
        }  
        
        //int status = 0;
        //try {status=process.waitFor(); } catch (InterruptedException e) {
        try {process.waitFor(); } catch (InterruptedException e) {
            Log.getInstance().write(Log.LOGLEVEL_WARN, "MakeExecutable.run: chmod was interrupted " + e.getMessage());
            return;
        }
        
        Log.getInstance().write(Log.LOGLEVEL_WARN, "MakeExecutable.run: chmod completed.");
    }    
}
