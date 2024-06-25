package affluex.school.solutions.Network;
import com.google.gson.JsonObject;
import affluex.school.solutions.ExamModel.ClassListResponse;
import affluex.school.solutions.ExamModel.Section.SectionResponse;
import affluex.school.solutions.ExamModel.Subject.SubjectResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface Api {

    @POST("MasterForApi/GetClassList")
    Call<ClassListResponse> getClassData(@Body  JsonObject request);

//    @POST("MasterForApi/GetSectionList")
//    Call<SectionResponse> getSectionData(@Body SectionRequest request);

    @POST("MasterForApi/GetSectionList") // Replace with your actual endpoint
    Call<SectionResponse> getSectionList(@Body JsonObject body);

    @POST("GetSubjectNameBySection")
    Call<SubjectResponse> getSubjectList(@Body JsonObject request);


}
