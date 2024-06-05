import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

export default class clientKeeperClient extends BindingClass {

    constructor(props = {}) {
        super();

        /* Methods that need binding. Add to this as methods are created */

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'createClient', 'getAllClients'];
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

