import ClientKeeperClient from '../api/clientKeeperClient';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class AddClient extends BindingClass {
    constructor(props = {}) {
        super();
        this.bindClassMethods(['mount', 'openAddClientModal', 'closeAddClientModal', 'addClient'], this);
        this.client = new ClientKeeperClient();
        this.dataStore = props.dataStore || new DataStore();
        this.manageClients = props.manageClients;
    }

    async mount() {
        document.getElementById('addClientButton').addEventListener('click', this.openAddClientModal);
        document.getElementById('closeAddClientModal').addEventListener('click', this.closeAddClientModal);
        document.getElementById('addClientForm').addEventListener('submit', this.addClient);

        window.addEventListener('click', (event) => {
            if (event.target == document.getElementById('addClientModal')) {
                this.closeAddClientModal();
            }
        });
    }

    openAddClientModal() {
        document.getElementById('addClientModal').style.display = 'block';
    }

    closeAddClientModal() {
        document.getElementById('addClientModal').style.display = 'none';
    }

    resetAddButton() {
        const addButton = document.querySelector('#addClientForm .button[type="submit"]');
        addButton.disabled = false;
        addButton.textContent = 'Add Client';
    }

    resetFormFields() {
        document.getElementById('addClientForm').reset();
    }

    async addClient(event) {
        event.preventDefault();

        const saveButton = event.target.querySelector('.button[type="submit"]');
        saveButton.disabled = true;
        saveButton.textContent = 'Adding...';

        const formData = new FormData(event.target);
        const newClient = {
            clientName: formData.get('clientName'),
            clientEmail: formData.get('clientEmail'),
            clientPhone: formData.get('clientPhone'),
            clientAddress: formData.get('clientAddress'),
            clientMemberSince: formData.get('clientMemberSince')
        };

        try {
            await this.client.createClient(newClient);
            this.closeAddClientModal();
            this.manageClients.displayAllClients();
        } catch (error) {
            console.error('Error creating client:', error);
        } finally {
            this.resetAddButton();
            this.resetFormFields();
        }
    }
}

export default AddClient;
