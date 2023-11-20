package affluex.school.solutions.Retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import affluex.school.solutions.Model.ModelParents;
import affluex.school.solutions.Model.ModelProfile;
import affluex.school.solutions.Model.ModelTeachers;
import affluex.school.solutions.Model.ResponseAssignmentDashboard;
import affluex.school.solutions.Model.ResponseClass;
import affluex.school.solutions.Model.ResponseHomework;
import affluex.school.solutions.Model.ResponseLeave;
import affluex.school.solutions.Model.ResponseLeaveApproval;
import affluex.school.solutions.Model.ResponseParentDashboard;
import affluex.school.solutions.Model.ResponseSection;
import affluex.school.solutions.Model.ResponseSubject;
import affluex.school.solutions.Model.lstNoticeList;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {
    @POST("MasterForApi/TeacherLogin")
    Call<ModelTeachers> getLoginTeachers(@Body JsonObject login);


    @POST("MasterForApi/AddNotice")
    Call<String> addNotice(@Body JsonObject login);

    @POST("MasterForApi/NoticeList")
    Call<lstNoticeList> noticeList(@Body JsonObject login);

    @POST("MasterForApi/Login")
    Call<ModelParents> loginParents(@Body JsonObject login);


    @POST("MasterForApi/GetClassList")
    Call<ResponseClass> GetClassList(@Body JsonObject login);

    @POST("MasterForApi/GetSectionList")
    Call<ResponseSection> GetSectionList(@Body JsonObject login);

    @POST("MasterForApi/GetSubjectNameBySection")
    Call<ResponseSubject> GetSubjectList(@Body JsonObject login);
    @POST("MasterForApi/GetHomeworkList")
    Call<ResponseHomework> GetHomeworkList(@Body JsonObject login);
//@POST("MasterForApi/SaveHomework")
//    Call<String> SaveAsignment(@Body JsonObject login);
@Multipart
    @POST("MasterForApi/SaveHomework")
Call<String> SaveAsignment(@Part MultipartBody.Part image,
                           @Part("Fk_ClassID") RequestBody Fk_ClassID,
                           @Part("Fk_SectionID") RequestBody Fk_SectionID,
                           @Part("SubjectID") RequestBody SubjectID,
                           @Part("HomeworkDate") RequestBody HomeworkDate,
                           @Part("AddedBy") RequestBody AddedBy,
                           @Part("HomeworkBy") RequestBody HomeworkBy,
                           @Part("HomeWorkHTML") RequestBody HomeWorkHTML,
                           @Part("StudentFiles") RequestBody StudentFiles







);

    @POST("MasterForApi/PendingLeave")
    Call<ResponseLeave> GetPendingLeave(@Body JsonObject login);

    @POST("MasterForApi/TotalLeaveList")
    Call<ResponseLeave> GetTotalLeave(@Body JsonObject login);


    @POST("MasterForApi/ApprovePendingLeave")
    Call<ResponseLeaveApproval> ApproveLeave(@Body JsonObject login);


    @POST("MasterForApi/DeclinePendingLeave")
    Call<ResponseLeaveApproval> DeclineLeave(@Body JsonObject login);

    @POST("MasterForApi/GetTeacherProfile")
    Call<ModelProfile> GetProfile(@Body JsonObject login);

    @POST("MasterForApi/UpdateTeacherProfile")
    Call<String> UpdateProfile(@Body JsonObject login);

    @POST("MasterForApi/SaveAttendance")
    Call<String> SaveAttendance(@Body JsonObject login);


    @POST("MasterForApi/DashBoardDetails")
    Call<ResponseParentDashboard> GetDashboard(@Body JsonObject login);
    @POST("MasterForApi/HomeWork")
    Call<ResponseAssignmentDashboard> GetAssignmentParent(@Body JsonObject login);

}