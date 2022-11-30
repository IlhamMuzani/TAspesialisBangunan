package com.ilham.taspesialisbangunan.ui.fragment.home

import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail

interface HomeContract {

    interface Presenter{
        fun userDetail(id: String)
    }

    interface View{
        fun initFragment(view: android.view.View)
        fun showMessage(message: String)
        fun onLoading(loading: Boolean, message: String? = "Loading...")
        fun onResult(responseUserdetail: ResponseUserdetail)
    }
}