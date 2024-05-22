import BirthdayClient from '../api/birthdayClient';
import Header from '../components/headerBirthday';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the add birthday page of the website.
 */
class AddBirthday extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'birthdayDoneMessage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.birthdayDoneMessage);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the BirthdayClient.
     */
    mount() {
        document.getElementById('add-birthday').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new BirthdayClient();
    }

    /**
     * Method to run when the add birthday submit button is pressed. Call Birthday Buddy to create the
     * birthday.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const addButton = document.getElementById('add-birthday');
        const origButtonText = addButton.innerText;
        addButton.innerText = 'Adding...';

        const firstName = document.getElementById('first-name').value;
        const lastName = document.getElementById('last-name').value;
        const month = document.getElementById('month').value;
        const day = document.getElementById('day').value;
        const year = document.getElementById('birth-year').value;

        const birthday = await this.client.addBirthday(firstName, lastName, month, day, year, (error) => {
            addButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('birthday', birthday);
        }
    /**
     * When the birthday is updated in the datastore, show a done message.
     */
    birthdayDoneMessage() {
        const birthday = this.dataStore.get('birthday');
        if (birthday != null) {
            document.getElementById("messageDisplay").innerText = "Done";

            setTimeout(function() {
                document.getElementById("messageDisplay").innerText = '';
            }, 5000);
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = () => {
    const addBirthday = new AddBirthday();
    addBirthday.mount();
};

window.addEventListener('DOMContentLoaded', main);
