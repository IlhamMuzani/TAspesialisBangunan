package com.ilham.taspesialisbangunan.ui.USER.homeuser

interface UserContract {

    interface View{
        fun initListener()
        fun showMessage(message: String)
    }
}