package com.example.store_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.store_mvvm.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), OnClickListener {

    //renombrar shif+fn+f6 nuevo dato
    private lateinit var mBinding: ActivityMainBinding //creamos la instancia binding de la activity

    private lateinit var mAdapter: StoreAdapter //variable del adaptador
    private lateinit var mGridLayout: GridLayoutManager //variable de configuracion del grid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater) //asiganmos e inflamos el bindig
        setContentView(mBinding.root) //generemos la coneccion del bindig

        /*mBinding.btnSave.setOnClickListener {

            if(mBinding.etName.text.isNotEmpty()){
                val store = StoreEntity(name = mBinding.etName.text.toString().trim())
                Thread{
                    StoreApplication.database.storeDao().addStores(store)
                }.start()

                limpiar()
                mAdapter.add(store)
            }

        }*/

        mBinding.fab.setOnClickListener{
            launchEditFragment()
        }

        setupRecyclerView() //mandamos llamar las configuraciones
    }

    //lanzamiento de fragment
    private fun launchEditFragment() {
        val fragment = EditStoreFragment()
        //fragment manager: es el gestor de controlar los fragmentos
        val fragmetManager = supportFragmentManager
        //fragment transaction es para decir como se va a ejecutar
        val fragmentTransaction = fragmetManager.beginTransaction()

        //como salir -> es para salir del fragmento para retroceder
        fragmentTransaction.addToBackStack(null)
        //le pasamos el contenedor y el fragmento
        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.commit()

        mBinding.fab.hide()

    }

    private fun limpiar() {
        //mBinding.etName.setText("")
    }

    private fun setupRecyclerView() {
        mAdapter = StoreAdapter(mutableListOf(), this) //mandamos una lista vacia y el contexto
        mGridLayout = GridLayoutManager(this, 2) //conexto y el numero de columnas
        getStores() //consulta a la base de datos


        //el bindig esta haciendo referencia al adaptador del activity_main
        mBinding.recyclerView.apply {
            setHasFixedSize(true) //no cambiara de tamaño y usara los del recurso
            layoutManager = mGridLayout //configuracion del grid
            adapter = mAdapter //le pasamos el adaptador custom que creamos
        }
    }

    private fun getStores(){
        doAsync {
            val stores = StoreApplication.database.storeDao().getAllStores()
            uiThread{
                mAdapter.setStores(stores)
            }
        }


    }

    /* OnClickListenerS
    * Ver detalles
    * */
    override fun onClick(storeEntity: StoreEntity) {
        TODO("Not yet implemented")
    }

    //actualizar a foavrito una historia
    override fun onFavoriteStore(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite
        doAsync{
            StoreApplication.database.storeDao().updateStores(storeEntity)
            uiThread{
                mAdapter.update(storeEntity)
            }
        }
    }

    //eliminar una tienda
    override fun onDeleteStore(storeEntity: StoreEntity) {
        doAsync{
            StoreApplication.database.storeDao().deleteStores(storeEntity)
            uiThread{
                mAdapter.delete(storeEntity)
            }
        }
    }
}