package com.ilham.taspesialisbangunan.ui.USERJASA.produk_materialjasa.tabs.ProdukJasa

//class ProdukActivity : AppCompatActivity(), ProdukContract.View, OnMapReadyCallback {
//
//    lateinit var presenter: ProdukPresenter
//    lateinit var produkAdapter: ProdukAdapter
//    lateinit var produk: DataProduk
//    lateinit var prefsManager: PrefsManager
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_produk)
//        presenter = ProdukPresenter(this)
//        prefsManager = PrefsManager(this)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        presenter.getProduk(prefsManager.prefsId)
//    }
//
////    override fun initActivity() {
////        supportActionBar!!.title = "Produk"
////        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
////
////        MapsHelper.permissionMap(this,this)
////    }
////
////    override fun initListener() {
////
////        produkAdapter = ProdukAdapter(this, arrayListOf()) {
////                dataProduk: DataProduk, position: Int, type: String ->
////
////            produk = dataProduk
////
////
////            when (type) {
////                "Update" -> startActivity(Intent(this, ProdukUpdateActivity::class.java))
////                "Delete" -> showDialogDelete( dataProduk, position )
////                "Detail" -> showDialogDetail( dataProduk, position )
////
////            }
////        }
////
////        rcvProduk.apply {
////            layoutManager = LinearLayoutManager(context)
////            adapter = produkAdapter
////        }
////
////        swipe.setOnRefreshListener {
////            presenter.getProduk(prefsManager.prefsId)
////        }
////
////        fab.setOnClickListener { view ->
////            startActivity(Intent(this, ProdukCreateActivity::class.java))
////        }
////    }
////
////    override fun onLoadingProduk(loading: Boolean) {
////        when (loading) {
////            true -> swipe.isRefreshing = true
////            false -> swipe.isRefreshing = false
////        }
////    }
//
//    override fun onResultProduk(responseProdukList: ResponseProdukList) {
//        val dataProduk: List<DataProduk> = responseProdukList.dataProduk
//        produkAdapter.setData(dataProduk)
//    }
//
//    override fun onResultDelete(responseProdukUpdate: ResponseProdukUpdate) {
//        showMessage( responseProdukUpdate. msg )
//    }
//
//    override fun showDialogDelete(dataProduk: DataProduk, position: Int) {
//        val dialog = AlertDialog.Builder(this)
//        dialog.setTitle( "Konfirmasi" )
//        dialog.setMessage( "Hapus ${produk.nama_toko}?" )
//
//        dialog.setPositiveButton("Hapus") { dialog, which ->
//            presenter.deleteProduk( Constant.PRODUK_ID )
//            produkAdapter.removeProduk( position )
//            dialog.dismiss()
//        }
//
//        dialog.setNegativeButton("Batal") { dialog, which ->
//            dialog.dismiss()
//        }
//
//        dialog.show()
//    }
//
//    override fun showDialogDetail(dataProduk: DataProduk, position: Int) {
//        val dialog = BottomSheetDialog(this)
//        val view = layoutInflater.inflate(R.layout.dialog_detailproduk, null)
//
//        GlideHelper.setImage( applicationContext,"http://192.168.43.224/api_spesialisJB/public/"+dataProduk.gambar!!, view.imvGambartoko)
//
//        view.txvName.text = dataProduk.nama_toko
//        view.txvJenis.text = dataProduk.jenis_pembuatan
//        view.txvAlamat.text = dataProduk.alamat
//        view.edtPhoneDetail.text = dataProduk.phone
//        view.txvHarga.text = dataProduk.harga
//        view.txvDeskripsi.text = dataProduk.deskripsi
//
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync( this )
//
//        view.imvClose.setOnClickListener {
//            supportFragmentManager.beginTransaction().remove(mapFragment).commit()
//            dialog.dismiss()
//        }
//
//        dialog.setCancelable(false)
//        dialog.setContentView(view)
//        dialog.show()
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        val latLng = LatLng (produk.latitude!!.toDouble(), produk.longitude!!.toDouble())
//        googleMap.addMarker ( MarkerOptions(). position(latLng).title( produk.nama_toko ))
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
//    }
//
//    override fun showMessage(message: String) {
//        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        finish()
//        return super.onSupportNavigateUp()
//    }
//
//}
