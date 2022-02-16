package com.stenleone.testcontactproject.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stenleone.testcontactproject.data.repository.ContactRepository
import com.stenleone.testcontactproject.domain.DataState
import com.stenleone.testcontactproject.domain.entity.ContactEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(private val contactRepository: ContactRepository): ViewModel() {

    private val _contacts = MutableLiveData<ArrayList<ContactEntity>>()
    val contacts: LiveData<ArrayList<ContactEntity>> = _contacts

    init {
        getContact()
    }

    private fun getContact() {
        viewModelScope.launch {
            val contacts = contactRepository.getContacts()
            if (contacts is DataState.Success) {
                _contacts.postValue(contacts.data)
            }
        }
    }

}