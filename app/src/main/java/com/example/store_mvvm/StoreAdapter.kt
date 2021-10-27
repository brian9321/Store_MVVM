package com.example.store_mvvm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.store_mvvm.databinding.ItemStoreBinding

class StoreAdapter(private var stores: MutableList<StoreEntity>, private var listener: OnClickListener): RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    //miembro de la clase, una regla en las buenas practicas
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        //inflar nuestra vista
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_store, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = stores.get(position)

        with(holder){
            setListener(store)
            binding.tvName.text = store.name
            binding.cbFavorite.isChecked = store.isFavorite
        }
    }

    //tamaño de la lista que esta reciviendo
    override fun getItemCount() = stores.size

    //agregar registro
    fun add(storeEntity: StoreEntity) {
        stores.add(storeEntity) //agrega un nuevo dato al adaptador
        //actualiza de forma general
        notifyDataSetChanged()
    }

    //agregar las datas e BD
    fun setStores(stores: MutableList<StoreEntity>) {
        this.stores = stores
        notifyDataSetChanged()
    }

    //ACTUALIZAR
    fun update(storeEntity: StoreEntity) {
        val index = stores.indexOf(storeEntity)
        if(index != -1){
            stores.set(index, storeEntity)
            notifyItemChanged(index) //actualiza solo el item
        }
    }

    //ELIMINAR
    fun delete(storeEntity: StoreEntity) {
        val index = stores.indexOf(storeEntity) //busca el idex de la tienda que le mandamos
        //si el index es diferente de -1 procede con el flujo
        if(index != -1){
            stores.removeAt(index)
            notifyItemRemoved(index) //elimina solo el item dado
        }
    }

    //vinvular la vista con viewBindig
    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val binding = ItemStoreBinding.bind(view)

        fun setListener(storeEntity: StoreEntity){
            //nos ayuda a quitar binding.root de los listener
            with(binding.root){
                //pulsacion corta detalle
                setOnClickListener{
                    listener.onClick(storeEntity)
                }
                //pulsacion larga eleimna
                setOnLongClickListener{
                    listener.onDeleteStore(storeEntity)
                    true
                }
            }

            binding.cbFavorite.setOnClickListener{listener.onFavoriteStore(storeEntity)}
        }
    }
}