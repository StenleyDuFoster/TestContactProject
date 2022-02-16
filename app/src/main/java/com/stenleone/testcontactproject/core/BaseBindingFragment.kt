package com.stenleone.testcontactproject.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

abstract class BaseBindingFragment<Binding : ViewDataBinding> : Fragment() {

    protected lateinit var binding: Binding
    protected abstract val layoutId: Int
    open val tagFragment: String = this::class.toString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<Binding>(inflater, layoutId, container, false).apply {
        binding = this
    }.root

    fun <T> LiveData<T>.observeNotNull(
        owner: LifecycleOwner = viewLifecycleOwner,
        observer: (t: T) -> Unit
    ) {
        this.observe(owner, {
            it?.let(observer)
        })
    }

    fun hideKeyboard() {
        (requireActivity() as BaseBindingActivity<*>).hideKeyboard()
    }

}