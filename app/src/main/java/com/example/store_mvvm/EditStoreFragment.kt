package com.example.store_mvvm

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.store_mvvm.databinding.FragmentEditStoreBinding
import com.google.android.material.snackbar.Snackbar


class EditStoreFragment : Fragment() {

    private lateinit var mBinding: FragmentEditStoreBinding
    private var mActivity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentEditStoreBinding.inflate(inflater, container, false)
        // retornamos binding root
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //conseguir la actividad en la cual esta alojada y la llamaremos MainActivity
         mActivity = activity as? MainActivity
        //inidicar la flecha de retroceso en la actividad de este fragmento
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //agrega el titulo en la barra
        mActivity?.supportActionBar?.title = getString(R.string.edit_store_title_add)
        //acceso al menu y darle el control
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            R.id.action_save -> {
                Snackbar.make(mBinding.root, getString(R.string.edit_store_message_save_success), Snackbar.LENGTH_SHORT).show()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}