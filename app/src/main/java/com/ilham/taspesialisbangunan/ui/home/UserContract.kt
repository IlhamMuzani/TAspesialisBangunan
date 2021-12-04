package com.ilham.taspesialisbangunan.ui.home

interface UserContract {

    interface View{
        fun initListener()
        fun showMessage(message: String)
    }
}