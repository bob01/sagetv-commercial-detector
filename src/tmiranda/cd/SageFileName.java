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
 * @author Tom Miranda
 */
public class SageFileName {
    String FileName = null;

    public SageFileName(String N) {
        FileName = (N==null ? "ERROR" : N);
    }

    public String getExtension() {
        String[] NameExt = FileName.split("\\.");

        String MediaFileExt = null;

        if (NameExt.length == 1) {
            MediaFileExt = "";
        } else if(NameExt.length == 2) {
            // Most common case: "filename.ext"
            MediaFileExt = NameExt[1];
        } else {
            int lastPos = FileName.lastIndexOf(".");
            MediaFileExt = lastPos == -1 ? "" : FileName.substring(lastPos+1);
        }

        return MediaFileExt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SageFileName other = (SageFileName) obj;
        if ((this.FileName == null) ? (other.FileName != null) : !this.FileName.equals(other.FileName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.FileName != null ? this.FileName.hashCode() : 0);
        return hash;
    }
    
}
