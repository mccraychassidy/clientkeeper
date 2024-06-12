package com.nashss.se.clientkeeper.dependency;

import com.nashss.se.clientkeeper.activity.*;

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
     * Provides an instance of CreateOrderActivity.
     *
     * @return CreateOrderActivity
     */
    CreateOrderActivity provideCreateOrderActivity();

    /**
     * Provides an instance of GetUndeliveredOrdersActivity.
     *
     * @return GetUndeliveredOrdersActivity
     */
    GetUndeliveredOrdersActivity provideGetUndeliveredOrdersActivity();

    /**
     * Provides an instance of GetOrderActivity.
     *
     * @return GetOrderActivity
     */
    GetOrderActivity provideGetOrderActivity();

    /**
     * Provides an instance of EditOrderActivity.
     *
     * @return EditOrderActivity
     */
    EditOrderActivity provideEditOrderActivity();
}
