package org.sopt.dosopttemplate.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.sopt.dosopttemplate.data.adaptor.UserAdaptor
import org.sopt.dosopttemplate.databinding.FragmentHomeBinding
import org.sopt.dosopttemplate.server.ServicePool.userService
import org.sopt.dosopttemplate.server.auth.response.ResponseUserListDto
import org.sopt.dosopttemplate.util.makeToast
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "바인딩 객체가 생성되지 않았습니다." }

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
        userService.getUserList(2)
            .enqueue(object : retrofit2.Callback<ResponseUserListDto> {
                override fun onResponse(
                    call: Call<ResponseUserListDto>,
                    response: Response<ResponseUserListDto>
                ) {
                    if (response.isSuccessful) {
                        val userList = response.body()?.data
                        if (userList != null) {
                            Log.e("서버 통신", "성공")

                            userAdapter.setUserList(userList)
                        }

                    }
                }

                override fun onFailure(call: Call<ResponseUserListDto>, t: Throwable) {
                    binding.root.makeToast("서버 에러 발생")
                }
            }
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}