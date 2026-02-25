package com.devtamuno.cashipayment

import android.app.Application
import com.devtamuno.cashipayment.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    initKoin { androidContext(this@MyApplication) }
  }
}
