package com.example.sul_game_frontend_practice1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guhaejo.main.notification.Alarm
import com.example.guhaejo.main.notification.AlarmAdapter
import com.example.guhaejo.main.notification.AlarmDecoration
import com.example.sul_game_frontend_practice1.databinding.FragmentAlarmBinding

class AlarmFragment : Fragment() {

    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlarmBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        IntroMain()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun IntroMain() {
        val notificationData = arrayListOf<Alarm>(
            Alarm("인기", "어제 가장 핫했던 인기 게임이예요 : 바니바니 게임", "06/29 00:00"),
            Alarm("창작", "어제 가장 핫했던 인기 게임이예요 : 바니바니삽", "06/28 22:00"),
            Alarm("창작", "고해조님이 회원님의 게시물을 좋아합니다.", "06/27 21:4"),
            Alarm("창작", "구해조님이 댓글을 남겼습니다 : 개재밌네요 ㅋㅋ", "06/27 21:04"),
        )

        binding.rvNotificationNotification.adapter = AlarmAdapter(notificationData)
        val ver_space_decoration = AlarmDecoration()
        binding.rvNotificationNotification.addItemDecoration(ver_space_decoration)
        binding.rvNotificationNotification.layoutManager =
            LinearLayoutManager(context)
    }
}