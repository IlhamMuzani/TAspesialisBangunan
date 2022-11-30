package com.ilham.taspesialisbangunan.ui.pelanggan.invoice

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.print.PrintAttributes
import android.print.PrintManager
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.produk.DataProduk
import com.ilham.taspesialisbangunan.data.model.user.ResponseUser
import com.ilham.taspesialisbangunan.data.model.user.ResponseUserUpdate
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.detail_produknotif.DetailPelangganPresenter
import kotlinx.android.synthetic.main.activity_passwordbaru.*
import kotlinx.android.synthetic.main.activity_passwordbaru.btnPasswordbaru
import kotlinx.android.synthetic.main.activity_perbaruipassword.*
import kotlinx.android.synthetic.main.activity_produkdetail.*
import kotlinx.android.synthetic.main.activity_register_pelanggan.*
import java.util.concurrent.TimeUnit

class InvoiceActivity : AppCompatActivity(), InvoiceContract.View {

    lateinit var presenter: InvoicePresenter
    lateinit var pengajuan: DataPengajuan
    lateinit var produk: DataProduk

    private var webView: WebView? = null
    lateinit var btnprint : ImageView

    private lateinit var sLoading: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sSuccess: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sError: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog
    private lateinit var sAlert: com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)
        presenter = InvoicePresenter(this)

    }

    override fun onStart() {
        super.onStart()
        presenter.getDetail(Constant.PENGAJUAN_ID)
    }

    override fun initActivity() {

        sLoading = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.PROGRESS_TYPE)
        sSuccess = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.SUCCESS_TYPE).setTitleText("Berhasil")
        sError = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.ERROR_TYPE).setTitleText("Gagal!")
        sAlert = com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog(this, com.ilham.taspesialisbangunan.ui.utils.sweetalert.SweetAlertDialog.WARNING_TYPE).setTitleText("Perhatian!")

        btnprint = findViewById(R.id.btnCetakprint)
        webView = findViewById(R.id.webView)

    }

    override fun initListener() {

        btnprint.setOnClickListener(View.OnClickListener { // Code Here ==========
            presenter.getDetail(Constant.PENGAJUAN_ID)
            createWebPrintJob(webView!!)
        })
        webView!!.webChromeClient = WebChromeClient()
        webView!!.settings.javaScriptEnabled = true
        // WebView loading handling
        // WebView loading handling
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                //if WebView load successfully then VISIBLE fab Button
                btnprint.setVisibility(View.VISIBLE)
            }
        }
        webView!!.loadUrl("https://spesialisjb.ufomediategal.com/invoice/" + Constant.PENGAJUAN_ID)

    }

    override fun onResultDetail(responsePengajuanDetail: ResponsePengajuanDetail) {
        pengajuan = responsePengajuanDetail.pengajuan
    }

    override fun onLoading(loading: Boolean, message: String?) {
        when (loading) {
            true -> sLoading.setTitleText(message).show()
            false -> sLoading.dismiss()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun createWebPrintJob(webView: WebView) {
        val printManager = this.getSystemService(PRINT_SERVICE) as PrintManager
        val printAdapter = webView.createPrintDocumentAdapter()
        val jobName = getString(R.string.app_name) + " Print Test"
        printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
    }


}