import ClientKeeperClient from '../api/clientKeeperClient';
import BindingClass from "../util/bindingClass";

/**
 * The secondary header component for the website.
 */
export default class SecondaryHeader extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteHeader', 'createButton', 'createUserInfoForHeader'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new ClientKeeperClient();
    }

    /**
     * Add the secondary header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteHeader = this.createSiteHeader(currentUser);

        const secondaryHeader = document.getElementById('secondary-header');
        secondaryHeader.appendChild(siteHeader);
    }

    createSiteHeader(currentUser) {
        const headerContainer = document.createElement('div');
        headerContainer.classList.add('header-container');

        const clientsButton = this.createButton('Clients', 'manageClients.html', 'button');
        const currentOrdersButton = this.createButton('Current Orders', 'currentOrders.html', 'button');
        const completedOrdersButton = this.createButton('Completed Orders', 'completedOrders.html', 'button');
        const userButton = currentUser 
            ? this.createButton(`Logout: ${currentUser.name}`, '#', 'button', this.client.logout.bind(this.client))
            : this.createButton('Login / Sign Up', '#', 'button', this.client.login.bind(this.client));

        headerContainer.appendChild(clientsButton);
        headerContainer.appendChild(currentOrdersButton);
        headerContainer.appendChild(completedOrdersButton);
        headerContainer.appendChild(userButton);

        return headerContainer;
    }

    createUserInfoForHeader(currentUser) {
        const userInfoContainer = document.createElement('div');
        userInfoContainer.classList.add('user-info-container');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

        userInfoContainer.appendChild(childContent);

        return userInfoContainer;
    }

    createLoginButton() {
        return this.createButton('Login / Sign Up', '#', 'button', this.client.login.bind(this.client));
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, '#', 'button', this.client.logout.bind(this.client));
    }

    createButton(text, href, className, clickHandler) {
        const button = document.createElement('a');
        button.classList.add(className);
        button.href = href;
        button.innerText = text;

        if (clickHandler) {
            button.addEventListener('click', async (event) => {
                event.preventDefault();
                await clickHandler();
            });
        }

        return button;
    }
}
