import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

export default class BirthdayClient extends BindingClass {

    constructor(props = {}) {
        super();

        /* Methods that need binding. Add to this as methods are created */

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'addBirthday'];
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
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Add a birthday.
     * @param firstName The first name of the new birthday.
     * @param lastName The last name of the new birthday.
     * @param month The month of the new birthday (MM).
     * @param day The day of the new birthday (DD).
     * @param year The year of new birthday (optional).
     * @returns The new birthday.
     */
    async addBirthday(firstName, lastName, month, day, year, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a birthday.");
            const birthdayData = {
                firstName: firstName,
                lastName: lastName,
                month: month,
                day: day
            };

            if (year !== "" && year !== undefined && year !== null) {
                birthdayData.year = year;
            }

            const response = await this.axiosClient.post(`birthday`, birthdayData, {
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
     * Fetch today's birthdays.
     * @returns An array of today's birthdays.
     */
    async getTodaysBirthdays(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("You need to be logged in to view today's birthdays.");

            const response = await this.axiosClient.get(`/birthdays/id`, {
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
     * Get all birthdays
     * @returns all saved birthdays for a logged in user
     */
    async getAllBirthdays() {
        try {
            const token = await this.getTokenOrThrow("Log in to manage birthdays");
            const response = await this.axiosClient.get(`/birthdays/all`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback)
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

