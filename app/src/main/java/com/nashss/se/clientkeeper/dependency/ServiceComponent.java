package com.nashss.se.clientkeeper.dependency;

import com.nashss.se.clientkeeper.activity.CreateClientActivity;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component that provides dependencies for ClientKeeper.
 */
@Singleton
@Component(modules = {DaoModule.class})
public interface ServiceComponent {

    /**
     * Provides an instance of CreateClientActivity.
     *
     * @return CreateClientActivity
     */
    CreateClientActivity provideCreateClientActivity();

}
