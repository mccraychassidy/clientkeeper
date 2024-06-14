import ClientKeeperClient from '../api/clientKeeperClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class CompletedOrders extends BindingClass {
    constructor() {
        super();
        this.dataStore = new DataStore();
        this.bindClassMethods(['mount', 'loadAllDeliveredOrders', 'renderOrdersTable', 'searchOrdersByClientId', 'refreshOrders'], this);
        this.header = new Header(this.dataStore);
        this.orders = [];
        this.dataStore.addChangeListener(this.refreshOrders);
    }

    mount() {
        this.header.addHeaderToPage();
        this.client = new ClientKeeperClient();
        document.getElementById('searchBar').addEventListener('input', this.searchOrdersByClientId);
        document.getElementById('signOutButton').addEventListener('click', async () => {
            await this.client.logout();
            window.location.href = 'index.html';
        });
        this.loadAllDeliveredOrders();
    }

    async loadAllDeliveredOrders() {
        try {
            const response = await this.client.getDeliveredOrders();
            this.orders = response.orders || [];
            this.renderOrdersTable(this.orders);
        } catch (error) {
            console.error('Error loading delivered orders:', error);
        }
    }

    async searchOrdersByClientId(event) {
        const clientId = event.target.value.trim();
        if (clientId) {
            try {
                const response = await this.client.getOrdersByClientId(clientId);
                this.renderOrdersTable(response.orders || []);
            } catch (error) {
                console.error('Error searching orders by client ID:', error);
            }
        } else {
            this.loadAllDeliveredOrders();
        }
    }

    renderOrdersTable(orders) {
        const ordersTableBody = document.getElementById('ordersTable').getElementsByTagName('tbody')[0];
        ordersTableBody.innerHTML = '';

        if (Array.isArray(orders)) {
            orders.forEach(order => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${order.clientName}</td>
                    <td>${order.clientId}</td>
                    <td>${order.orderId}</td>
                    <td>${order.purchaseDate}</td>
                    <td>${order.deliveredDate}</td>
                    <td>${order.shippingService}</td>
                    <td>${order.trackingNumber}</td>
                    <td>${order.item}</td>
                    <td>${order.reference}</td>
                `;
                ordersTableBody.appendChild(row);
            });
        } else {
            console.error('Expected an array of orders, but got:', orders);
        }
    }

    async refreshOrders() {
        await this.loadAllDeliveredOrders();
    }
}

const main = async () => {
    const completedOrders = new CompletedOrders();
    completedOrders.mount();
};

window.addEventListener('DOMContentLoaded', main);

export default CompletedOrders;
