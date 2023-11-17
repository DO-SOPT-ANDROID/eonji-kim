package org.sopt.dosopttemplate.presentation.main.doandroid

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import org.sopt.dosopttemplate.databinding.FragmentDoAndroidBinding
import kotlin.math.abs

class DoAndroidFragment : Fragment() {
    private var _binding: FragmentDoAndroidBinding? = null
    private val binding: FragmentDoAndroidBinding
        get() = requireNotNull(_binding) { "바인딩 객체가 생성되지 않았다. 생성하고 불러라 임마!" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoAndroidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 대부분의 로직은 여기에 구현한다.
        val viewPager = connectAdapter()

        addSpacing(viewPager)
    }

    private fun connectAdapter(): ViewPager2 {
        val demoData = arrayListOf(
            "Android",
            "ios",
            "Web",
            "Server",
            "Design",
            "Plan",
        )

        val viewPager = initCarousel()

        val adapter = CarouseIRVAdapter(demoData)
        viewClickListener(adapter)
        viewPager.adapter = adapter
        return viewPager
    }

    private fun addSpacing(viewPager: ViewPager2) {
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        addScaling(compositePageTransformer)
        viewPager.setPageTransformer(compositePageTransformer)
    }

    private fun addScaling(compositePageTransformer: CompositePageTransformer) {
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
    }

    private fun initCarousel(): ViewPager2 {
        val viewPager = binding.vpPart
        viewPager.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
        }
        return viewPager
    }

    private fun viewClickListener(adapter: CarouseIRVAdapter) {
        adapter.onItemClickListener = object : CarouseIRVAdapter.OnItemClickListener {
            override fun onItemClick(item: String) {
                Toast.makeText(
                    requireContext(),
                    "DO SOPT 33기 $item 파트 화이팅!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}