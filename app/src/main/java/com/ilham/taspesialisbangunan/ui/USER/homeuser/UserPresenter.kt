package com.ilham.taspesialisbangunan.ui.USER.homeuser

class UserPresenter(val view : UserContract.View) {

    init {
        view.initListener()
    }
}