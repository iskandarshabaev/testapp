package com.ishabaev.testapp.screen

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.ishabaev.testapp.presenter.BasePresenter
import com.ishabaev.testapp.presenter.PresenterStorage

abstract class BaseFragment<V : BaseView, out P : BasePresenter<V>> : Fragment() {

    protected abstract val fragmentTag: String
    private var _presenter: P? = null
    protected val presenter get() = _presenter!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _presenter = (PresenterStorage.getPresenter(fragmentTag) as P?) ?:
                onCreatePresenter(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _presenter?.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _presenter?.detachView(this as V)
    }

    override fun onDestroy() {
        if (isDisappearing()) PresenterStorage.removePresenter(fragmentTag)
        super.onDestroy()
    }

    public fun isDisappearing(): Boolean {
        if (activity.isFinishing)
            return true
        var anyParentIsRemoving = false
        if (Build.VERSION.SDK_INT >= 17) {
            var parent: Fragment? = parentFragment
            while (!anyParentIsRemoving && parent != null) {
                anyParentIsRemoving = parent.isRemoving
                parent = parent.parentFragment
            }
        }
        return isRemoving || anyParentIsRemoving
    }

    abstract fun onCreatePresenter(savedInstanceState: Bundle?): P
}