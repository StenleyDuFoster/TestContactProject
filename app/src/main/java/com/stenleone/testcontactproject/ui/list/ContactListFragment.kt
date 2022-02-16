package com.stenleone.testcontactproject.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.stenleone.testcontactproject.R
import com.stenleone.testcontactproject.core.BaseBindingFragment
import com.stenleone.testcontactproject.databinding.FragmentListContactBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactListFragment : BaseBindingFragment<FragmentListContactBinding>() {

    override val layoutId = R.layout.fragment_list_contact

    private val viewModel: ContactListViewModel by viewModels()
    private val contactsAdapter = ContactAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupViewModel()
        setupAdapter()
    }

    private fun setupViewModel() {
        viewModel.contacts.observeNotNull {
            lifecycleScope.launch {
                contactsAdapter.submitData(it)
            }
        }
    }

    private fun setupAdapter() {
        contactsAdapter.clickListener = {

        }
        binding.recycler.adapter = contactsAdapter
    }

}