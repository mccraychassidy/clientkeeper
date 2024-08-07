import ClientKeeperClient from '../api/clientKeeperClient';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class EditClient extends BindingClass {
    constructor(props = {}) {
        super();
        this.bindClassMethods(['mount', 'openEditModal', 'closeEditModal', 'saveClient', 'deleteClient', 'resetFormFields', 'resetSaveButton'], this);
        this.client = new ClientKeeperClient();
        this.dataStore = props.dataStore || new DataStore();
        this.manageClients = props.manageClients;
    }

    async mount() {
        document.getElementById('close-client-modal').addEventListener('click', this.closeEditModal);
        document.getElementById('save-client-button').addEventListener('click', this.saveClient);
        document.getElementById('delete-client-button').addEventListener('click', this.deleteClient);

        this.userEmail = (await this.client.getIdentity()).email;
    }

    openEditModal(client) {
        this.resetFormFields();
        this.resetSaveButton();

        document.getElementById('edit-client-id').value = client.clientId;
        document.getElementById('edit-client-name').value = client.clientName;
        document.getElementById('edit-client-email').value = client.clientEmail;
        document.getElementById('edit-client-phone').value = client.clientPhone;
        document.getElementById('edit-client-address').value = client.clientAddress;
        document.getElementById('edit-client-member-since').value = client.clientMemberSince;
        document.getElementById('edit-client-modal').style.display = 'block';
    }

    closeEditModal() {
        this.resetFormFields();
        this.resetSaveButton();
        document.getElementById('edit-client-modal').style.display = 'none';
    }

    resetFormFields() {
        document.getElementById('edit-client-id').value = '';
        document.getElementById('edit-client-name').value = '';
        document.getElementById('edit-client-email').value = '';
        document.getElementById('edit-client-phone').value = '';
        document.getElementById('edit-client-address').value = '';
        document.getElementById('edit-client-member-since').value = '';
    }

    resetSaveButton() {
        const saveButton = document.getElementById('save-client-button');
        saveButton.disabled = false;
        saveButton.textContent = 'Save';
    }

    async saveClient() {
        const saveButton = document.getElementById('save-client-button');
        saveButton.disabled = true;
        saveButton.textContent = 'Saving...';

        const clientId = document.getElementById('edit-client-id').value;
        const clientName = document.getElementById('edit-client-name').value;
        const clientEmail = document.getElementById('edit-client-email').value;
        const clientPhone = document.getElementById('edit-client-phone').value;
        const clientAddress = document.getElementById('edit-client-address').value;
        const clientMemberSince = document.getElementById('edit-client-member-since').value;

        const clientData = {
            clientId,
            clientName,
            clientEmail,
            clientPhone,
            clientAddress,
            clientMemberSince,
            userEmail: this.userEmail
        };

        try {
            const updatedClient = await this.client.updateClient(clientData);
            this.dataStore.set('client', updatedClient);
            this.closeEditModal();
            if (this.manageClients) {
                this.manageClients.displayAllClients();
            }
        } catch (error) {
            console.error('Error saving client:', error);
        } finally {
            this.resetSaveButton();
        }
    }

    async deleteClient() {
        const deleteButton = document.getElementById('delete-client-button');
        deleteButton.disabled = true;
        deleteButton.textContent = 'Deleting...';

        const clientId = document.getElementById('edit-client-id').value;

        try {
            await this.client.deleteClient(this.userEmail, clientId);
            this.closeEditModal();
            if (this.manageClients) {
                this.manageClients.displayAllClients();
            }
        } catch (error) {
            console.error('Error deleting client:', error);
        } finally {
            this.resetSaveButton();
            this.resetFormFields();
        }
    }
}

export default EditClient;
