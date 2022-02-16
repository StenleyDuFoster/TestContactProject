package com.stenleone.testcontactproject.ui.main

import android.os.Bundle
import com.stenleone.testcontactproject.R
import com.stenleone.testcontactproject.core.BaseBindingActivity
import com.stenleone.testcontactproject.core.FragmentController
import com.stenleone.testcontactproject.data.repository.ContactRepository
import com.stenleone.testcontactproject.databinding.ActivityMainBinding
import com.stenleone.testcontactproject.ui.list.ContactListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main

    private val fragmentController = FragmentController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentController.setup(supportFragmentManager, lifecycle, R.id.fragmentContainer)
        fragmentController.add(ContactListFragment())
    }

}