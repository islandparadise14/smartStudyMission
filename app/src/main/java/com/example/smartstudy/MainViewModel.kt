package com.example.smartstudy

import android.app.Application
import android.widget.EditText
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import rx.android.schedulers.AndroidSchedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var mToastMessage: MutableLiveData<String> = MutableLiveData()
    var startHour: RxEditTextUtil = RxEditTextUtil()
    var startMinute: RxEditTextUtil = RxEditTextUtil()
    var endHour: RxEditTextUtil = RxEditTextUtil()
    var endMinute: RxEditTextUtil = RxEditTextUtil()
    var nowHour: RxEditTextUtil = RxEditTextUtil()
    var nowMinute: RxEditTextUtil = RxEditTextUtil()

    var mStartHourString: ObservableField<String> = ObservableField()
    var mStartMinuteString: ObservableField<String> = ObservableField()
    var mEndHourString: ObservableField<String> = ObservableField()
    var mEndMinuteString: ObservableField<String> = ObservableField()
    var mNowHourString: ObservableField<String> = ObservableField()
    var mNowMinuteString: ObservableField<String> = ObservableField()

    private var mStartHour: String? = null
    private var mStartMinute: String? = null
    private var mEndHour: String? = null
    private var mEndMinute: String? = null
    private var mNowHour: String? = null
    private var mNowMinute: String? = null

    fun observeTextSetting(startHourText: EditText,
                           startMinuteText: EditText,
                           endHourText: EditText,
                           endMinuteText: EditText,
                           nowHourText: EditText,
                           nowMinuteText: EditText
    ) {
        startHour.getInstance().getEditTextObservable(startHourText)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text: String? ->
                run {
                    mStartHour = checkExcessHour(text)
                    mStartHourString.set(checkExcessHour(text))
                    checkResult()
                }
            }
        startMinute.getInstance().getEditTextObservable(startMinuteText)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text: String? ->
                run {
                    mStartMinute = checkExcessMinute(text)
                    mStartMinuteString.set(checkExcessMinute(text))
                    checkResult()
                }
            }
        endHour.getInstance().getEditTextObservable(endHourText)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text: String? ->
                run {
                    mEndHour = checkExcessHour(text)
                    mEndHourString.set(checkExcessHour(text))
                    checkResult()
                }
            }
        endMinute.getInstance().getEditTextObservable(endMinuteText)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text: String? ->
                run {
                    mEndMinute = checkExcessMinute(text)
                    mEndMinuteString.set(checkExcessMinute(text))
                    checkResult()
                }
            }
        nowHour.getInstance().getEditTextObservable(nowHourText)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text: String? ->
                run {
                    mNowHour = checkExcessHour(text)
                    mNowHourString.set(checkExcessHour(text))
                    checkResult()
                }
            }
        nowMinute.getInstance().getEditTextObservable(nowMinuteText)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text: String? ->
                run {
                    mNowMinute = checkExcessMinute(text)
                    mNowMinuteString.set(checkExcessMinute(text))
                    checkResult()
                }
            }
    }

    private fun checkExcessHour(hour: String?): String? {
        hour?.let {
            if (it.toInt() > 23)
                return lessThenTen(it.toInt() % 24)
        }
        return hour
    }

    private fun checkExcessMinute(minute: String?): String? {
        minute?.let {
            if (it.toInt() > 59)
                return lessThenTen(it.toInt() % 60)
        }
        return minute
    }

    private fun lessThenTen(time: Int): String {
        return if (time < 10)
            "0$time"
        else
            time.toString()
    }

    private fun checkResult() {
        if (!mStartHour.isNullOrEmpty()
            && !mStartMinute.isNullOrEmpty()
            && !mEndHour.isNullOrEmpty()
            && !mEndMinute.isNullOrEmpty()
            && !mNowHour.isNullOrEmpty()
            && !mNowMinute.isNullOrEmpty())
            mToastMessage.value = isBetweenStartAndEndTime(
                mStartHour!!.toInt(),
                mStartMinute!!.toInt(),
                mEndHour!!.toInt(),
                mEndMinute!!.toInt(),
                mNowHour!!.toInt(),
                mNowMinute!!.toInt()).toString()
    }

    private fun isBetweenStartAndEndTime(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int, nowHour: Int, nowMinute: Int): Boolean {
        val startTime = toMinute(startHour, startMinute)
        var endTime = toMinute(endHour, endMinute)
        var nowTime = toMinute(nowHour, nowMinute)
        if (startTime > endTime) {
            if (nowTime <= endTime)
                nowTime += toMinute(24, 0)
            endTime += toMinute(24, 0)
        }
        return nowTime in startTime..endTime
    }

    private fun toMinute(hour: Int, minute: Int): Int {
        return hour * 60 + minute
    }

}