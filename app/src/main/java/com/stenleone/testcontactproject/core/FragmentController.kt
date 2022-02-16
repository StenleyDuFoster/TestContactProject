package com.stenleone.testcontactproject.core

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver

class FragmentController : LifecycleObserver {

    private var fragmentManager: FragmentManager? = null
    private var containerId: Int? = null

    private var lifecycleEventObserver: LifecycleEventObserver? = null

    fun setup(fragmentManager: FragmentManager, lifecycle: Lifecycle, containerId: Int? = null) {
        this.fragmentManager = fragmentManager
        this.containerId = containerId
        lifecycleEventObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                destroy(lifecycle)
            }
        }.also {
            lifecycle.addObserver(it)
        }
    }

    fun add(newFragment: BaseBindingFragment<*>, newFragmentTag: String? = null, oldFragment: BaseBindingFragment<*>? = null, oldFragmentTag: String? = null, containerId: Int? = null) {
        (newFragmentTag ?: newFragment.tagFragment).also {
            if (fragmentManager?.findFragmentByTag(it) != null)     {
                return
            }
        }

        when {
            oldFragment == null -> {
                fragmentManager?.beginTransaction()?.add(this.containerId ?: containerId ?: 0, newFragment, newFragmentTag)?.commit()
            }
            oldFragmentTag != null -> {
                fragmentManager?.findFragmentByTag(oldFragmentTag)?.let {
                    fragmentManager?.beginTransaction()?.remove(it)?.add(newFragment, newFragmentTag ?: newFragment.tagFragment)?.commit()
                }
            }
            else -> {
                fragmentManager?.beginTransaction()?.remove(oldFragment)?.add(newFragment, newFragmentTag ?: newFragment.tagFragment)?.commit()
            }
        }
    }

    private fun destroy(lifecycle: Lifecycle) {
        this.fragmentManager = null
        this.containerId = null
        lifecycleEventObserver?.let {
            lifecycle.removeObserver(it)
        }
    }

}