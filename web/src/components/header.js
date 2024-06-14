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
            'createLoginButton', 'createLogoutButton', 'createAboutButton'
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

        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);
        siteTitle.appendChild(aboutButton);

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
        return this.createButton('Login / Sign Up', this.client.login);
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