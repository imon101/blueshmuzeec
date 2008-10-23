/**
 * 
 */
package edu.its.ta.control;

import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 * @author satria
 *
 */
public class RMSControl {
    
    private String recordName = "";
    
    private RecordStore rs = null;
    
    public RMSControl(String rsName) {
        recordName = rsName;
        
        try {
            rs = RecordStore.openRecordStore(recordName,true);
        } catch (RecordStoreException rse) {
            System.err.println(rse.toString());
        }
    }
    
    public int addStringData(String data) {
        byte[] dataByte = data.getBytes();
        
        int recId = 0;
        try {
            recId = rs.addRecord(dataByte, 0, dataByte.length);
        } catch (RecordStoreNotOpenException rsnoE) {
            
        } catch (RecordStoreFullException rsfE) {
            
        } catch (RecordStoreException rsE) {
            
        }
        return recId;
    }
    
    public byte[] readStringData(int recordId) {
        byte[] dataByte = null;
        
        try {
            dataByte = rs.getRecord(recordId);
        } catch (RecordStoreException rsE) {
            
        }
        
        return dataByte;
    }
    
    public void updateStringData(String data, int recordId) {
        byte[] dataByte = data.getBytes();
        
        try {
            rs.setRecord(recordId, dataByte, 0, dataByte.length);
        } catch (RecordStoreNotOpenException rsnoE) {
            
        } catch (RecordStoreFullException rsfE) {
            
        } catch (RecordStoreException rsE) {
            
        }
    }
    
    public void deleteStringData(int recordId) {
        try {
            rs.deleteRecord(recordId);
        } catch (InvalidRecordIDException iriE) {
            iriE.printStackTrace();
        } catch (RecordStoreException rsE) {
            rsE.printStackTrace();
        }
    }
    
    public void closeRecordStore() {
        try {
            rs.closeRecordStore();
        } catch(RecordStoreException rsE) {
            rsE.printStackTrace();
        }
    }
    
    public RecordEnumeration getEnumerationRecordStore() {
        RecordEnumeration re = null;
        try {
            re = rs.enumerateRecords(null, null, true);
        } catch(RecordStoreNotOpenException rsnoE) {
            
        }
        return re;
    }
    
    public boolean isDataExist(String dt) {
        final String fltr = dt;
        RecordEnumeration re = null;
        try {
            re = rs.enumerateRecords(new RecordFilter() {
                public boolean matches(byte[] arg0) {
                    if (arg0 != null) {
                        String data = new String(arg0);
                        if (data.equalsIgnoreCase(fltr)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }                
            }, null, true);
            if (re.numRecords() > 0) {
                return true;
            } else {
                return false;
            }
        } catch(RecordStoreNotOpenException rsnoE) {
            System.out.println("RSException: " + rsnoE);
            return true;
        }
    }
}