import ClientKeeperClient from '../api/clientKeeperClient';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class AddOrder extends BindingClass {
    constructor(props = {}) {
        super();
        this.bindClassMethods(['mount', 'openAddOrderModal', 'closeAddOrderModal', 'addOrder'], this);
        this.client = new ClientKeeperClient();
        this.dataStore = props.dataStore || new DataStore();
        this.currentOrders = props.currentOrders;
    }

    async mount() {
        document.getElementById('addOrderButton').addEventListener('click', this.openAddOrderModal);
        document.getElementById('closeAddOrderModal').addEventListener('click', this.closeAddOrderModal);
        document.getElementById('addOrderForm').addEventListener('submit', this.addOrder);

        window.addEventListener('click', (event) => {
            if (event.target == document.getElementById('addOrderModal')) {
                this.closeAddOrderModal();
            }
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

        const saveButton = event.target.querySelector('.button[type="submit"]');
        saveButton.disabled = true;
        saveButton.textContent = 'Adding...';

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
            this.currentOrders.loadOrders();
        } catch (error) {
            console.error('Error creating order:', error);
        }
    }
}

export default AddOrder;
