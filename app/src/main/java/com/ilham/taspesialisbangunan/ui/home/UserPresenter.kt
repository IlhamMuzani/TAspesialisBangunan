package com.ilham.taspesialisbangunan.ui.home

class UserPresenter(val view : UserContract.View) {

    init {
        view.initListener()
    }
}