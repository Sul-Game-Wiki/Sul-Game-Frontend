package info.sul_game.utils.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import info.sul_game.R
import info.sul_game.databinding.DialogUniversityBinding
import info.sul_game.recyclerview.UniversitySignUpItem
import info.sul_game.recyclerview.UniversitySignUpAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.text.Collator
import java.util.Locale

class ModalBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogUniversityBinding
    private lateinit var universityNames: List<String>
    private lateinit var sortedUniversitySignUpItemNames: ArrayList<UniversitySignUpItem>
    var onUniversitySelected: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DialogUniversityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadUniversityNames()
        sortedUniversitySignUpItemNames = ArrayList()
        val groupedUniversities = sortAndGroupUniversities(universityNames)

        groupedUniversities.forEach { name ->
            if (name.length == 1 && name[0] in 'ㄱ'..'ㅎ') {
                sortedUniversitySignUpItemNames.add(UniversitySignUpItem(0, name)) // Header type
            } else {
                sortedUniversitySignUpItemNames.add(UniversitySignUpItem(1, name)) // Item type
            }
        }

        val adapter = UniversitySignUpAdapter(ArrayList(sortedUniversitySignUpItemNames)) //수정됨
        adapter.onItemClick = { selectedUniversity ->
            onUniversitySelected?.invoke(selectedUniversity)
            dismiss() // Close the dialog
        }

        binding.rvUniversityUniversityDialog.adapter = adapter
        binding.rvUniversityUniversityDialog.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // Add a TextWatcher to the search input field to filter results as the user types
        binding.etSearchUniversityDialog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                Log.d("술겜위키", "텍스트 변환")
                if (query.isEmpty()) {
                    // 검색어가 없으면 전체 리스트 다시 보여주기
                    Log.d("술겜위키", "검색값 없음")
                    adapter.updateList(ArrayList(sortedUniversitySignUpItemNames)) // 전체 리스트 다시 표시 //수정됨
                } else {
                    Log.d("술겜위키", "query : $query")
                    val filteredList = filterUniversityList(query)
                    Log.d("술겜위키", "검색된 리스트: $filteredList")
                    adapter.updateList(filteredList) // Update the adapter with the filtered list
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // 필터링 함수에서 헤더(초성) 없이 대학 이름만 필터링
    private fun filterUniversityList(query: String): ArrayList<UniversitySignUpItem> {
        return ArrayList(sortedUniversitySignUpItemNames.filter { item -> // 수정됨
            item.state == 1 && item.text.contains(query, ignoreCase = true)
        }).also {
            Log.d("술겜위키", "필터링된 결과: $it") // 필터된 항목을 로그로 출력
        }
    }

    private fun loadUniversityNames() {
        val inputStream = resources.openRawResource(R.raw.university)
        val jsonFile = inputStream.bufferedReader().use { it.readText() }

        val jsonObject = JSONObject(jsonFile)
        val recordsArray: JSONArray = jsonObject.getJSONArray("records")

        universityNames = mutableListOf<String>().apply {
            for (i in 0 until recordsArray.length()) {
                val record: JSONObject = recordsArray.getJSONObject(i)
                add(record.getString("학교명"))
            }
        }
    }

    // 대학 이름들을 정렬하고 초성 변경 시 초성을 리스트에 추가하는 함수
    private fun sortAndGroupUniversities(universityNames: List<String>): List<String> {
        // 정렬을 위해 한국어 Collator 사용
        val collator = Collator.getInstance(Locale.KOREAN)

        // 대학 이름을 초성 기준으로 정렬
        val sortedNames = universityNames.sortedWith(collator)

        // 결과를 저장할 리스트
        val result = mutableListOf<String>()
        var previousInitial = ""

        // 초성을 추출하고 그룹화
        for (name in sortedNames) {
            val initial = getInitialSound(name)

            // 초성이 이전과 다르면 리스트에 추가
            if (initial != previousInitial) {
                result.add(initial)
                previousInitial = initial
            }

            // 대학 이름 추가
            result.add(name)
        }
        return result
    }

    // 문자열에서 첫 번째 글자의 초성을 추출하는 함수
    private fun getInitialSound(text: String): String {
        val initial = text.firstOrNull() ?: return ""

        return when (initial) {
            in '가'..'깋' -> "ㄱ"
            in '나'..'닣' -> "ㄴ"
            in '다'..'딯' -> "ㄷ"
            in '라'..'맇' -> "ㄹ"
            in '마'..'밓' -> "ㅁ"
            in '바'..'빟' -> "ㅂ"
            in '사'..'싷' -> "ㅅ"
            in '아'..'잏' -> "ㅇ"
            in '자'..'짛' -> "ㅈ"
            in '차'..'칳' -> "ㅊ"
            in '카'..'킿' -> "ㅋ"
            in '타'..'팋' -> "ㅌ"
            in '파'..'핗' -> "ㅍ"
            in '하'..'힣' -> "ㅎ"
            else -> initial.toString() // 한글이 아닐 경우 문자 그대로 반환
        }
    }

    companion object {
        const val TAG = "BasicBottomModalSheet"
    }
}
