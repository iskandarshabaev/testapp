package com.ishabaev.testapp

import com.ishabaev.testapp.api.CurrencyResponse
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

object Repository {
    private var concurrencySubject = BehaviorSubject.create<CurrencyResponse>()

    fun update(response: CurrencyResponse) {
        concurrencySubject.onNext(response)
    }

    fun subscribeToConcurrencyResponse(): Observable<CurrencyResponse> {
        return concurrencySubject
    }
}