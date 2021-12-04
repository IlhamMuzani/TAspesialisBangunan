package com.ilham.taspesialisbangunan.ui.home.fragment.home

import com.ilham.taspesialisbangunan.data.model.user.ResponseUserdetail

interface HomeContract {

    interface Presenter{
        fun userDetail(id: String)
    }

    interface View{
        fun initFragment(view: android.view.View)
        fun showMessage(message: String)
        fun onLoading(loading: Boolean)
        fun onResult(responseUserdetail: ResponseUserdetail)
    }
}