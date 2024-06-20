import ClientKeeperClient from '../api/clientKeeperClient';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class EditOrder extends BindingClass {
    constructor(props = {}) {
        super();
        this.bindClassMethods(['mount', 'openEditModal', 'closeEditModal', 'saveOrder', 'deleteOrder', 'resetFormFields', 'resetSaveButton'], this);
        this.client = new ClientKeeperClient();
        this.dataStore = props.dataStore || new DataStore();
        this.reloadOrdersCallback = props.reloadOrdersCallback;
    }

    async mount() {
        document.getElementById('close-order-modal').addEventListener('click', this.closeEditModal);
        document.getElementById('save-order-button').addEventListener('click', this.saveOrder);
        document.getElementById('delete-order-button').addEventListener('click', this.deleteOrder);

        const userIdentity = await this.client.getIdentity();
        this.userEmail = userIdentity.email;
    }

    openEditModal(order) {
        this.resetFormFields();
        this.resetSaveButton();

        document.getElementById('edit-order-id').value = order.orderId;
        document.getElementById('edit-client-id').value = order.clientId;
        document.getElementById('edit-client-name').value = order.clientName;
        document.getElementById('edit-item').value = order.item;
        document.getElementById('edit-shipped').checked = order.shipped;
        document.getElementById('edit-purchase-date').value = order.purchaseDate;
        document.getElementById('edit-shipping-service').value = order.shippingService;
        document.getElementById('edit-expected-date').value = order.expectedDate || '';
        document.getElementById('edit-delivered-date').value = order.deliveredDate || '';
        document.getElementById('edit-tracking-number').value = order.trackingNumber;
        document.getElementById('edit-reference').value = order.reference;
        document.getElementById('edit-order-modal').style.display = 'block';
    }

    closeEditModal() {
        this.resetFormFields();
        this.resetSaveButton();
        document.getElementById('edit-order-modal').style.display = 'none';
    }

    resetFormFields() {
        document.getElementById('edit-order-id').value = '';
        document.getElementById('edit-client-id').value = '';
        document.getElementById('edit-client-name').value = '';
        document.getElementById('edit-item').value = '';
        document.getElementById('edit-shipped').checked = false;
        document.getElementById('edit-purchase-date').value = '';
        document.getElementById('edit-shipping-service').value = '';
        document.getElementById('edit-expected-date').value = '';
        document.getElementById('edit-delivered-date').value = '';
        document.getElementById('edit-tracking-number').value = '';
        document.getElementById('edit-reference').value = '';
    }

    resetSaveButton() {
        const saveButton = document.getElementById('save-order-button');
        saveButton.disabled = false;
        saveButton.textContent = 'Save';
    }

    resetDeleteButton() {
        const deleteButton = document.getElementById('delete-order-button');
        deleteButton.disabled = false;
        deleteButton.textContent = 'Delete';
    }

    async saveOrder() {
        const saveButton = document.getElementById('save-order-button');
        saveButton.disabled = true;
        saveButton.textContent = 'Editing...';

        const orderId = document.getElementById('edit-order-id').value;
        const clientId = document.getElementById('edit-client-id').value;
        const clientName = document.getElementById('edit-client-name').value;
        const item = document.getElementById('edit-item').value;
        const shipped = document.getElementById('edit-shipped').checked;
        const purchaseDate = document.getElementById('edit-purchase-date').value;
        const shippingService = document.getElementById('edit-shipping-service').value;
        const expectedDate = document.getElementById('edit-expected-date').value || null;
        const deliveredDate = document.getElementById('edit-delivered-date').value || null;
        const trackingNumber = document.getElementById('edit-tracking-number').value;
        const reference = document.getElementById('edit-reference').value;

        const orderData = {
            orderId,
            clientId,
            clientName,
            item,
            shipped,
            purchaseDate,
            shippingService,
            expectedDate,
            deliveredDate,
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
            window.dispatchEvent(new Event('refreshOrders'));
        } catch (error) {
            console.error('Error saving order:', error);
        }
    }

    async deleteOrder() {
        const deleteButton = document.getElementById('delete-order-button');
        deleteButton.disabled = true;
        deleteButton.textContent = 'Deleting...';

        const orderId = document.getElementById('edit-order-id').value;

        try {
            await this.client.deleteOrder(this.userEmail, orderId);
            this.closeEditModal();
            if (this.reloadOrdersCallback) {
                this.reloadOrdersCallback();
            }
            window.dispatchEvent(new Event('refreshOrders'));
        } catch (error) {
            console.error('Error deleting order:', error);
        }
    }
}

export default EditOrder;
