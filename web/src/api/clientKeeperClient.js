import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

class ClientKeeperClient extends BindingClass {

    constructor(props = {}) {
        super();

        /* Methods that need binding. Add to this as methods are created */

        const methodsToBind = [
            'clientLoaded', 'getIdentity', 'login', 'logout', 'createClient',
             'getAllClients', 'updateClient', 'deleteClient', 'getUndeliveredOrders',
              'createOrder', 'updateOrder', 'getOrder', 'getOrdersByClientId',
            'formatDate', 'getDeliveredOrders', 'deleteOrder', 'handleError'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        console.log("Client loaded successfully");
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
            console.log("Getting identity of the current user");
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                console.log("User is not logged in");
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            console.error("Error getting identity:", error);
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Log in the user.
     */
    async login() {
        console.log("Logging in the user");
        await this.authenticator.login();
    }

    /**
     * Log out the user.
     */
    async logout() {
        console.log("Logging out the user");
        await this.authenticator.logout();
    }

    /**
     * Get the user token or throw an error if unauthenticated.
     * @param unauthenticatedErrorMessage The error message to throw if unauthenticated.
     * @returns The user token.
     * @throws Error if the user is not logged in.
     */
    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            console.log("User is not logged in. Throwing error:", unauthenticatedErrorMessage);
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
            console.log("Creating a new client with data:", clientData);
            const token = await this.getTokenOrThrow("You must be logged in to create a client.");
            const response = await this.axiosClient.post("/clients", clientData, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log("Client created successfully:", response.data);
            return response.data;
        } catch (error) {
            console.error("Error creating client:", error);
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Retrieves all clients.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns A list of clients.
     */
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

    /**
     * Retrieves all undelivered orders.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns A list of undelivered orders.
     */
    async getUndeliveredOrders(errorCallback) {
        try {
            console.log("Retrieving undelivered orders");
            const token = await this.getTokenOrThrow("You must be logged in to view undelivered orders.");
            console.log(`Using token: ${token}`);
            const response = await this.axiosClient.get('/orders/undelivered', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log('Undelivered Orders API response:', response.data);
            return response.data;
        } catch (error) {
            console.error("Error retrieving undelivered orders:", error);
            this.handleError(error, errorCallback);
            return null;
        }
    }

    /**
     * Creates a new order.
     * @param orderData The order data to be created.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The created order data.
     */
    async createOrder(orderData, errorCallback) {
        try {
            console.log("Creating a new order with data:", orderData);
            const token = await this.getTokenOrThrow("You must be logged in to create an order.");
            const response = await this.axiosClient.post("/orders", orderData, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log("Order created successfully:", response.data);
            return response.data;
        } catch (error) {
            console.error("Error creating order:", error);
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
    
    /**
     * Retrieves all delivered orders.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns A list of delivered orders.
     */
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
            return response.data;
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
     * Formats a date string into MM-DD-YYYY format.
     * @param {string} dateString The date string to format.
     * @returns {string} The formatted date string.
     */
    formatDate(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        const month = ('0' + (date.getMonth() + 1)).slice(-2); // Add 1 because months are zero-based
        const day = ('0' + date.getDate()).slice(-2);
        const year = date.getFullYear();
        return `${month}-${day}-${year}`;
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error('Detailed error:', error.response ? error.response.data : error.message);
        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi);
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}

export default ClientKeeperClient;
