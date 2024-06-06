package com.nashss.se.clientkeeper.dependency;


import com.nashss.se.clientkeeper.activity.CreateClientActivity;
import com.nashss.se.clientkeeper.activity.DeleteClientActivity;
import com.nashss.se.clientkeeper.activity.EditClientActivity;
import com.nashss.se.clientkeeper.activity.GetAllClientsActivity;
import com.nashss.se.clientkeeper.activity.GetClientActivity;

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

    /**
     * Provides an instance of GetClientActivity.
     *
     * @return GetClientActivity
     */
    GetClientActivity provideGetClientActivity();

    /**
     * Provides an instance of GetAllClientsActivity.
     *
     * @return GetAllClientsActivity
     */
    GetAllClientsActivity provideGetAllClientsActivity();

    /**
     * Provides an instance of EditClientActivity.
     *
     * @return EditClientActivity
     */
    EditClientActivity provideEditClientActivity();

    /**
     * Provides an instance of DeleteClientActivity.
     *
     * @return DeleteClientActivity
     */
    DeleteClientActivity provideDeleteClientActivity();

}
