package com.ilham.taspesialisbangunan.ui.USER.homeuser.fragment.notifications

import android.view.View
import com.ilham.taspesialisbangunan.data.database.PrefsManageruser

interface NotificationsContract {

   interface View{
       fun initFragment(view: android.view.View)
       fun showMessage(message: String)
   }

}