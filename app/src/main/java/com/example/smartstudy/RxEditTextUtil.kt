package com.example.smartstudy

import android.widget.EditText
import com.jakewharton.rxbinding.widget.RxTextView
import java.util.concurrent.TimeUnit

class RxEditTextUtil {
    private var instance: RxEditTextUtil? = null

    fun getInstance(): RxEditTextUtil {
        if (instance == null)
            instance =  RxEditTextUtil()
        return instance as RxEditTextUtil
    }

    fun getEditTextObservable(editText: EditText): rx.Observable<String> {
        return RxTextView.textChanges(editText)
            .debounce(1000, TimeUnit.MILLISECONDS)
            .filter { it.length > 1 }
            .map { t -> t.toString() }
    }
}