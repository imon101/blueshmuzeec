package edu.its.ta.control;

import java.util.Enumeration;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

public class FileControl {

    private String currentDirectory = "/";
    private boolean isRoot = false;

    private Enumeration CurrentDirEnum = null;
    private FileConnection currDirFC = null;

    public FileControl() {
        
    }

    public synchronized void getDirContent(String currentDir) {
        currentDirectory = currentDir;
        try {
            if (currentDirectory.equals("/")) {
                CurrentDirEnum = FileSystemRegistry.listRoots();
                isRoot = true;
            } else {
                //currDirFC = (FileConnection) Connector.open("file://localhost/" + currentDir);
                currDirFC = (FileConnection) Connector.open("file:///" + currentDir);
                if (currDirFC != null) {
                    CurrentDirEnum = currDirFC.list();
                    isRoot = false;
                }
            }
        } catch (SecurityException e) {
            System.out.println("Security exception");
            CurrentDirEnum = null;
        } catch (Exception ex) {
            System.out.println("FileSystemThread.run- Exception:" + ex.toString());
        } finally {
            try {
                if (currDirFC != null) {
                    currDirFC.close();
                }
            } catch (Exception e) {
                System.out.println("FileSystemThread.run Exception " + e.toString());
            }
            currDirFC = null;
        }
    }

    public String getCurrentDirectory() {
        return currentDirectory.equals("/") ? "" : currentDirectory;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public Enumeration getCurrentDirEnum() {
        return CurrentDirEnum;
    }
}
