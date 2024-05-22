import BirthdayClient from '../api/birthdayClient';
import Header from '../components/headerBirthday';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class ManageBirthdays extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'displayAllBirthdays'], this);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the BirthdayClient.
     */
    mount() {
        this.header.addHeaderToPage();

        this.client = new BirthdayClient();

        this.displayAllBirthdays();
    }

    /**
     * Create table of all saved birthdays for logged in user
     */
     async displayAllBirthdays() {
        try {
            const allBirthdays = await this.client.getAllBirthdays();

            const birthdaysList = document.getElementById('all-birthdays');
            const currentYear = new Date().getFullYear();

            allBirthdays.birthdays.forEach(birthday => {
                const birthdayElement = document.createElement('li');
                let birthdayText = `${birthday.firstName} ${birthday.lastName}`;

                // If the year is provided, calculate the age.
                if (birthday.year) {
                    const age = currentYear - birthday.year;
                    birthdayText += ` - Age: ${age}`;
                } else {
                    birthdayText += ' - Age: N/A';
                }

                birthdayElement.innerText = birthdayText;
                birthdaysList.appendChild(birthdayElement);
            });
        } catch (error) {
            console.error('Error fetching today’s birthdays:', error);
        }
     }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const manageBirthdays = new ManageBirthdays();
    manageBirthdays.mount();
};

window.addEventListener('DOMContentLoaded', main);