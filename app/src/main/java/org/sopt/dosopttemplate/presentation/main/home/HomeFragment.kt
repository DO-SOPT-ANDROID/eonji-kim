package org.sopt.dosopttemplate.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.sopt.dosopttemplate.data.adaptor.UserAdaptor
import org.sopt.dosopttemplate.databinding.FragmentHomeBinding
import org.sopt.dosopttemplate.util.makeToast

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "바인딩 객체가 생성되지 않았습니다." }
    private val userViewModel by viewModels<UserViewModel>()

    private lateinit var userAdapter: UserAdaptor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 대부분의 로직은 여기에 구현한다.
        connectAdapter()
        getUserList()
    }

    private fun connectAdapter() {
        userAdapter = UserAdaptor(requireContext())

        binding.rcvHomeMain.adapter = userAdapter
    }

    private fun getUserList() {
        userViewModel.getUserList(2)
        userViewModel.userSuccess.observe(viewLifecycleOwner) {
            if (it) {
                val userList = userViewModel.userResult.value?.data
                if (userList != null) {
                    Log.e("서버 통신", "성공")

                    userAdapter.setUserList(userList)
                }
            } else {
                binding.root.makeToast("유저 받아오기 실패")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}