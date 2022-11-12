package ir.aut.ceit.model;


import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class WriteToFile {
    public void write(JSONObject object)
    {
        String path = "C:\\Users\\ASUS\\Desktop\\New folder(14)\\server";

        ObjectOutputStream outputStream = null;
        try{
            outputStream = new ObjectOutputStream(new FileOutputStream(path));
            System.out.println("Start Writings");
            outputStream.writeObject(object);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            System.err.println("Error: " + e);
        }
    }
}
