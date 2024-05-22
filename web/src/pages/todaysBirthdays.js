import BirthdayClient from '../api/birthdayClient';
import Header from '../components/headerBirthday';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic for the Today's Birthdays page of the website.
 */
class TodaysBirthdays extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'renderBirthdays'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.client = new BirthdayClient();
    }

    /**
     * Mount the component, fetch today's birthdays and render them.
     */
    async mount() {
        this.header.addHeaderToPage();
        await this.renderBirthdays();
    }

    /**
     * Fetch and display today's birthdays.
     */
    async renderBirthdays() {
        try {
            const todaysBirthdays = await this.client.getTodaysBirthdays();

            const birthdaysList = document.getElementById('todays-birthdays');
            const currentYear = new Date().getFullYear();

            todaysBirthdays.birthdays.forEach(birthday => {
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
    const todaysBirthdays = new TodaysBirthdays();
    await todaysBirthdays.mount();
};

window.addEventListener('DOMContentLoaded', main);
