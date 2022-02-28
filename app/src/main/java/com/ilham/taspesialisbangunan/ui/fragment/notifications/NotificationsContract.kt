package com.ilham.taspesialisbangunan.ui.fragment.notifications

import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail

interface NotificationsContract {

    interface Presenter{
        fun UserDetail(id: String)
    }

   interface View{
       fun initFragment(view: android.view.View)
       fun showMessage(message: String)
       fun onLoading(loading: Boolean)
       fun onResult(responseUserdetail: ResponseUserdetail)
   }
}