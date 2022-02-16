package com.stenleone.testcontactproject.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.stenleone.testcontactproject.data.repository.ContactRepository
import com.stenleone.testcontactproject.domain.entity.ContactEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class ContactListViewModel @Inject constructor(private val contactRepository: ContactRepository) : ViewModel() {

    private val _contacts = MutableLiveData<PagingData<ContactEntity>>()
    val contacts: LiveData<PagingData<ContactEntity>> = _contacts

    init {
        getContact()
    }

    @ExperimentalPagingApi
    private fun getContact() {
        viewModelScope.launch {
            contactRepository.getContacts().cachedIn(viewModelScope)
                .catch {

                }
                .collect {
                _contacts.postValue(it)
            }
//            if (contacts is DataState.Success) {
//                _contacts.postValue(contacts.data)
//            }
        }
    }

}