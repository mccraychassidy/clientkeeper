import ClientKeeperClient from '../api/clientKeeperClient';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class EditClient extends BindingClass {
    constructor(props = {}) {
        super();
        this.bindClassMethods(['mount', 'openEditModal', 'closeEditModal', 'saveClient'], this);
        this.client = new ClientKeeperClient();
        this.dataStore = props.dataStore || new DataStore();
    }

    async mount() {
        document.getElementById('close-modal').addEventListener('click', this.closeEditModal);
        document.getElementById('save-client-button').addEventListener('click', this.saveClient);
        document.getElementById('cancel-button').addEventListener('click', this.closeEditModal);

        this.userEmail = (await this.client.getIdentity()).email;
    }

    openEditModal(client) {
        document.getElementById('edit-client-id').value = client.clientId;
        document.getElementById('edit-client-name').value = client.clientName;
        document.getElementById('edit-client-email').value = client.clientEmail;
        document.getElementById('edit-client-phone').value = client.clientPhone;
        document.getElementById('edit-client-address').value = client.clientAddress;
        document.getElementById('edit-client-member-since').value = client.clientMemberSince;
        document.getElementById('edit-client-modal').style.display = 'block';
    }

    closeEditModal() {
        document.getElementById('edit-client-modal').style.display = 'none';
    }

    async saveClient() {
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
        } catch (error) {
            console.error('Error saving client:', error);
        }
    }
}

const main = async () => {
    const editClient = new EditClient();
    editClient.mount();
};

window.addEventListener('DOMContentLoaded', main);

export default EditClient;