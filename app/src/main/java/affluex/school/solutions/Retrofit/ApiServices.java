package affluex.school.solutions.Retrofit;
import com.google.gson.JsonObject;

import affluex.school.solutions.ExamModel.Exam.ExamResponse;
import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Model.ModelParents;
import affluex.school.solutions.Model.ModelProfile;
import affluex.school.solutions.Model.ModelTeachers;
import affluex.school.solutions.Model.REsponseFeeReport;
import affluex.school.solutions.Model.ResponseAssignmentDashboard;
import affluex.school.solutions.Model.ResponseAttendnace;
import affluex.school.solutions.Model.ResponseClass;
import affluex.school.solutions.Model.ResponseComplain;
import affluex.school.solutions.Model.ResponseHomework;
import affluex.school.solutions.Model.ResponseLeave;
import affluex.school.solutions.Model.ResponseLeaveApproval;
import affluex.school.solutions.Model.ResponseLeaveParent;
import affluex.school.solutions.Model.ResponseParentDashboard;
import affluex.school.solutions.Model.ResponseParentNotice;
import affluex.school.solutions.Model.ResponseParentsProfile;
import affluex.school.solutions.Model.ResponseSalary;
import affluex.school.solutions.Model.ResponseSalarySlip;
import affluex.school.solutions.Model.ResponseSection;
import affluex.school.solutions.Model.ResponseStudentAttendance;
import affluex.school.solutions.Model.ResponseStudentDetails;
import affluex.school.solutions.Model.ResponseSubject;
import affluex.school.solutions.Model.ResponseSyllabus;
import affluex.school.solutions.Model.ResponseTimetable;
import affluex.school.solutions.Model.lstNoticeList;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;



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
    Call<ResponseClass> GetClassList(@Body JsonObject login); //

    @POST("MasterForApi/GetSectionList")
    Call<ResponseSection> GetSectionList(@Body JsonObject login); //

    @POST("MasterForApi/GetSubjectNameBySection")//
    Call<ResponseSubject> GetSubjectList(@Body JsonObject login);

    @POST("MasterForApi/GetHomeworkList")
    Call<ResponseHomework> GetHomeworkList(@Body JsonObject login);


    @POST("MasterForApi/EmployeeSalarySlipBy")
    Call<ResponseSalary> EmployeeSalarySlipBy(@Body JsonObject login);

    @POST("MasterForApi/PrintSalarySlip")
    Call<ResponseSalarySlip> PrintSalarySlip(@Body JsonObject login);
@POST("MasterForApi/SaveHomework")
    Call<String> SaveAsignment(@Body RequestBody login);
//@Multipart
//    @POST("MasterForApi/SaveHomework")
//Call<String> SaveAsignment(@Part MultipartBody.Part image,
//                           @Part("Fk_ClassID") RequestBody Fk_ClassID,
//                           @Part("Fk_SectionID") RequestBody Fk_SectionID,
//                           @Part("SubjectID") RequestBody SubjectID,
//                           @Part("HomeworkDate") RequestBody HomeworkDate,
//                           @Part("AddedBy") RequestBody AddedBy,
//                           @Part("HomeworkBy") RequestBody HomeworkBy,
//                           @Part("HomeWorkHTML") RequestBody HomeWorkHTML,
//                           @Part("StudentFiles") RequestBody StudentFiles
//
//
//
//
//
//
//
//);

    @POST("MasterForApi/PendingLeave")
    Call<ResponseLeave> GetPendingLeave(@Body JsonObject login);

    @POST("MasterForApi/TotalLeaveList")
    Call<ResponseLeave> GetTotalLeave(@Body JsonObject login);


    @POST("MasterForApi/LeaveList")
    Call<ResponseLeaveParent> LeaveList(@Body JsonObject login);


    @POST("MasterForApi/ApprovePendingLeave")
    Call<ResponseLeaveApproval> ApproveLeave(@Body JsonObject login);


    @POST("MasterForApi/DeclinePendingLeave")
    Call<ResponseLeaveApproval> DeclineLeave(@Body JsonObject login);

    @POST("MasterForApi/GetTeacherProfile")
    Call<ModelProfile> GetProfile(@Body JsonObject login);

    @POST("MasterForApi/UpdateTeacherProfile")
    Call<CommonResponse> UpdateProfile(@Body JsonObject login);

    @POST("MasterForApi/SaveAttendance")
    Call<CommonResponse> SaveAttendance(@Body RequestBody login);

@POST("MasterForApi/SavePunchOutAttendance")
    Call<CommonResponse> SavePunchOutAttendance(@Body JsonObject login);


    @POST("MasterForApi/DeleteHomeWork")
    Call<CommonResponse> DeleteHomeWork(@Body JsonObject login);

@POST("MasterForApi/DeleteNotice")
    Call<CommonResponse> DeleteNotice  (@Body JsonObject login);

@POST("MasterForApi/ChangePassword")
    Call<CommonResponse> ChangePassword    (@Body JsonObject login);

@POST("MasterForApi/UpdateNotice ")
    Call<CommonResponse> UpdateNotice     (@Body JsonObject login);

    @POST("MasterForApi/GetOtp ")
    Call<CommonResponse> GetOtp     (@Body JsonObject login);

@POST("MasterForApi/GetPassword  ")
    Call<CommonResponse> GetPassword      (@Body JsonObject login);
@POST("MasterForApi/ApplyLeave  ")
    Call<CommonResponse> ApplyLeave      (@Body JsonObject login);

    @POST("MasterForApi/DashBoardDetails")
    Call<ResponseParentDashboard> GetDashboard(@Body JsonObject login);

    @POST("MasterForApi/GetAttenndaceList")
    Call<ResponseAttendnace> GetAttenndaceList(@Body JsonObject login);
    @POST("MasterForApi/HomeWork")
    Call<ResponseAssignmentDashboard> GetAssignmentParent(@Body JsonObject login);
 @POST("MasterForApi/StudentAttendanceFilter")
    Call<ResponseStudentAttendance> StudentAttendanceFilter(@Body JsonObject login);

    @POST("MasterForApi/GetComplains")
    Call<ResponseComplain> GetComplains(@Body JsonObject login);

    @POST("MasterForApi/Complain")
    Call<CommonResponse> Complain(@Body JsonObject login);

    @POST("MasterForApi/FeeReport")
    Call<REsponseFeeReport> FeeReport(@Body JsonObject login);
 @POST("MasterForApi/GetNoticeForParent")
    Call<ResponseParentNotice> GetNoticeForParent(@Body JsonObject login);

@POST("MasterForApi/TimeTable")
    Call<ResponseTimetable> TimeTable(@Body JsonObject login);

@POST("MasterForApi/Sylabus")
    Call<ResponseSyllabus> Sylabus(@Body JsonObject login);

@POST("MasterForApi/GetDashBoardDetailsofStudent")
    Call<ResponseStudentDetails> GetDashBoardDetailsofStudent(@Body JsonObject login);

@POST("MasterForApi/ProfileDetailsParent")
    Call<ResponseParentsProfile> ProfileDetailsParent(@Body JsonObject login);

@POST("MasterForApi/UpdateParentProfile")
    Call<CommonResponse> UpdateParentProfile(@Body JsonObject login);


    @POST(" MasterForApi/GetExamType")
    Call<ExamResponse> getExamDetails();







}