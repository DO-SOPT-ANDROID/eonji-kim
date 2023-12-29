package org.sopt.dosopttemplate.data.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import org.sopt.dosopttemplate.databinding.ItemFriendBinding
import org.sopt.dosopttemplate.server.auth.response.ResponseUserListDataDto

class UserAdaptor(context: Context) : RecyclerView.Adapter<UserViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var userList: List<ResponseUserListDataDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemFriendBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    fun setUserList(user: List<ResponseUserListDataDto>) {
        this.userList = user
        notifyDataSetChanged()
    }

}

class UserViewHolder(private val binding: ItemFriendBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val context: Context = binding.root.context
    fun onBind(user: ResponseUserListDataDto) {
        loadImage(binding.ivItemImage, user.avatar)
        binding.tvItemName.text = user.firstName
        binding.tvItemMessage.text = user.email
    }

    //기존 뷰들에 없는 새로운 xml 속성을 연결하는 기능 메소드를 가지는 객체
    //보통 static 메소드를 가진 class로 사용
    //static 메소드를 가져야하기 때문에 class면 안됨 object로 명시
    companion object {
        //객체가 단 한마리 밖에 없는 애 : 싱글턴 패턴!!


        @JvmStatic  //static 만드는 어노테이션
        // 1) 이미지뷰에 새로운 xml 속성 만들기
        // [속성명 : imageUrl ]
        @BindingAdapter("imageUrl")  //어노테이션 해독기 필요 - 빌드그래이들에 기능 추가 필요!
        fun loadImage(view: ShapeableImageView, imageUrl: String?) {
            imageUrl?.let {
                //메소드 명은 내 맘대로 다만! 파라미터 (어떤뷰에 주는지(뷰타입), 어떤 값인지(속성값)) 가 중요!!
                Glide.with(view.context)
                    .load(it)
                    .into(view)
            }
        }
    }
}