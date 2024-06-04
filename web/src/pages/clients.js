import ClientKeeperClient from '../api/clientKeeperClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class ManageClients extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'displayAllClients', 'filterClients'], this);
        this.header = new Header(this.dataStore);
        this.clients = [];
    }

    /**
     * Add the header to the page and load the ClientKeeperClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new ClientKeeperClient();
        this.displayAllClients();
        document.getElementById('search').addEventListener('input', this.filterClients);
    }

    /**
     * Create table of all saved clients for logged-in user
     */
    async displayAllClients() {
        try {
            const allClients = await this.client.getAllClients();
            this.clients = allClients.clients;
            this.renderClientsTable(this.clients);
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
                <td>${client.name}</td>
                <td>${client.email}</td>
                <td>${client.phone}</td>
                <td>${client.address}</td>
                <td>${client.memberSince}</td>
            `;
            clientsList.appendChild(row);
        });
    }

    /**
     * Filter clients based on search input
     */
    filterClients(event) {
        const searchTerm = event.target.value.toLowerCase();
        const filteredClients = this.clients.filter(client => {
            return client.name.toLowerCase().includes(searchTerm) ||
                   client.email.toLowerCase().includes(searchTerm) ||
                   client.phone.includes(searchTerm) ||
                   client.address.toLowerCase().includes(searchTerm) ||
                   client.memberSince.includes(searchTerm);
        });
        this.renderClientsTable(filteredClients);
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
