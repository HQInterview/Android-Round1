/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.domain.interactor;

import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.data.repository.WebDataRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class LoadWebListUseCase {

    private final WebDataRepository webDataRepository;

    @Inject
    public LoadWebListUseCase(WebDataRepository webDataRepository){
        this.webDataRepository = webDataRepository;
    }

    public Observable<List<Web>> buildUseCaseObservable() {
        return webDataRepository.getWebList();
    }
}
