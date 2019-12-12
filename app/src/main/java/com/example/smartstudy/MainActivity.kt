package com.example.smartstudy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.smartstudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mViewModel: MainViewModel
    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        observeViewModel()
    }

    private fun initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mBinding.viewModel = mViewModel

        mViewModel.observeTextSetting(
            mBinding.startHour,
            mBinding.startMinute,
            mBinding.endHour,
            mBinding.endMinute,
            mBinding.nowHour,
            mBinding.nowMinute)
    }

    private fun observeViewModel() {
        mViewModel.mToastMessage.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        })
    }
}
