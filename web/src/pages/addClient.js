import ClientKeeperClient from '../api/clientKeeperClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';
import Authenticator from '../api/authenticator';
import { v4 as uuidv4 } from 'uuid';

/**
 * Logic needed for the add client page of the website.
 */
class AddClient extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'clientDoneMessage', 'cancel'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.clientDoneMessage);
        this.header = new Header(this.dataStore);
        this.authenticator = new Authenticator();
        this.client = new ClientKeeperClient();
    }

    /**
     * Add the header to the page and load the ClientKeeperClient.
     */
    async mount() {
        document.getElementById('add-client').addEventListener('click', this.submit);
        document.getElementById('cancel-button').addEventListener('click', this.cancel);

        this.header.addHeaderToPage();

        this.userInfo = await this.authenticator.getCurrentUserInfo();
    }

    /**
     * Method to run when the add client submit button is pressed. Call ClientKeeper to create the client.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const addButton = document.getElementById('add-client');
        const origButtonText = addButton.innerText;
        addButton.innerText = 'Adding...';

        const clientId = generateUUID();
        const userEmail = this.userInfo.email;
        const clientName = document.getElementById('client-name').value;
        const clientEmail = document.getElementById('client-email').value;
        const clientPhone = document.getElementById('client-phone').value;
        const clientAddress = document.getElementById('client-address').value;
        const clientMemberSince = document.getElementById('client-member-since').value;

        try {
            const client = await this.client.createClient({
                userEmail,
                clientId,
                clientName,
                clientEmail,
                clientPhone,
                clientAddress,
                clientMemberSince
            });
            this.dataStore.set('client', client);
        } catch (error) {
            addButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        }

        addButton.innerText = origButtonText; 
    }

    /**
     * When the client is updated in the datastore, show a done message.
     */
    clientDoneMessage() {
        const client = this.dataStore.get('client');
        if (client != null) {
            document.getElementById("messageDisplay").innerText = "Client Added Successfully";

            setTimeout(() => {
                document.getElementById("messageDisplay").innerText = '';
            }, 5000);
        }
    }

    /**
     * Cancel adding client and return to previous page
     */
    cancel() {
        window.location.href = 'clients.html';
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = () => {
    const addClient = new AddClient();
    addClient.mount();
};

window.addEventListener('DOMContentLoaded', main);

/**
 * Function to generate a unique client ID (UUID).
 */
function generateUUID() {
    return uuidv4();
}
