import ClientKeeperClient from '../api/clientKeeperClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
            'createLoginButton', 'createLoginButton', 'createLogoutButton', 'createAboutButton', 'createOrdersButton'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new ClientKeeperClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);
    }

    createSiteTitle() {
        const homeButton = document.createElement('a');
        homeButton.classList.add('header_home');
        homeButton.href = 'demo.html';
        homeButton.innerText = 'Request A Demo';

        const aboutButton = this.createAboutButton();
        const ordersButton = this.createOrdersButton();

        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);
        siteTitle.appendChild(aboutButton);
        siteTitle.appendChild(ordersButton);

        return siteTitle;
    }

    createAboutButton() {
        const aboutButton = document.createElement('a');
        aboutButton.classList.add('header_about');
        aboutButton.href = 'about.html';
        aboutButton.innerText = 'About';

        const aboutContainer = document.createElement('div');
        aboutContainer.classList.add('about-container');
        aboutContainer.appendChild(aboutButton);

        return aboutContainer;
    }

    createOrdersButton() {
        const ordersButton = document.createElement('a');
        ordersButton.classList.add('header_orders');
        ordersButton.href = 'currentOrders.html';
        ordersButton.innerText = 'Current Orders';

        const ordersContainer = document.createElement('div');
        ordersContainer.classList.add('orders-container');
        ordersContainer.appendChild(ordersButton);

        return ordersContainer;
    }

    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user-info');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

        userInfo.appendChild(childContent);

        return userInfo;
    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async (event) => {
            await clickHandler();
        });

        return button;
    }
}