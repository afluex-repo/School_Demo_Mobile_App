package affluex.school.solutions.ExamModel;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class MyClass {

    private static final String PREFS_NAME = "MyPrefsFile";

    public static void saveIdsFromJson(Context context, String jsonString) {
        try {
            // Parse JSON string
            JSONObject jsonObject = new JSONObject(jsonString);

            // Extract Fk_ClassID, Fk_SectionID, and TeacherID
            String fkClassId = jsonObject.getString("Fk_ClassID");
            String fkSectionId = jsonObject.getString("Fk_SectionID");
            String teacherId = jsonObject.getString("TeacherID");

            // Save values in SharedPreferences
            SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.putString("Fk_ClassID", fkClassId);
            editor.putString("Fk_SectionID", fkSectionId);
            editor.putString("TeacherID", teacherId);
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getFkClassID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString("Fk_ClassID", null);
    }

    public static String getFkSectionID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString("Fk_SectionID", null);
    }

    public static String getTeacherID(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString("TeacherID", null);
    }
}
