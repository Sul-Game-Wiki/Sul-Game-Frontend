package info.sul_game.utils.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import info.sul_game.activity.MainActivity
import info.sul_game.activity.SignUpActivity
import info.sul_game.activity.SignUpConfirmationActivity
import info.sul_game.config.RetrofitClient
import info.sul_game.databinding.DialogWarningBinding
import info.sul_game.model.MemberResponse
import info.sul_game.utils.TokenUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

class WarningDialog (private val context : AppCompatActivity) {

    private lateinit var binding : DialogWarningBinding
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var nickname: String
    private lateinit var birthDate: LocalDate
    private lateinit var university: String
    private var isUniversityVisible: Boolean = false

    fun show(nickname: String, birthDate: LocalDate, university: String, isUniversityVisible: Boolean) {
        binding = DialogWarningBinding.inflate(LayoutInflater.from(context))

        this.nickname = nickname
        this.birthDate = birthDate
        this.university = university
        this.isUniversityVisible = isUniversityVisible

        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        // 확인 버튼 동작
        binding.btnSubmitWarningDialog.setOnClickListener {
            dlg.dismiss()
            Log.d("술겜위키", "버튼 클릭")
            completeSignUp()
            context.startActivity(Intent(context, SignUpConfirmationActivity::class.java))
            context.finish()
        }

        // 취소 버튼 동작
        binding.btnCancelWarningDialog.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }

    private val _member = MutableLiveData<MemberResponse>()
    private val _error = MutableLiveData<String>()

    private fun completeSignUp(){
        Log.d("술겜위키", "토큰값 : ${TokenUtil().getRefreshToken(context)}")
        val token = "Bearer ${TokenUtil().getRefreshToken(context)}"

        val memberId = createRequestBody("")
        val nickname = createRequestBody(this.nickname)
        val birthDate = createRequestBody(this.birthDate.format(
            DateTimeFormatter.ISO_DATE))
        val university = createRequestBody(this.university)
        val isUniversityVisible = createRequestBody(this.isUniversityVisible.toString())
        val isNotificationEnabled = createRequestBody("")
        val pageNumber = createRequestBody("")
        val pageSize = createRequestBody("")
        val expRank = createRequestBody("")
        val expRankPercentile = createRequestBody("")
        val nextLevelExp = createRequestBody("")
        val remainingExpForNextLevel = createRequestBody("")
        val progressPercentToNextLevel = createRequestBody("")
        val rankChange = createRequestBody("")

        RetrofitClient.memberApiService
            .checkNickName(
                token,
                memberId,
                nickname,
                birthDate,
                university,
                isUniversityVisible,
                isNotificationEnabled,
                pageNumber,
                pageSize,
                expRank,
                expRankPercentile,
                nextLevelExp,
                remainingExpForNextLevel,
                progressPercentToNextLevel,
                rankChange
            ).enqueue(object :
                Callback<MemberResponse> {
                override fun onResponse(
                    call: Call<MemberResponse>,
                    response: Response<MemberResponse>
                ) {
                    if (response.isSuccessful) {
                        _member.value = response.body()  // 성공적으로 데이터를 받으면 LiveData에 저장
                        Log.d("술겜위키", "회원가입 성공!")
                        Log.d("술겜위키", "값은 : ${_member.value}")
                    } else {
                        _error.value = "Error: ${response.code()}"  // 에러 발생 시 처리
                        Log.e("술겜위키", "응답 실패: ${response.code()} - ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(
                    call: Call<MemberResponse>,
                    t: Throwable
                ) {
                    _error.value = "Failure: ${t.message}"  // 네트워크 오류 처리
                    Log.d("술겜위키", "오류")
                }
            })
    }

    private fun createRequestBody(value: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, value)
    }
}