import ClientKeeperClient from '../api/clientKeeperClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';
import EditOrder from './editOrder';

class CurrentOrders extends BindingClass {
    constructor() {
        super();
        this.dataStore = new DataStore();
        this.bindClassMethods(['mount', 'loadOrders', 'renderOrdersTable', 'openAddOrderModal', 'closeAddOrderModal', 'addOrder', 'refreshOrders'], this);
        this.header = new Header(this.dataStore);
        this.orders = [];
        this.dataStore.addChangeListener(this.refreshOrders);
        this.editOrder = new EditOrder({ dataStore: this.dataStore, currentOrders: this });
    }

    mount() {
        this.header.addHeaderToPage();
        this.client = new ClientKeeperClient();
        document.getElementById('addOrderButton').addEventListener('click', this.openAddOrderModal);
        document.getElementById('closeAddOrderModal').addEventListener('click', this.closeAddOrderModal);
        document.getElementById('addOrderForm').addEventListener('submit', this.addOrder);
        document.getElementById('signOutButton').addEventListener('click', async () => {
            await this.client.logout();
            window.location.href = 'index.html';
        });
        window.addEventListener('click', (event) => {
            if (event.target == document.getElementById('addOrderModal')) {
                this.closeAddOrderModal();
            }
        });
        this.loadOrders();
        this.editOrder.mount();
    }

    async loadOrders() {
        try {
            const response = await this.client.getUndeliveredOrders();
            this.orders = response.orders || [];
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
                <td>${order.purchaseDate}</td>
                <td>${order.shipped ? '✅' : '❌'}</td>
                <td>${order.shippingService}</td>
                <td>${order.expectedDate}</td>
                <td>${order.trackingNumber}</td>
                <td>${order.item}</td>
                <td>${order.reference}</td>
            `;
            row.addEventListener('click', () => this.editOrder.openEditModal(order));
            ordersTableBody.appendChild(row);
        });
    }

    openAddOrderModal() {
        document.getElementById('addOrderModal').style.display = 'block';
    }

    closeAddOrderModal() {
        document.getElementById('addOrderModal').style.display = 'none';
    }

    async addOrder(event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        const newOrder = {
            clientName: formData.get('clientName'),
            clientId: formData.get('clientId'),
            purchaseDate: formData.get('purchaseDate'),
            shipped: formData.get('shipped') === 'on',
            shippingService: formData.get('shippingService'),
            expectedDate: formData.get('expectedDate'),
            trackingNumber: formData.get('trackingNumber'),
            item: formData.get('item'),
            reference: formData.get('reference')
        };

        try {
            await this.client.createOrder(newOrder);
            this.closeAddOrderModal();
            this.loadOrders();
        } catch (error) {
            console.error('Error creating order:', error);
        }
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
