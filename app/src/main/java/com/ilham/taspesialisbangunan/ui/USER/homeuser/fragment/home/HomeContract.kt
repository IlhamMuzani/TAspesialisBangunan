package com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.home

interface HomeContract {

    interface View{
        fun initFragment(view: android.view.View)
        fun showMessage(message: String)
    }
}