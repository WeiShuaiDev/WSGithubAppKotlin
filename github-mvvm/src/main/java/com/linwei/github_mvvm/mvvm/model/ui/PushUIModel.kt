package com.linwei.github_mvvm.mvvm.model.ui
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.linwei.github_mvvm.BR

class PushUIModel : BaseObservable() {
    var pushUserName: String = "---"
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushUserName)
        }
    var pushImage: String = "---"
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushImage)
        }
    var pushEditCount: String = ""
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushEditCount)
        }
    var pushAddCount: String = "---"
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushAddCount)
        }
    var pushReduceCount: String = "---"
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushReduceCount)
        }
    var pushTime: String = "---"
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushTime)
        }
    var pushDes: String = "---"
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.pushDes)
        }

    fun cloneFrom(pushUIModel: PushUIModel) {
        pushUserName = pushUIModel.pushUserName
        pushImage = pushUIModel.pushImage
        pushEditCount = pushUIModel.pushEditCount
        pushAddCount = pushUIModel.pushAddCount
        pushReduceCount = pushUIModel.pushReduceCount
        pushTime = pushUIModel.pushTime
        pushDes = pushUIModel.pushDes
    }
}
