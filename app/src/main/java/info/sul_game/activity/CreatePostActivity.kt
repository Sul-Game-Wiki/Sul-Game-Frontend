package info.sul_game.activity
import info.sul_game.fragment.OfficialCreateFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import info.sul_game.databinding.ActivityCreateBinding
import info.sul_game.fragment.CreateBottomSheetFragment
import info.sul_game.fragment.CreateCreationGameFragment
import info.sul_game.fragment.IntroCreateFragment

class CreatePostActivity : AppCompatActivity(), CreateBottomSheetFragment.OnOptionSelectedListener {

    private lateinit var binding: ActivityCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vpCreate.setUserInputEnabled(false)

        val pagerAdapter = CreateFragmentPagerAdapter(this)
        binding.vpCreate.adapter = pagerAdapter

        binding.btnChangeCreate.setOnClickListener {
            val bottomSheetFragment = CreateBottomSheetFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    override fun onOptionSelected(option: Int) {
        binding.vpCreate.currentItem = option
    }

    class CreateFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 3 // 0번과 1번, 두 개의 프래그먼트

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CreateCreationGameFragment()
                1 -> IntroCreateFragment()
                2 -> OfficialCreateFragment()
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}