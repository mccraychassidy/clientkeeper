import ClientKeeperClient from '../api/clientKeeperClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';
import EditClient from './editClient';

class ManageClients extends BindingClass {
    constructor() {
        super();
        this.dataStore = new DataStore();
        this.bindClassMethods(['mount', 'displayAllClients', 'renderClientsTable', 'filterClients', 'refreshClients'], this);
        this.header = new Header(this.dataStore);
        this.clients = [];
        this.dataStore.addChangeListener(this.refreshClients);
        this.editClient = new EditClient({ dataStore: this.dataStore });
    }

    /**
     * Add the header to the page and load the ClientKeeperClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new ClientKeeperClient();
        this.displayAllClients();
        document.getElementById('search').addEventListener('input', this.filterClients);
        this.editClient.mount();
    }

    /**
     * Create table of all saved clients for logged-in user
     */
    async displayAllClients() {
        try {
            const allClients = await this.client.getAllClients();
            if (allClients && allClients.clients) {
                this.clients = allClients.clients;
                this.renderClientsTable(this.clients);
            } else {
                console.error('No clients found or API response is malformed');
            }
        } catch (error) {
            console.error('Error fetching clients:', error);
        }
    }

    /**
     * Render clients table
     */
    renderClientsTable(clients) {
        const clientsList = document.getElementById('clients-list');
        clientsList.innerHTML = '';
        
        clients.forEach(client => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${client.clientName}</td>
                <td>${client.clientEmail}</td>
                <td>${client.clientPhone}</td>
                <td>${client.clientAddress}</td>
                <td>${client.clientMemberSince}</td>
            `;
            row.addEventListener('click', () => this.editClient.openEditModal(client));
            clientsList.appendChild(row);
        });
    }

    /**
     * Filter clients based on search input
     */
    filterClients(event) {
        const searchTerm = event.target.value.toLowerCase();
        const filteredClients = this.clients.filter(client => {
            return client.clientName.toLowerCase().includes(searchTerm) ||
                   client.clientEmail.toLowerCase().includes(searchTerm) ||
                   client.clientPhone.includes(searchTerm) ||
                   client.clientAddress.toLowerCase().includes(searchTerm) ||
                   client.clientMemberSince.includes(searchTerm);
        });
        this.renderClientsTable(filteredClients);
    }

    async refreshClients() {
        await this.displayAllClients();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const manageClients = new ManageClients();
    manageClients.mount();
};

window.addEventListener('DOMContentLoaded', main);

export default ManageClients;
