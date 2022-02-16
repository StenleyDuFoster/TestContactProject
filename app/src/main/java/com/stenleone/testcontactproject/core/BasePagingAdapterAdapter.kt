package com.stenleone.testcontactproject.core

import android.view.View
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stenleone.testcontactproject.util.extencion.clicks
import com.stenleone.testcontactproject.util.extencion.throttleFirst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BasePagingAdapterAdapter<T: Any, VH : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) : PagingDataAdapter<T, VH>(diffCallback) {

    private val job = Job()
    protected val adapterScope = CoroutineScope(Dispatchers.Main + job)

    lateinit var clickListener: (data: T) -> Unit

    override fun onBindViewHolder(holder: VH, position: Int) =
        (holder as RecyclerBinder).bind()

    override fun onViewDetachedFromWindow(holder: VH) =
        (holder as RecyclerBinder).unBind()

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        job.cancel()
    }

    protected fun View.throttleClicks(periodMillis: Long = 500, onEach: () -> Unit) {
        this.clicks()
            .throttleFirst(periodMillis)
            .onEach {
                onEach.invoke()
            }
            .launchIn(adapterScope)
    }

}