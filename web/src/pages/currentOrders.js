import ClientKeeperClient from '../api/clientKeeperClient';
import SecondaryHeader from '../components/secondaryHeader';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';
import EditOrder from './editOrder';
import AddOrder from './addOrder';

class CurrentOrders extends BindingClass {
    constructor() {
        super();
        this.dataStore = new DataStore();
        this.bindClassMethods(['mount', 'loadOrders', 'renderOrdersTable', 'refreshOrders'], this);
        this.secondaryHeader = new SecondaryHeader(this.dataStore);
        this.orders = [];
        this.dataStore.addChangeListener(this.refreshOrders);
        this.editOrder = new EditOrder({ dataStore: this.dataStore, currentOrders: this, reloadOrdersCallback: this.loadOrders.bind(this) });
        this.addOrder = new AddOrder({ dataStore: this.dataStore, currentOrders: this });
    }

    async mount() {
        await this.secondaryHeader.addHeaderToPage();
        this.client = new ClientKeeperClient();
        window.addEventListener('refreshOrders', this.refreshOrders.bind(this));

        this.loadOrders();
        this.editOrder.mount();
        this.addOrder.mount();
    }

    async loadOrders() {
        try {
            const response = await this.client.getUndeliveredOrders();
            this.orders = response?.orders || [];
            this.renderOrdersTable(this.orders);
        } catch (error) {
            console.error('Error loading orders:', error);
        }
    }

    renderOrdersTable(orders) {
        const ordersTableBody = document.getElementById('ordersTable').getElementsByTagName('tbody')[0];
        ordersTableBody.innerHTML = '';

        orders.forEach(order => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${order.clientName}</td>
                <td>${order.clientId}</td>
                <td>${order.orderId}</td>
                <td>${this.client.formatDate(order.purchaseDate)}</td>
                <td>${order.shipped ? '✅' : '❌'}</td>
                <td>${order.shippingService || ''}</td>
                <td>${order.expectedDate ? this.client.formatDate(order.expectedDate) : ''}</td>
                <td>${order.trackingNumber || ''}</td>
                <td>${order.item}</td>
                <td>${order.reference || ''}</td>
            `;
            row.addEventListener('click', () => this.editOrder.openEditModal(order));
            ordersTableBody.appendChild(row);
        });
    }

    async refreshOrders() {
        await this.loadOrders();
    }
}


const main = async () => {
    const currentOrders = new CurrentOrders();
    currentOrders.mount();
};

window.addEventListener('DOMContentLoaded', main);

export default CurrentOrders;
