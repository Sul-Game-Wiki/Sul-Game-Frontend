package info.sul_game.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import info.sul_game.databinding.FragmentCreateBottomSheetBinding

class CreateBottomSheetFragment : BottomSheetDialogFragment() {

    interface OnOptionSelectedListener {
        fun onOptionSelected(option: Int)
    }

    private var listener: OnOptionSelectedListener? = null
    private var _binding: FragmentCreateBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnOptionSelectedListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 버튼 클릭 리스너 설정
        binding.btnSelectCreate.setOnClickListener {
            listener?.onOptionSelected(0)  // Create 선택 시
            dismiss()
        }

        binding.btnSelectIntro.setOnClickListener {
            listener?.onOptionSelected(1)  // Intro 선택 시
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // 메모리 누수 방지
    }
}
