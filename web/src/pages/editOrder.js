import ClientKeeperClient from '../api/clientKeeperClient';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class EditOrder extends BindingClass {
    constructor(props = {}) {
        super();
        this.bindClassMethods(['mount', 'openEditModal', 'closeEditModal', 'saveOrder'], this);
        this.client = new ClientKeeperClient();
        this.dataStore = props.dataStore || new DataStore();
        this.reloadOrdersCallback = props.reloadOrdersCallback;
    }

    async mount() {
        document.getElementById('close-order-modal').addEventListener('click', this.closeEditModal);
        document.getElementById('save-order-button').addEventListener('click', this.saveOrder);
        document.getElementById('cancel-order-button').addEventListener('click', this.closeEditModal);

        const userIdentity = await this.client.getIdentity();
        this.userEmail = userIdentity.email;
    }

    openEditModal(order) {
        document.getElementById('edit-order-id').value = order.orderId;
        document.getElementById('edit-client-id').value = order.clientId;
        document.getElementById('edit-client-name').value = order.clientName;
        document.getElementById('edit-item').value = order.item;
        document.getElementById('edit-shipped').checked = order.shipped;
        document.getElementById('edit-purchase-date').value = order.purchaseDate;
        document.getElementById('edit-shipping-service').value = order.shippingService;
        document.getElementById('edit-expected-date').value = order.expectedDate;
        document.getElementById('edit-tracking-number').value = order.trackingNumber;
        document.getElementById('edit-reference').value = order.reference;
        document.getElementById('edit-order-modal').style.display = 'block';
    }

    closeEditModal() {
        document.getElementById('edit-order-modal').style.display = 'none';
    }

    async saveOrder() {
        const orderId = document.getElementById('edit-order-id').value;
        const clientId = document.getElementById('edit-client-id').value;
        const clientName = document.getElementById('edit-client-name').value;
        const item = document.getElementById('edit-item').value;
        const shipped = document.getElementById('edit-shipped').checked;
        const purchaseDate = document.getElementById('edit-purchase-date').value;
        const shippingService = document.getElementById('edit-shipping-service').value;
        const expectedDateElement = document.getElementById('edit-expected-date');
        const trackingNumber = document.getElementById('edit-tracking-number').value;
        const reference = document.getElementById('edit-reference').value;

        const expectedDate = expectedDateElement.value ? expectedDateElement.value : null;

        const orderData = {
            orderId,
            clientId,
            clientName,
            item,
            shipped,
            purchaseDate,
            shippingService,
            expectedDate,
            trackingNumber,
            reference,
            userEmail: this.userEmail
        };

        try {
            const updatedOrder = await this.client.updateOrder(orderData);
            this.dataStore.set('order', updatedOrder);
            this.closeEditModal();
            if (this.reloadOrdersCallback) {
                this.reloadOrdersCallback();
            }
        } catch (error) {
            console.error('Error saving order:', error);
        }
    }
}

export default EditOrder;
