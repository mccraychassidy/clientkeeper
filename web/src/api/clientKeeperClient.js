import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

class clientKeeperClient extends BindingClass {

    constructor(props = {}) {
        super();

        /* Methods that need binding. Add to this as methods are created */

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'createClient', 'getAllClients', 'updateClient', 'deleteClient', 'getUndeliveredOrders', 'createOrder', 'updateOrder', 'getOrder', 'getOrdersByClientId'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }
    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
       await this.authenticator.login();
    }

    async logout() {
        await this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Creates a new client.
     * @param clientData The client data to be created.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The created client data.
     */
    async createClient(clientData, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to create a client.");
            const response = await this.axiosClient.post("/clients", clientData, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    async getAllClients(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to view clients.");
            const userInfo = await this.getIdentity();
            if (!userInfo || !userInfo.email) {
                throw new Error("Unable to retrieve user information.");
            }
            const response = await this.axiosClient.get(`/clients?email=${userInfo.email}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log('API response:', response.data);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
            return null;
        }
    }

    /**
    * Updates an existing client.
    * @param clientData The client data to be updated.
    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns The updated client data.
    */
    async updateClient(clientData, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to update a client.");
            const response = await this.axiosClient.put(`/clients/${clientData.clientId}`, clientData, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
    * Deletes an existing client.
    * @param userEmail The email of the user.
     * @param clientId The ID of the client to delete.
    * @param errorCallback (Optional) A function to execute if the call fails.
    */
    async deleteClient(userEmail, clientId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to delete a client.");
            await this.axiosClient.delete('/clients', {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                data: {
                    userEmail,
                    clientId
                }
            });
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

        

    async getUndeliveredOrders(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to view undelivered orders.");
            const response = await this.axiosClient.get('/orders/undelivered', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log('Undelivered Orders API response:', response.data);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
            return null;
        }
    }

    async createOrder(orderData, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to create an order.");
            const response = await this.axiosClient.post("/orders", orderData, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Fetches an order by ID.
     * @param userEmail The email of the user.
     * @param orderId The ID of the order to fetch.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The order data.
     */
    async getOrder(userEmail, orderId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to view an order.");
            const response = await this.axiosClient.get(`/orders/${orderId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                },
                params: {
                    userEmail: userEmail
                }
            });
            return response.data.order;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Updates an existing order.
     * @param orderData The order data to be updated.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The updated order data.
     */
    async updateOrder(orderData, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to update an order.");
            const response = await this.axiosClient.put(`/orders/${orderData.orderId}`, orderData, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
            } catch (error) {
                this.handleError(error, errorCallback);
            }
    }
    
    async getDeliveredOrders(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to view delivered orders.");
            const response = await this.axiosClient.get('/orders/delivered', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log('Delivered Orders API response:', response.data);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
            return null;
        }
    }

    /**
     * Fetches orders by Client ID using GSI.
     * @param clientId The Client ID to search for orders.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns A list of orders for the specified Client ID.
     */
    async getOrdersByClientId(clientId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to search orders.");
            const response = await this.axiosClient.get(`/orders/byClientId/${clientId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.orders;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    async getDeliveredOrders(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to view delivered orders.");
            const response = await this.axiosClient.get('/orders/delivered', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log('Delivered Orders API response:', response.data);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
            return null;
        }
    }

    /**
     * Fetches orders by Client ID using GSI.
     * @param clientId The Client ID to search for orders.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns A list of orders for the specified Client ID.
     */
    async getOrdersByClientId(clientId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to search orders.");
            const response = await this.axiosClient.get(`/orders/byClientId/${clientId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.orders;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Deletes an existing order.
     * @param userEmail The email of the user.
     * @param orderId The ID of the order to delete.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    async deleteOrder(userEmail, orderId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You must be logged in to delete an order.");
            await this.axiosClient.delete('/orders', {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                data: {
                    userEmail,
                    orderId
                }
            });
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}

export default clientKeeperClient;