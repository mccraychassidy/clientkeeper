import ClientKeeperClient from '../api/clientKeeperClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic for the Today's Birthdays page of the website.
 */
class HomePage extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.client = new ClientKeeperClient();
    }

    /**
     * Mount the component, fetch today's birthdays and render them.
     */
    async mount() {
        await this.header.addHeaderToPage();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const homePage = new HomePage();
    await homePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
