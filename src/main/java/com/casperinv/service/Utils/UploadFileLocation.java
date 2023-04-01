package com.casperinv.service.Utils;

public class UploadFileLocation {
    public static String getLocation(){
        return UploadFileLocation.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }
}
