package org.sopt.dosopttemplate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import org.sopt.dosopttemplate.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "바인딩 객체가 생성되지 않았다. 생성하고 불러라 임마!" }

    private val mockmyList = DataProvider.getMyList()
    private val mockFriendList = DataProvider.getFriendList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { // 이제 반환하는 View가 Null일 수 없기 때문에, ?를 지워주셔도 됩니다.
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 대부분의 로직은 여기에 구현합니다.
        // 원두를 갈고~
        // 커피를 내리고~
        val myAdapter = MyAdaptor(requireContext())
        val friendAdapter = FriendAdaptor(requireContext())

        val concatAdaptor = ConcatAdapter(myAdapter, friendAdapter)

        binding.rcvHomeMain.adapter = concatAdaptor

        myAdapter.setMyList(mockmyList)
        friendAdapter.setFriendList(mockFriendList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}