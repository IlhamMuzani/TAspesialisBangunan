package com.ilham.taspesialisbangunan.ui.userjasa.home

class JasaPresenter(val view : JasaContract.View) {

    init {
        view.initListener()
    }
}