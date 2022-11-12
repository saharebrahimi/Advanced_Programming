
package ir.aut.ceit.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChatHistory {
    private static JSONArray messages=new JSONArray();
    public static JSONObject finalmessage=new JSONObject();

    public ChatHistory(String text, String name) {
        JSONObject json = new JSONObject();
        json.put("text", text);
        messages.put(json);
        finalmessage.put("name", name);

    }

    public static void finalJson() {
        finalmessage.put("type", messages);

    }
}



