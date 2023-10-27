package org.sopt.dosopttemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.sopt.dosopttemplate.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_home)
        if (currentFragment == null) {  // 없으면 걍 직접 끼워넣는다!
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_home, HomeFragment())
                .commit()
        }

        clickBottomNavigation()
    }

    private fun clickBottomNavigation() {
        binding.bnvHome.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.menu_do_android -> {
                    replaceFragment(DoAndroidFragment())
                    true
                }

                R.id.menu_mypage -> {
                    replaceFragment(receiveLogin())
                    true
                }

                else -> false
            }
        }
        binding.bnvHome.selectedItemId = R.id.menu_home
    }

    private fun receiveLogin(): MyPageFragment {
        val idValue = intent.getStringExtra("idValue")
        val nameValue = intent.getStringExtra("nameValue")
        val mbtiValue = intent.getStringExtra("mbtiValue")

        val myPageFragment = MyPageFragment()
        val bundle = Bundle()

        bundle.putString("idValue", idValue)
        bundle.putString("nameValue", nameValue)
        bundle.putString("mbtiValue", mbtiValue)

        myPageFragment.arguments = bundle
        return myPageFragment
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_home, fragment)
            .commit()
    }
}